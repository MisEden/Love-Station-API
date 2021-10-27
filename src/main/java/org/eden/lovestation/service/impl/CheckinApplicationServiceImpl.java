package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.model.CheckinForm;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.enums.CheckinApplicationStage;
import org.eden.lovestation.dto.projection.*;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.dto.response.*;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.CheckinApplicationService;
import org.eden.lovestation.util.checker.CheckerUtil;
import org.eden.lovestation.util.line.LineUtil;
import org.eden.lovestation.util.line.Message;
import org.eden.lovestation.util.line.MessageType;
import org.eden.lovestation.util.storage.CheckinApplicationMetaData;
import org.eden.lovestation.util.storage.StorageUtil;
import org.hibernate.annotations.Check;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CheckinApplicationServiceImpl implements CheckinApplicationService {

    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;
    private final RoomStateRepository roomStateRepository;
    private final RoomStateChangeRepository roomStateChangeRepository;
    private final UserRepository userRepository;
    private final ReferralEmployeeRepository referralEmployeeRepository;
    private final VolunteerRepository volunteerRepository;
    private final CheckinApplicationRepository checkinApplicationRepository;
    private final CheckinFormRepository checkinFormRepository;
    private final AdminRepository adminRepository;
    private final HouseRepository houseRepository;
    private final CheckerUtil checkerUtil;
    private final StorageUtil storageUtil;
    private final LineUtil lineUtil;

    @Autowired
    public CheckinApplicationServiceImpl(ModelMapper modelMapper,
                                         RoomRepository roomRepository,
                                         RoomStateRepository roomStateRepository,
                                         RoomStateChangeRepository roomStateChangeRepository,
                                         UserRepository userRepository,
                                         ReferralEmployeeRepository referralEmployeeRepository,
                                         VolunteerRepository volunteerRepository,
                                         CheckinApplicationRepository checkinApplicationRepository,
                                         CheckinFormRepository checkinFormRepository,
                                         CheckerUtil checkerUtil,
                                         AdminRepository adminRepository,
                                         HouseRepository houseRepository,
                                         StorageUtil storageUtil,
                                         LineUtil lineUtil) {
        this.modelMapper = modelMapper;
        this.roomRepository = roomRepository;
        this.roomStateRepository = roomStateRepository;
        this.roomStateChangeRepository = roomStateChangeRepository;
        this.userRepository = userRepository;
        this.referralEmployeeRepository = referralEmployeeRepository;
        this.volunteerRepository = volunteerRepository;
        this.checkinApplicationRepository = checkinApplicationRepository;
        this.checkinFormRepository = checkinFormRepository;
        this.checkerUtil = checkerUtil;
        this.adminRepository = adminRepository;
        this.houseRepository = houseRepository;
        this.storageUtil = storageUtil;
        this.lineUtil = lineUtil;
    }

    @Transactional
    @Override
    public CheckinApplication applyFirstStageCheckinApplication(ApplyFirstStageCheckinApplicationRequest request, String referralEmployeeId) throws DateFormatParseException, RoomNotFoundException, RoomOccupiedException, UserNotFoundException, ReferralEmployeeNotFoundException {
        CheckinApplication checkinApplication = modelMapper.map(request, CheckinApplication.class);
        String userId = request.getUserId();
        Date referralDate = request.getReferralDateConverted();
        checkinApplication.setReferralDate(referralDate);
        Date startDate = request.getStartDateConverted();
        Date endDate = request.getEndDateConverted();
        String roomId = request.getRoomId();
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        House house = room.getHouse();
        boolean isRoomOccupied = roomStateRepository.existsByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndDeletedIsFalse(room, endDate, startDate);
        if (isRoomOccupied) {
            throw new RoomOccupiedException();
        }
        User user = userRepository.findByIdAndVerified(userId, true).orElseThrow(UserNotFoundException::new);
        checkinApplication.setUser(user);
        ReferralEmployee referralEmployee = referralEmployeeRepository.findByIdAndVerified(referralEmployeeId, true).orElseThrow(ReferralEmployeeNotFoundException::new);
        checkinApplication.setReferralEmployee(referralEmployee);

        String attachment = request.getAttachmentConverted();
        String language = request.getLanguageConverted();
        String userIdentity = request.getUserIdentityConverted();
        String applicationReason = request.getApplicationReasonConverted();
        checkinApplication.setLanguage(language);
        checkinApplication.setAttachment(attachment);
        checkinApplication.setUserIdentity(userIdentity);
        checkinApplication.setApplicationReason(applicationReason);

        RoomState roomState = new RoomState();
        roomState.setRoom(room);
        roomState.setState("預約中");
        roomState.setUser(user);
        roomState.setStartDate(startDate);
        roomState.setEndDate(endDate);
        roomState.setDeleted(false);
        roomStateRepository.save(roomState);

        checkinApplication.setRoomState(roomState);
        checkinApplication.setRoom(room);
        checkinApplication.setHouse(house);
        return checkinApplicationRepository.save(checkinApplication);
    }

    @Override
    public List<CheckinApplicationBeforeCheckoutBrief> findAllUserCheckinApplicationsBeforeCheckout(String userId) throws UserNotFoundException, CheckinApplicationNotFoundException, RoomStateNotFoundException{

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        List<CheckinApplicationBeforeCheckoutBrief> checkinApplicationBeforeCheckoutBriefList = checkinApplicationRepository.findAllBeforeCheckout(user.getId());

        return checkinApplicationBeforeCheckoutBriefList;
    }

    @Override
    public CheckinApplicationBrief findUserLatestCheckinApplication(String userId) throws UserNotFoundException, CheckinApplicationNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        CheckinApplicationBrief checkinApplicationBrief = checkinApplicationRepository.findFirstByFirstVerifiedIsTrueAndSecondVerifiedIsTrueAndUserOrderByCreatedAtDesc(user);

        if (checkinApplicationBrief == null){
            throw new CheckinApplicationNotFoundException();
        }

        return checkinApplicationBrief;
//        return Objects.requireNonNullElse(checkinApplicationBrief, new String("checkin application not found !"));
    }

    @Override
    public Object findUserLatestFinishedCheckinApplication(String identityCard, String referralEmployeeId) throws UserNotFoundException {
        User user = userRepository.findByIdentityCardAndVerified(identityCard, true).orElseThrow(UserNotFoundException::new);
        CheckinApplicationDetail checkinApplicationDetail = checkinApplicationRepository.findFirstByFirstVerifiedIsNotNullAndSecondVerifiedIsNotNullAndUserOrderByCreatedAtDesc(user);
        return Objects.requireNonNullElseGet(checkinApplicationDetail, () -> new UserDetailResponse(user.getId(), user.getBirthday(), user.getChineseName(), user.getGender(), user.getAddress(), user.getCellphone(), user.getBloodType()) );
    }

    @Override
    public CheckinApplicationDetail findOwnedCheckinApplication(String checkinApplicationId, String id) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException {
        if (checkerUtil.hasRole("user")) {
            User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
            return checkinApplicationRepository.findByIdAndUser(checkinApplicationId, user);
        } else if (checkerUtil.hasRole("referral_employee")) {
            ReferralEmployee referralEmployee = referralEmployeeRepository.findById(id).orElseThrow(ReferralEmployeeNotFoundException::new);
            return checkinApplicationRepository.findByIdAndReferralEmployee(checkinApplicationId, referralEmployee);
        }
        throw new RoleNotFoundException();
    }

    @Override
    public UserCheckinApplicationSearchResultPagedResponse findUserCheckinApplicationSearchPaged(Date startDate, Date endDate, String houseName, int currentPage, String id) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Page<UserCheckinApplicationSearchResult> pageResult = checkinApplicationRepository.findAllBySearchCondition(user.getId(), startDate, endDate, houseName, PageRequest.of(currentPage,
                5,
                Sort.by("created_at").descending()));
        return new UserCheckinApplicationSearchResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public UserCheckinApplicationSearchResultPagedResponse findUserCheckinApplicationIntervalSearchPaged(String userId, Date startDate, Date endDate, int currentPage) throws UserNotFoundException, CheckinApplicationNotFoundException{

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Page<UserCheckinApplicationSearchResult> pageResult = checkinApplicationRepository.findAllBySearchCondition(user.getId(), startDate, endDate, PageRequest.of(currentPage,
                5,
                Sort.by("created_at").descending()));

        return new UserCheckinApplicationSearchResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public ReferralEmployeeCheckinApplicationSearchResultPagedResponse findReferralEmployeeCheckinApplicationSearchPaged(String userChineseName, Date startDate, Date endDate, String houseName, int currentPage, String id) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException, ReferralNotFoundException {
        if (userChineseName == null) {
            userChineseName = "";
        }
        ReferralEmployee referralEmployee = referralEmployeeRepository.findById(id).orElseThrow(ReferralNotFoundException::new);
        Page<ReferralEmployeeCheckinApplicationSearchResult> pageResult = checkinApplicationRepository.findAllBySearchCondition(referralEmployee.getId(), userChineseName, startDate, endDate, houseName, PageRequest.of(currentPage,
                5,
                Sort.by("created_at").descending()));
        return new ReferralEmployeeCheckinApplicationSearchResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public LandlordCheckinApplicationSearchResultPagedResponse findLandlordCheckinApplicationSearchPaged(String landlordId, String houseName, Integer roomNumber, Date startDate, Date endDate, int currentPage) throws ParseException{

        Page<LandlordCheckinApplicationSearchResult> pageResult = checkinApplicationRepository.findAllBySearchCondition(landlordId, houseName, roomNumber, startDate, endDate, PageRequest.of(currentPage,
                5,
                Sort.by("created_at").descending()));

        return new LandlordCheckinApplicationSearchResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public VolunteerCheckinApplicationSearchResultPagedResponse findVolunteerCheckinApplicationSearchPaged(String volunteerId, String houseName, Integer roomNumber, String yearAndMonth, int currentPage) throws ParseException{

        String startDateString = yearAndMonth + "-01 00:00:00";
        String endDateString = yearAndMonth + "-31 23:59:59";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = simpleDateFormat.parse(startDateString);
        Date endDate = simpleDateFormat.parse(endDateString);

        Page<VolunteerCheckinApplicationSearchResult> pageResult = checkinApplicationRepository.findAllBySearchCondition(houseName, roomNumber, startDate, endDate, PageRequest.of(currentPage,
                5,
                Sort.by("created_at").descending()));

        return new VolunteerCheckinApplicationSearchResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public FirmEmployeeCheckinApplicationResultPagedResponse findFirmEmployeeCheckinApplicationSearchPaged(String firmEmployeeId, String houseName, Integer roomNumber, String yearAndMonth, int currentPage) throws ParseException{

        String startDateString = yearAndMonth + "-01 00:00:00";
        String endDateString = yearAndMonth + "-31 23:59:59";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = simpleDateFormat.parse(startDateString);
        Date endDate = simpleDateFormat.parse(endDateString);

        Page<FirmEmployeeCheckinApplicationSearchResult> pageResult = checkinApplicationRepository.findAllBySearchCondition(startDate, endDate, houseName, roomNumber, PageRequest.of(currentPage,
                5,
                Sort.by("created_at").descending()));

        return new FirmEmployeeCheckinApplicationResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Transactional
    @Override
    public void uploadRentAndAffidavitImageFile(UploadRentAndAffidavitImageFileRequest request, String checkinApplicationId, String host, String userId) throws AffidavitImageTypeMismatchException, RentImageTypeMismatchException, UploadRentAndAffidavitImageException, UserNotFoundException {
        User user = userRepository.findByIdAndVerified(userId, true).orElseThrow(UserNotFoundException::new);
        String[] filePaths = storageUtil.storeCheckinApplicationMetaData(new CheckinApplicationMetaData(host, request.getRentImage(), request.getAffidavitImage()));
        checkinApplicationRepository.updateRentImageAndAffidavitImageById(filePaths[0], filePaths[1], checkinApplicationId, user);
    }

    @Override
    public String sendNewRoomState(String checkinAppId, SendNewRoomStateRequest request) throws CheckinApplicationNotFoundException, RoomStateChangeExistException, RoomStateDuplicatedException, LineNotificationException, ParseException {

        String newStartDateString = request.getNewStartDate() + " 00:00:00";
        String newEndDateString = request.getNewEndDate() + " 23:59:59";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date newStartDate = simpleDateFormat.parse(newStartDateString);
        Date newEndDate = simpleDateFormat.parse(newEndDateString);

        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinAppId).orElseThrow(CheckinApplicationNotFoundException::new);
        Boolean exist = roomStateChangeRepository.existsByRoomStateAndAdminVerifiedIsFalse(checkinApplication.getRoomState());
        List<RoomState> roomStateList = roomStateRepository.findAllBySearchCondition(checkinApplication.getRoomState().getId(), checkinApplication.getRoomState().getRoom().getId(), newStartDate, newEndDate);
        User user = checkinApplication.getUser();
        ReferralEmployee referralEmployee = checkinApplication.getReferralEmployee();

        if (roomStateList.isEmpty()){
            System.out.println("沒擋到");
        }else{
            System.out.println("擋到了啦");
        }

        if (exist.equals(true)) {

            throw new RoomStateChangeExistException();

        }else if(!roomStateList.isEmpty()){

            throw new RoomStateDuplicatedException();

        }else{

            RoomStateChange roomStateChange = new RoomStateChange();
            roomStateChange.setRoomState(checkinApplication.getRoomState());
            roomStateChange.setNewStartDate(newStartDate);
            roomStateChange.setNewEndDate(newEndDate);
            roomStateChange.setChangedItem(request.getChangedItem());
            roomStateChange.setReason(request.getReason());
            if (request.getChangedItem().equals("提前退房")){
                roomStateChange.setReferralVerified(true);
            }
            roomStateChangeRepository.save(roomStateChange);

            String user_msg = "您好，就醫民眾「" + user.getChineseName() + "」變更申請已送出，請等候審核，謝謝。\n\n感謝您使用本服務!";
            String referral_employee_msg = "您好，就醫民眾「" + user.getChineseName() + "」變更申請已送出，請點擊下方選單圖示的[需求變更]查看，謝謝。\n\n感謝您使用本服務!";

            try{
                lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, user_msg)));
            }catch (Exception e){
                System.out.println(new UserNotFoundException().getMessage());
            }

            try{
                lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
            }catch (Exception e){
                System.out.println(new ReferralEmployeeNotFoundException().getMessage());
            }
//            lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, user_msg)));
//            lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
        }

        return checkinAppId + " : send new roomState success ! ";
    }
}
