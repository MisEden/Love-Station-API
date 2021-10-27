package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.model.CheckinForm;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.enums.CheckinApplicationStage;
import org.eden.lovestation.dto.projection.*;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.dto.response.*;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.AdminService;
import org.eden.lovestation.util.checker.CheckerUtil;
import org.eden.lovestation.util.email.EmailUtil;
import org.eden.lovestation.util.excel.ExcelUtil;
import org.eden.lovestation.util.line.LineUtil;
import org.eden.lovestation.util.line.Message;
import org.eden.lovestation.util.line.MessageType;
import org.eden.lovestation.util.password.PasswordUtil;
import org.eden.lovestation.util.storage.HouseFullDegreePanoramaMetaData;
import org.eden.lovestation.util.storage.HouseImageMetaData;
import org.eden.lovestation.util.storage.HousePlanimetricMapMetaData;
import org.eden.lovestation.util.storage.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;

@Service
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final CheckinApplicationRepository checkinApplicationRepository;
    private final CheckinFormConfirmRepository checkinFormConfirmRepository;
    private final CheckinFormPrivateFurnitureRepository checkinFormPrivateFurnitureRepository;
    private final CheckinFormPublicFurnitureRepository checkinFormPublicFurnitureRepository;
    private final CheckinFormRepository checkinFormRepository;
    private final CheckoutFeedbackRepository checkoutFeedbackRepository;
    private final FirmEmployeeServiceRecordRepository firmEmployeeServiceRecordRepository;
    private final FirmEmployeesRepository firmEmployeesRepository;
    private final FirmRepository firmRepository;
    private final HouseRepository houseRepository;
    private final HouseImageRepository houseImageRepository;
    private final HousePrivateFurnitureRepository housePrivateFurnitureRepository;
    private final HousePublicFurnitureRepository housePublicFurnitureRepository;
    private final InvestigationRepository investigationRepository;
    private final LandlordRepository landlordRepository;
    private final LandlordServiceRecordRepository landlordServiceRecordRepository;
    private final PrivateFurnitureRepository privateFurnitureRepository;
    private final PublicFurnitureRepository publicFurnitureRepository;
    private final ReferralEmployeeRepository referralEmployeeRepository;
    private final ReferralRepository referralRepository;
    private final RoleRepository roleRepository;
    private final RoomRepository roomRepository;
    private final RoomStateRepository roomStateRepository;
    private final RoomStateChangeRepository roomStateChangeRepository;
    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;
    private final VolunteerServiceRecordRepository volunteerServiceRecordRepository;

    private final CheckerUtil checkerUtil;
    private final LineUtil lineUtil;
    private final EmailUtil emailUtil;
    private final ExcelUtil excelUtil;
    private final PasswordUtil passwordUtil;
    private final StorageUtil storageUtil;

    private final String commonMessage = "您好，在此通知您:\n";

    private final String assignedMessage = "分派給您，請登入帳號查看。\n";

    private final String roomStateChangeVerified = " 先生/小姐 申請變更 已通過。\n";
    private final String roomStateChangeVerifyDenied = " 先生/小姐 申請變更 已被拒絕。\n";

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository,
                            CheckinApplicationRepository checkinApplicationRepository,
                            CheckinFormConfirmRepository checkinFormConfirmRepository,
                            CheckinFormPrivateFurnitureRepository checkinFormPrivateFurnitureRepository,
                            CheckinFormPublicFurnitureRepository checkinFormPublicFurnitureRepository,
                            CheckinFormRepository checkinFormRepository,
                            CheckoutFeedbackRepository checkoutFeedbackRepository,
                            FirmEmployeeServiceRecordRepository firmEmployeeServiceRecordRepository,
                            FirmEmployeesRepository firmEmployeesRepository,
                            FirmRepository firmRepository,
                            HouseRepository houseRepository,
                            HouseImageRepository houseImageRepository,
                            HousePrivateFurnitureRepository housePrivateFurnitureRepository,
                            HousePublicFurnitureRepository housePublicFurnitureRepository,
                            InvestigationRepository investigationRepository,
                            LandlordRepository landlordRepository,
                            LandlordServiceRecordRepository landlordServiceRecordRepository,
                            PrivateFurnitureRepository privateFurnitureRepository,
                            PublicFurnitureRepository publicFurnitureRepository,
                            ReferralEmployeeRepository referralEmployeeRepository,
                            ReferralRepository referralRepository,
                            RoleRepository roleRepository,
                            RoomRepository roomRepository,
                            RoomStateRepository roomStateRepository,
                            RoomStateChangeRepository roomStateChangeRepository,
                            UserRepository userRepository,
                            VolunteerRepository volunteerRepository,
                            VolunteerServiceRecordRepository volunteerServiceRecordRepository,
                            CheckerUtil checkerUtil,
                            LineUtil lineUtil,
                            EmailUtil emailUtil,
                            ExcelUtil excelUtil,
                            PasswordUtil passwordUtil,
                            StorageUtil storageUtil) {

        this.adminRepository = adminRepository;
        this.checkinApplicationRepository = checkinApplicationRepository;
        this.checkinFormConfirmRepository = checkinFormConfirmRepository;
        this.checkinFormPrivateFurnitureRepository = checkinFormPrivateFurnitureRepository;
        this.checkinFormPublicFurnitureRepository = checkinFormPublicFurnitureRepository;
        this.checkinFormRepository = checkinFormRepository;
        this.checkoutFeedbackRepository = checkoutFeedbackRepository;
        this.firmEmployeeServiceRecordRepository = firmEmployeeServiceRecordRepository;
        this.firmEmployeesRepository = firmEmployeesRepository;
        this.firmRepository = firmRepository;
        this.houseRepository = houseRepository;
        this.houseImageRepository = houseImageRepository;
        this.housePrivateFurnitureRepository = housePrivateFurnitureRepository;
        this.housePublicFurnitureRepository = housePublicFurnitureRepository;
        this.investigationRepository = investigationRepository;
        this.landlordRepository = landlordRepository;
        this.landlordServiceRecordRepository = landlordServiceRecordRepository;
        this.privateFurnitureRepository = privateFurnitureRepository;
        this.publicFurnitureRepository = publicFurnitureRepository;
        this.referralEmployeeRepository = referralEmployeeRepository;
        this.referralRepository = referralRepository;
        this.roleRepository = roleRepository;
        this.roomRepository = roomRepository;
        this.roomStateRepository = roomStateRepository;
        this.roomStateChangeRepository = roomStateChangeRepository;
        this.userRepository = userRepository;
        this.volunteerRepository = volunteerRepository;
        this.volunteerServiceRecordRepository = volunteerServiceRecordRepository;

        this.checkerUtil = checkerUtil;
        this.lineUtil = lineUtil;
        this.emailUtil = emailUtil;
        this.excelUtil = excelUtil;
        this.passwordUtil = passwordUtil;
        this.storageUtil = storageUtil;
    }

    @Override
    public Admin register(RegisterAdministratorRequest request) throws RepeatedAccountException, RoleNotFoundException, RepeatedEmailException{
        Admin admin = checkerUtil.checkRegisterAdministratorMappingAndReturn(request);
        checkerUtil.checkRepeatedAccount(admin.getAccount());
        checkerUtil.checkRepeatedEmail(admin.getEmail());
        String plainPassword = admin.getPassword();
        String hashPassword = passwordUtil.generateHashPassword(plainPassword);
        admin.setPassword(hashPassword);

        return adminRepository.save(admin);
    }


    // Password
    @Override
    public void updateAdminPassword(String id, UpdateAdministratorPasswordRequest request) throws AdminNotFoundException, PasswordResetConfirmException, PasswordResetOriginalException {
        Admin admin = adminRepository.findById(id).orElseThrow(AdminNotFoundException::new);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new PasswordResetConfirmException();
        }else if(!encoder.matches(request.getOldPassword(), admin.getPassword())){
            System.out.println("[UpdatePassword] Admin(" + admin.getId() + ") / Origin：" + admin.getPassword() + " / Request：" + request.getOldPassword());
            throw new PasswordResetOriginalException();
        }

        String hashPassword_new = passwordUtil.generateHashPassword(request.getPassword());

        admin.setPassword(hashPassword_new);
        adminRepository.save(admin);
    }



    // index
    @Override
    public CheckinApplicationDetailPagedResponse findCheckinApplicationPaged(CheckinApplicationStage stage, int currentPage, String id) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException {
        Admin admin = adminRepository.findById(id).orElseThrow(AdminNotFoundException::new);
        return findAdminCheckinApplicationDetailPagedResponse(admin, stage, currentPage);
    }

    private CheckinApplicationDetailPagedResponse findAdminCheckinApplicationDetailPagedResponse(Admin admin, CheckinApplicationStage stage, int currentPage) throws CheckinApplicationStageEnumConvertException {
        Page<CheckinApplicationDetail> pageResult;
        switch (stage) {
            case FIRST:
                pageResult = checkinApplicationRepository.findAllByFirstVerifiedIsNullAndSecondVerifiedIsNull(PageRequest.of(currentPage,
                    5,
                    Sort.by("createdAt").descending()));
                break;
            case SECOND:
                pageResult = checkinApplicationRepository.findAllByRentImageIsNotNullAndAffidavitImageIsNotNullAndFirstVerifiedIsNotNullAndSecondVerifiedIsNullOrSecondVerifiedIsFalse(PageRequest.of(currentPage,
                    5,
                    Sort.by("createdAt").descending()));
                break;
            case FINISHED:
                pageResult = checkinApplicationRepository.findAllByFirstVerifiedIsNotNullAndSecondVerifiedIsNotNull(PageRequest.of(currentPage,
                    5,
                    Sort.by("createdAt").descending()));
                break;
            case ALL:
                pageResult = checkinApplicationRepository.findAllBy(PageRequest.of(currentPage,
                    5,
                    Sort.by("createdAt").descending()));
                break;
            default:
                throw new CheckinApplicationStageEnumConvertException();
        }
        return new CheckinApplicationDetailPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public AdminGetScheduledCheckinResponse getScheduledCheckinThatDay(String startDate, int currentPage) throws RoomNotFoundException, CheckinApplicationNotFoundException, UserNotFoundException, ParseException{

        String startDateString = startDate + " 00:00:00";
        String startDateEndString = startDate + " 23:59:59";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(startDateString);
        Date dateEnd = simpleDateFormat.parse(startDateEndString);

        Page<CheckinInfoAdminNeed> pageResult = checkinApplicationRepository.findAllScheduledCheckin(date, dateEnd, PageRequest.of(currentPage,
                5,
                Sort.by("created_at").descending()));

        return new AdminGetScheduledCheckinResponse(startDate, pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public AdminGetScheduledCheckoutResponse getScheduledCheckoutThatDay(String endDate, int currentPage) throws RoomNotFoundException, CheckinApplicationNotFoundException, UserNotFoundException, ParseException{

        String endDateString = endDate + " 00:00:00";
        String endDateEndString = endDate + " 23:59:59";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(endDateString);
        Date dateEnd = simpleDateFormat.parse(endDateEndString);

        Page<CheckoutInfoAdminNeed> pageResult = checkinApplicationRepository.findAllScheduledCheckout(date, dateEnd, PageRequest.of(currentPage,
                5,
                Sort.by("created_at").descending()));

        return new AdminGetScheduledCheckoutResponse(endDate, pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public CheckinActualTimeResponse getCheckinActualTime(String roomStateId) throws RoomStateNotFoundException, ParseException{

        RoomState roomState = roomStateRepository.findById(roomStateId).orElseThrow(RoomStateNotFoundException::new);
        CheckinForm checkinForm = new CheckinForm();
        Optional<CheckinForm> checkinFormOptional = checkinFormRepository.findByRoomState(roomState);

        if (checkinFormOptional.isPresent()){
            checkinForm = checkinFormOptional.get();
        }else {

            String s = "1999-01-01 00:00:00";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(s);

            return new CheckinActualTimeResponse(date, false);
        }

        return new CheckinActualTimeResponse(checkinForm.getCreatedAt(), true);

    }

    @Override
    public CheckoutActualTimeResponse getCheckoutActualTime(String roomStateId) throws RoomStateNotFoundException, ParseException{
        RoomState roomState = roomStateRepository.findById(roomStateId).orElseThrow(RoomStateNotFoundException::new);
        CheckoutFeedback checkoutFeedback = new CheckoutFeedback();
        Optional<CheckoutFeedback> checkoutFeedbackOptional = checkoutFeedbackRepository.findByRoomStates(roomState);

        if (checkoutFeedbackOptional.isPresent()){
            checkoutFeedback = checkoutFeedbackOptional.get();
        }else {

            String s = "1999-01-01 00:00:00";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(s);

            return new CheckoutActualTimeResponse(date, false);
        }
        return new CheckoutActualTimeResponse(checkoutFeedback.getCreatedAt(), true);
    }

    @Override
    public Map<String, Integer> getNonViewServiceNumber(){
        Map<String, Integer> nonViewNumber = new HashMap<String, Integer>();
        nonViewNumber.put("landlord", landlordServiceRecordRepository.getCountNonView());
        nonViewNumber.put("volunteer", volunteerServiceRecordRepository.getCountNonView());
        nonViewNumber.put("firm_employee", firmEmployeeServiceRecordRepository.getCountNonView());

        return nonViewNumber;
    }


    // CheckinApplication
    @Override
    public CheckinApplicationDetail findCheckinApplicationById(String id) throws CheckinApplicationNotFoundException {
        return checkinApplicationRepository.findDetailById(id).orElseThrow(CheckinApplicationNotFoundException::new);
    }

    @Override
    public AdminCheckinApplicationSearchResultPagedResponse findAdminCheckinApplicationSearchPaged_allHouse(String userChineseName, Date startDate, Date endDate, Integer roomNumber, String houseName, String first, String second, int currentPage, String id){
        if (userChineseName == null) {
            userChineseName = "";
        }
        if (roomNumber == null) {
            roomNumber = 0;
        }

        if (first == null) {
            first = "";
        }

        if (second == null) {
            second = "";
        }

        Page<AdminCheckinApplicationSearchResult> pageResult = checkinApplicationRepository.findAllBySearchCondition_allHouse(userChineseName, startDate, endDate, roomNumber, houseName, first, second, PageRequest.of(currentPage,
            5));
        return new AdminCheckinApplicationSearchResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public List<CheckinApplicationID> findAdminCheckinApplicationSearchPaged_allHouse_ID(String userChineseName, Date startDate, Date endDate, Integer roomNumber, String houseName, String first, String second, String id){
        if (userChineseName == null) {
            userChineseName = "";
        }
        if (roomNumber == null) {
            roomNumber = 0;
        }

        if (first == null) {
            first = "";
        }

        if (second == null) {
            second = "";
        }

        List<CheckinApplicationID> listResult = checkinApplicationRepository.findAllBySearchCondition_allHouse_ID(userChineseName, startDate, endDate, roomNumber, houseName, first, second);

        return listResult;
    }

    @Override
    public String createCheckinApplicationHistoryExportExcel(List<String> checkinApplicationId) throws CheckinApplicationNotFoundException, DownloadExportExcelException{
        List<String> title = new ArrayList<String>(
            Arrays.asList(new String[]{"入住棧點", "房號", "入住日期", "退房日期",
                "照顧者", "轉介單位", "轉介人員", "一階審核", "一階理由", "二階審核"}));
        List<List<String>> checkinApplicationExportData = new ArrayList<List<String>>();
        List<Integer> columnWidth = new ArrayList<Integer>(
            Arrays.asList(new Integer[]{15, 10, 15, 15, 15, 25, 15, 10, 25, 10}));


        for(int i = 0; i < checkinApplicationId.size(); i++){
            CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinApplicationId.get(i)).orElseThrow(CheckinApplicationNotFoundException::new);

            List<String> checkinApplicationRow = new ArrayList<>();
            checkinApplicationRow.add(checkinApplication.getHouse().getName());
            checkinApplicationRow.add(String.valueOf(checkinApplication.getRoom().getNumber()));

            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
            checkinApplicationRow.add(sdFormat.format(checkinApplication.getRoomState().getStartDate()));
            checkinApplicationRow.add(sdFormat.format(checkinApplication.getRoomState().getEndDate()));

            checkinApplicationRow.add(checkinApplication.getCaregiverName());
            checkinApplicationRow.add(checkinApplication.getReferralEmployee().getReferral().getHospitalChineseName());
            checkinApplicationRow.add(checkinApplication.getReferralEmployee().getName());

            if(checkinApplication.getFirstVerified() == null){
                checkinApplicationRow.add("");
            }else if(checkinApplication.getFirstVerified()){
                checkinApplicationRow.add("通過");
            }else{
                checkinApplicationRow.add("不通過");
            }

            checkinApplicationRow.add(checkinApplication.getFirstVerifiedReason());

            if(checkinApplication.getSecondVerified() == null){
                checkinApplicationRow.add("");
            }else if(checkinApplication.getSecondVerified()){
                checkinApplicationRow.add("通過");
            }else{
                checkinApplicationRow.add("不通過");
            }

            checkinApplicationExportData.add(checkinApplicationRow);
        }

        return excelUtil.getDataExcel(new ExportExcelRequest(title, checkinApplicationExportData, columnWidth));
    }

    @Transactional
    @Override
    public void updateFirstStageCheckinApplicationVerification(UpdateFirstStageCheckinApplicationVerificationRequest request, String checkinApplicationId, String adminId) throws RoomNotFoundException, DateFormatParseException, RoomOccupiedException, CheckinApplicationNotFoundException, AdminNotFoundException {
        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinApplicationId).orElseThrow(CheckinApplicationNotFoundException::new);
        Admin admin = adminRepository.findById(adminId).orElseThrow(AdminNotFoundException::new);
        Date adminDate = new Date();
        String roomId = request.getRoomId();
        String startDateStr = request.getStartDate();
        String endDateStr = request.getEndDate();
        Boolean firstVerified = request.getFirstVerified();
        String firstVerifiedReason = request.getFirstVerifiedReason();
        if (startDateStr == null || endDateStr == null) {
            if (!firstVerified) {
                roomStateRepository.deleteById(checkinApplication.getRoomState().getId());
            }
            checkinApplicationRepository.updateFirstStageStatusById(firstVerified, firstVerifiedReason, admin, adminDate, checkinApplicationId);
        }

        if (startDateStr != null && endDateStr != null && roomId != null) {
            String roomStateId = checkinApplication.getRoomState().getId();
            Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
            Date startDate = request.getStartDateConverted();
            Date endDate = request.getEndDateConverted();
            boolean isRoomOccupied = roomStateRepository.existsByIdNotAndRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndDeletedIsFalse(roomStateId, room, endDate, startDate);
            if (isRoomOccupied) {
                throw new RoomOccupiedException();
            }
            roomStateRepository.updateRoomStateById(room, startDate, endDate, roomStateId);
            House house = room.getHouse();
            checkinApplicationRepository.updateFirstStageStatusAndChangeRoomAndHouseById(firstVerified, firstVerifiedReason, room, house, admin, adminDate, checkinApplicationId);
        }
    }

    @Transactional
    @Override
    public void updateSecondStageCheckinApplicationVerification(UpdateSecondStageCheckinApplicationVerificationRequest request, String checkinApplicationId) throws CheckinApplicationNotFoundException {
        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinApplicationId).orElseThrow(CheckinApplicationNotFoundException::new);
        if (!request.getSecondVerified()) {
            roomStateRepository.deleteById(checkinApplication.getRoomState().getId());
        }
        roomStateRepository.updateStateById("已訂房", checkinApplication.getRoomState().getId());
        checkinApplicationRepository.updateSecondStageStatusById(request.getSecondVerified(), checkinApplicationId);
    }

    @Override
    public CheckinApplicationWithRoomStateChangeInfoAdminNeedResponse getRoomStateChange(int currentPage){

        Page<CheckinApplicationWithRoomStateChangeInfoAdminNeed> pageResult = checkinApplicationRepository.findAllBySearchCondition(PageRequest.of(currentPage,
                5,
                Sort.by("created_at").descending()));

        return new CheckinApplicationWithRoomStateChangeInfoAdminNeedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public CheckinApplicationWithRoomStateChangeDetail getRoomStateChangeDetail(String checkinAppId) throws CheckinApplicationNotFoundException, RoomStateChangeNotFoundException {
        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinAppId).orElseThrow(CheckinApplicationNotFoundException::new);
        CheckinApplicationWithRoomStateChangeDetail checkinApplicationWithRoomStateChangeDetail = checkinApplicationRepository.findBySearchCondition(checkinAppId);
        if (checkinApplicationWithRoomStateChangeDetail.equals(null)){
            throw new RoomStateChangeNotFoundException();
        }
        return checkinApplicationWithRoomStateChangeDetail;
    }

    @Override
    @Transactional
    public String updateRoomStateWithRoomStateChange(String checkinAppId, Boolean finalVerified, Date newStartDate, Date newEndDate, String deniedReason) throws CheckinApplicationNotFoundException, RoomStateChangeNotFoundException, RoomStateNotFoundException {

        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinAppId).orElseThrow(CheckinApplicationNotFoundException::new);
        RoomStateChange roomStateChange = roomStateChangeRepository.findByRoomStateAndReferralVerifiedAndAdminVerified(checkinApplication.getRoomState(), true, false).orElseThrow(RoomStateChangeNotFoundException::new);
        RoomState roomState = roomStateRepository.findById(checkinApplication.getRoomState().getId()).orElseThrow(RoomStateNotFoundException::new);

        if (finalVerified != true){

            roomStateChange.setDeniedReason(deniedReason);

            sendRoomStateChangeVerifyDeniedNotification(checkinApplication, roomStateChange);

        }else if(roomStateChange.getChangedItem().equals("取消入住")){

            roomState.setDeleted(true);
            roomStateRepository.save(roomState);

            sendRoomStateChangeVerifiedNotification(checkinApplication, roomStateChange);

        }else{

            // determine which new date have changed
            if (newStartDate == null && newEndDate == null) {
                roomState.setStartDate(roomStateChange.getNewStartDate());
                roomState.setEndDate(roomStateChange.getNewEndDate());
            } else if (newStartDate == null) {
                roomState.setStartDate(roomStateChange.getNewStartDate());
                roomState.setEndDate(newEndDate);
            } else if (newEndDate == null) {
                roomState.setStartDate(newStartDate);
                roomState.setEndDate(roomStateChange.getNewEndDate());
            } else {
                roomState.setStartDate(newStartDate);
                roomState.setEndDate(newEndDate);
            }

            roomStateRepository.save(roomState);

            sendRoomStateChangeVerifiedNotification(checkinApplication, roomStateChange);

        }

        roomStateChange.setAdminVerified(true);
        roomStateChangeRepository.save(roomStateChange);

        return "checkinAppId : " + checkinAppId + " verified room state change success ! ";
    }

    private void sendRoomStateChangeVerifiedNotification(CheckinApplication checkinApplication, RoomStateChange roomStateChange){

        User user = checkinApplication.getUser();
        ReferralEmployee referralEmployee = checkinApplication.getReferralEmployee();
        Landlord landlord = checkinApplication.getHouse().getLandlord();
        Volunteer volunteer = checkinApplication.getVolunteer();
        FirmEmployees firmEmployees = checkinApplication.getFirmEmployees();

        String roomStateChangeInfo = new String();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newStartDateStr = sdf.format(roomStateChange.getNewStartDate());
        String newEndDateStr = sdf.format(roomStateChange.getNewEndDate());

        if (roomStateChange.getChangedItem().equals("取消入住")){
            roomStateChangeInfo = roomStateChange.getChangedItem();
        }else{
            roomStateChangeInfo = "\n變更項目 : " + roomStateChange.getChangedItem() + "\n變更後入住時間 : " + newStartDateStr + "\n變更後退房時間 : " + newEndDateStr;
        }

        String user_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId() + ")" + roomStateChangeVerified + roomStateChangeInfo;
        String referral_employee_msg = commonMessage + user.getChineseName() + "(轉介編號：" + user.getReferralNumber().getId() + ")" + roomStateChangeVerified + roomStateChangeInfo;
        String landlord_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId() + ")" + roomStateChangeVerified + roomStateChangeInfo;
        String volunteer_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId() + ")" + roomStateChangeVerified + roomStateChangeInfo;
        String firm_employee_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId() + ")" + roomStateChangeVerified + roomStateChangeInfo;

//        lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, user_msg)));
//        lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
//        lineUtil.sendLandlordBotNotification(landlord.getLineId(), List.of(new Message(MessageType.TEXT, landlord_msg)));
//        lineUtil.sendVolunteerBotNotification(volunteer.getLineId(), List.of(new Message(MessageType.TEXT, volunteer_msg)));
//        lineUtil.sendFirmEmployeeBotNotification(firmEmployees.getLineId(), List.of(new Message(MessageType.TEXT, firm_employee_msg)));

        try {
            lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, user_msg)));
        } catch (Exception e) {
            System.out.println(new UserNotFoundException().getMessage());
        }

        try {
            lineUtil.sendVolunteerBotNotification(volunteer.getLineId(), List.of(new Message(MessageType.TEXT, volunteer_msg)));
        } catch (Exception e) {
            System.out.println(new VolunteerNotFoundException().getMessage());
        }

        try {
            lineUtil.sendFirmEmployeeBotNotification(firmEmployees.getLineId(), List.of(new Message(MessageType.TEXT, firm_employee_msg)));
        } catch (Exception e) {
            System.out.println(new FirmEmployeeNotFoundException().getMessage());
        }

        try {
            lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
        } catch (Exception e) {
            System.out.println(new ReferralEmployeeNotFoundException().getMessage());
        }

        try {
            lineUtil.sendLandlordBotNotification(landlord.getLineId(), List.of(new Message(MessageType.TEXT, landlord_msg)));
        } catch (Exception e) {
            System.out.println(new LandlordNotFoundException().getMessage());
        }

    }

    private void sendRoomStateChangeVerifyDeniedNotification(CheckinApplication checkinApplication, RoomStateChange roomStateChange){
        User user = checkinApplication.getUser();
        ReferralEmployee referralEmployee = checkinApplication.getReferralEmployee();

        String user_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId() + ")" + roomStateChangeVerifyDenied;
        String referral_employee_msg = commonMessage + user.getChineseName() + "(轉介編號：" + user.getReferralNumber().getId() + ")" + roomStateChangeVerifyDenied;

        try {
            lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, user_msg)));
        } catch (Exception e) {
            System.out.println(new UserNotFoundException().getMessage());
        }

        try {
            lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
        } catch (Exception e) {
            System.out.println(new ReferralEmployeeNotFoundException().getMessage());
        }

//        lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, user_msg)));
//        lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_msg)));

    }

    @Override
    public AdminCheckinApplicationBriefPagedResponse getCheckinApplicationWithVolunteerDateAndFirmEmployeeDate(String yearAndMonth, int currentPage) throws CheckinApplicationNotFoundException, ParseException {

        String startDateStr = yearAndMonth + "-01 00:00:00";
        String endDateStr = yearAndMonth + "-31 23:59:59";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = simpleDateFormat.parse(startDateStr);
        Date endDate = simpleDateFormat.parse(endDateStr);

        List<RoomState> roomStateList = roomStateRepository.findAllByStartDateBetween(startDate, endDate);

        Page<CheckinApplicationBrief> pageResult = checkinApplicationRepository.findAllByVolunteerDateIsNullAndFirmEmployeeDateIsNullAndSecondVerifiedAndRoomStateIn(true, roomStateList, PageRequest.of(currentPage,
                5,
                Sort.by("createdAt").descending()));

        return new AdminCheckinApplicationBriefPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    @Transactional
    public String assignVolunteerAndFirmEmployee(AssignVolunteerAndFirmEmployeeRequest request) throws CheckinApplicationNotFoundException, VolunteerNotFoundException, FirmEmployeeNotFoundException, LineNotificationException{

        CheckinApplication checkinApplication = checkinApplicationRepository.findById(request.getCheckinAppId()).orElseThrow(CheckinApplicationNotFoundException::new);
        Volunteer volunteer = volunteerRepository.findById(request.getVolunteerId()).orElseThrow(VolunteerNotFoundException::new);
        FirmEmployees firmEmployees = firmEmployeesRepository.findById(request.getFirmEmployeeId()).orElseThrow(FirmEmployeeNotFoundException::new);

        User user = checkinApplication.getUser();

        checkinApplication.setVolunteer(volunteer);
        checkinApplication.setVolunteerDate(new Date());
        checkinApplication.setFirmEmployees(firmEmployees);
        checkinApplication.setFirmEmployeeDate(new Date());
        checkinApplicationRepository.save(checkinApplication);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = sdf.format(checkinApplication.getRoomState().getStartDate());
        String endDateStr = sdf.format(checkinApplication.getRoomState().getEndDate());

        String msg = commonMessage + "管理員已將 " + user.getChineseName() + "(申請編號：" + checkinApplication.getId().substring(0, 5) + "...)" + assignedMessage
                + "\n入住棧點 : " + checkinApplication.getHouse().getName() + "\n入住時間 : " + startDateStr + "\n退房時間 : " + endDateStr;;

        lineUtil.sendVolunteerBotNotification(volunteer.getLineId(), List.of(new Message(MessageType.TEXT, msg)));
        lineUtil.sendFirmEmployeeBotNotification(firmEmployees.getLineId(), List.of(new Message(MessageType.TEXT, msg)));

        return "assign and notice success ! ";
    }

    @Override
    public AdminCheckinApplicationSearchResultPagedResponse findCheckinApplicationSearchPagedForAssignedChange(String userChineseName, Date startDate, Date endDate, Integer roomNumber, String houseName, int currentPage, String id) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException {
        if (userChineseName == null) {
            userChineseName = "";
        }
        if (roomNumber == null) {
            roomNumber = 0;
        }
//        Admin admin = adminRepository.findById(id).orElseThrow(AdminNotFoundException::new);
        Page<AdminCheckinApplicationSearchResult> pageResult = checkinApplicationRepository.findAllBySearchCondition(userChineseName, startDate, endDate, roomNumber, houseName, PageRequest.of(currentPage,
                5,
                Sort.by("created_at").descending()));
        return new AdminCheckinApplicationSearchResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }




    // check_account
    @Override
    public UserRegisterVerificationPagedResponse findUserRegisterVerificationPaged(int currentPage) {
        Page<UserRegisterVerification> pageResult = userRepository.findAllByVerifiedIsNull(PageRequest.of(currentPage,
            5, Sort.by("createdAt").descending()));
        return new UserRegisterVerificationPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Transactional
    @Override
    public void updateUserRegisterVerification(UpdateUserRegisterVerificationRequest request, String userId) {
        userRepository.updateVerificationById(request.getVerified(), userId);
    }

    @Override
    public ReferralEmployeeRegisterVerificationPagedResponse findReferralEmployeeRegisterVerificationPaged(int currentPage) {
        Page<ReferralEmployeeRegisterVerification> pageResult = referralEmployeeRepository.findAllByVerifiedIsNull(PageRequest.of(currentPage,
                5, Sort.by("createdAt").descending()));
        return new ReferralEmployeeRegisterVerificationPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Transactional
    @Override
    public void updateReferralEmployeeRegisterVerification(UpdateReferralEmployeeRegisterVerificationRequest request, String referralEmployeeId) {
        referralEmployeeRepository.updateVerificationById(request.getVerified(), referralEmployeeId);
    }

    @Override
    public LandlordRegisterVerificationPagedResponse findLandlordRegisterVerificationPaged(int currentPage) {
        Page<LandlordRegisterVerification> pageResult = landlordRepository.findAllByVerifiedIsNull(PageRequest.of(currentPage,
                5, Sort.by("createdAt").descending()));
        return new LandlordRegisterVerificationPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Transactional
    @Override
    public void updateLandlordRegisterVerification(UpdateLandlordRegisterVerificationRequest request, String landlordId) {
        landlordRepository.updateVerificationById(request.getVerified(), landlordId);
    }

    @Override
    public VolunteerRegisterVerificationPagedResponse findVolunteerRegisterVerificationPaged(int currentPage) {
        Page<VolunteerRegisterVerification> pageResult = volunteerRepository.findAllByVerifiedIsNull(PageRequest.of(currentPage,
                5, Sort.by("createdAt").descending()));
        return new VolunteerRegisterVerificationPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Transactional
    @Override
    public void updateVolunteerRegisterVerification(UpdateVolunteerRegisterVerificationRequest request, String volunteerId) {
        volunteerRepository.updateVerificationById(request.getVerified(), volunteerId);
    }

    @Override
    public FirmEmployeeRegisterVerificationPagedResponse findFirmEmployeeRegisterVerification(int currentPage) {
        Page<FirmEmployeeRegisterVerification> pageResult = firmEmployeesRepository.findAllByVerifiedIsNull(PageRequest.of(currentPage,
                5, Sort.by("created_at").descending()));
        return new FirmEmployeeRegisterVerificationPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Transactional
    @Override
    public void updateFirmEmployeeRegisterVerification(UpdateFirmEmployeeRegisterVerificationRequest request, String firmEmployeeId) {
        firmEmployeesRepository.updateVerificationById(request.getVerified(), firmEmployeeId);
    }




    // Role - Referral
    @Override
    public ReferralSearchResultPagedResponse findReferralByKeyword(String keyword, int currentPage){
        Page<ReferralSearchResult> pageResult = referralRepository.findAllBySearchCondition(keyword, PageRequest.of(currentPage,
            10));

        return new ReferralSearchResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public List<ReferralSearchResult> findReferralByKeywordWithoutPage(String keyword){
        List<ReferralSearchResult> listResult = referralRepository.findAllBySearchConditionWithoutPage(keyword);

        return listResult;
    }

    // Role - Landlord
    @Override
    public List<LandlordName> getLandlordNames() throws LandlordNotFoundException {

        List<LandlordName> landlordNameList = landlordRepository.findAllLandlordNames();
        if (landlordNameList.isEmpty()){
            throw new LandlordNotFoundException();
        }

        return landlordNameList;
    }

    @Override
    public LandlordContactSearchResultPagedResponse findLandlordContactByKeyword(String keyword, int currentPage){
        List<LandlordContact> pageResult = landlordRepository.findAllContactBySearchConditionWithoutPage();

        // Remove row of no relationship with keyword
        for(int i = pageResult.size() - 1; i >= 0 ; i--){

            boolean likeChineseName = pageResult.get(i).getChineseName().contains(keyword);

            boolean likeEnglishName = false;
            try{
                likeEnglishName = pageResult.get(i).getEnglishName().contains(keyword);
            }catch(Exception e){

            }

            boolean likeGender = pageResult.get(i).getGender().contains(keyword);
            boolean likeEmail = pageResult.get(i).getEmail().contains(keyword);
            boolean likeAddress = pageResult.get(i).getAddress().contains(keyword);
            boolean likeCellphone = pageResult.get(i).getCellphone().contains(keyword);
            boolean likeHouseName = false;
            if(pageResult.get(i).getHousesName() != null){
                likeHouseName = pageResult.get(i).getHousesName().contains(keyword);
            }

            if(!likeChineseName && !likeEnglishName && !likeGender && !likeEmail &&
                !likeAddress && !likeCellphone && !likeHouseName){
                pageResult.remove(i);
            }
        }


        // build return page data
        int pageRowNumber = 10;
        int totalPage = (int) Math.ceil((float)pageResult.size() / (float)pageRowNumber);

        List<LandlordContact> returnPageResult = new ArrayList<>();
        int startIndex = 0 + (currentPage * pageRowNumber);
        int endIndex_addFive = startIndex + pageRowNumber;
        int endIndex = (endIndex_addFive > pageResult.size()) ? pageResult.size() : endIndex_addFive;
        for(int j = startIndex; j < endIndex; j++){
            returnPageResult.add(pageResult.get(j));
        }

        return new LandlordContactSearchResultPagedResponse(returnPageResult, currentPage, totalPage);
    }

    @Override
    public List<LandlordContact> findLandlordContactByKeywordWithoutPage(String keyword){
        List<LandlordContact> pageResult = landlordRepository.findAllContactBySearchConditionWithoutPage();

        // Remove row of no relationship with keyword
        for(int i = pageResult.size() - 1; i >= 0 ; i--){

            boolean likeChineseName = pageResult.get(i).getChineseName().contains(keyword);
            boolean likeEnglishName = false;
            try{
                likeEnglishName = pageResult.get(i).getEnglishName().contains(keyword);
            }catch(Exception e){

            }
            boolean likeGender = pageResult.get(i).getGender().contains(keyword);
            boolean likeEmail = pageResult.get(i).getEmail().contains(keyword);
            boolean likeAddress = pageResult.get(i).getAddress().contains(keyword);
            boolean likeCellphone = pageResult.get(i).getCellphone().contains(keyword);
            boolean likeHouseName = false;
            if(pageResult.get(i).getHousesName() != null){
                likeHouseName = pageResult.get(i).getHousesName().contains(keyword);
            }

            if(!likeChineseName && !likeEnglishName && !likeGender && !likeEmail &&
                !likeAddress && !likeCellphone && !likeHouseName){
                pageResult.remove(i);
            }
        }

        return pageResult;
    }

    // Role - Volunteer
    @Override
    public List<VolunteerName> getVolunteerNames(){
        return volunteerRepository.findAllName();
    }

    @Override
    public VolunteerContactSearchResultPagedResponse findVolunteerContactByKeyword(String keyword, int currentPage){
        Page<VolunteerContact> pageResult = volunteerRepository.findAllContactBySearchCondition(keyword, PageRequest.of(currentPage,
            10));

        return new VolunteerContactSearchResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public List<VolunteerContact> findVolunteerContactByKeywordWithoutPage(String keyword){
        return volunteerRepository.findAllContactBySearchConditionWithoutPage(keyword);
    }

    // Role - Firm
    @Override
    public List<FirmName> getFirmNames(){
        return firmRepository.findAllFirmNames();
    }

    @Override
    public FirmSearchResultPagedResponse findFirmsByKeyword(String keyword, int currentPage){
        Page<FirmDetail> pageResult = firmRepository.findAllBySearchCondition(keyword, PageRequest.of(currentPage,
            10));

        return new FirmSearchResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public List<FirmDetail> findFirmsByKeywordWithoutPage(String keyword){
        List<FirmDetail> listResult = firmRepository.findAllBySearchConditionWithoutPage(keyword);

        return listResult;
    }

    @Override
    public List<FirmEmployeeName> getFirmEmployeeNames(){
        return firmEmployeesRepository.findAllByVerifiedIsNotNull();
    }



    // Partners - Referrals
    @Override
    public Referral insertReferral(AdminUpdateReferral request) throws IncorrectFormatCityException, RepeatedReferralHospitalChineseNameException, RepeatedReferralHospitalEnglishNameException, RepeatedReferralNumberException{
        int countByHospitalChineseName = referralRepository.countByHospitalChineseName(request.getHospitalChineseName());
        if(countByHospitalChineseName > 0){
            throw new RepeatedReferralHospitalChineseNameException();
        }

        int countByHospitalEnglishName = referralRepository.countByHospitalEnglishName(request.getHospitalEnglishName());
        if(countByHospitalEnglishName > 0){
            throw new RepeatedReferralHospitalEnglishNameException();
        }

        int countByReferralNumber = referralRepository.countByReferralNumber(request.getNumber());
        if(countByReferralNumber > 0){
            throw new RepeatedReferralNumberException();
        }

        List<String> counties = Arrays.asList("台北市", "基隆市", "新北市", "宜蘭縣", "桃園市", "新竹市",
            "新竹縣", "苗栗縣", "台中市", "彰化縣", "南投縣", "嘉義市", "嘉義縣", "雲林縣",
            "台南市", "高雄市","澎湖縣", "金門縣", "屏東縣", "台東縣", "花蓮縣", "連江縣");
        if(!counties.contains(request.getCity())){
            throw new IncorrectFormatCityException();
        }

        Referral referral = new Referral();
        referral.setCity(request.getCity());
        referral.setHospitalChineseName(request.getHospitalChineseName());
        referral.setHospitalEnglishName(request.getHospitalEnglishName());
        referral.setNumber(request.getNumber());

        return referralRepository.save(referral);
    }

    @Override
    public Referral updateReferral(AdminUpdateReferral request, String id) throws ReferralNotFoundException, IncorrectFormatCityException, RepeatedReferralHospitalChineseNameException, RepeatedReferralHospitalEnglishNameException, RepeatedReferralNumberException {
        Referral referral = referralRepository.findById(id).orElseThrow(ReferralNotFoundException::new);

        int countByHospitalChineseName = referralRepository.countByHospitalChineseName(request.getHospitalChineseName());
        if(countByHospitalChineseName == 1 && !request.getHospitalChineseName().equals(referral.getHospitalChineseName())){
            throw new RepeatedReferralHospitalChineseNameException();
        }else if(countByHospitalChineseName > 0 && countByHospitalChineseName != 1){
            throw new RepeatedReferralHospitalChineseNameException();
        }

        int countByHospitalEnglishName = referralRepository.countByHospitalEnglishName(request.getHospitalEnglishName());
        if(countByHospitalEnglishName == 1 && !request.getHospitalEnglishName().equals(referral.getHospitalEnglishName())){
            throw new RepeatedReferralHospitalEnglishNameException();
        }else if(countByHospitalEnglishName > 0 && countByHospitalEnglishName != 1){
            throw new RepeatedReferralHospitalEnglishNameException();
        }

        int countByReferralNumber = referralRepository.countByReferralNumber(request.getNumber());
        if(countByReferralNumber == 1 && !request.getNumber().equals(referral.getNumber())){
            throw new RepeatedReferralNumberException();
        }else if(countByReferralNumber > 0 && countByReferralNumber != 1){
            throw new RepeatedReferralNumberException();
        }

        List<String> counties = Arrays.asList("台北市", "基隆市", "新北市", "宜蘭縣", "桃園市", "新竹市",
            "新竹縣", "苗栗縣", "台中市", "彰化縣", "南投縣", "嘉義市", "嘉義縣", "雲林縣",
            "台南市", "高雄市","澎湖縣", "金門縣", "屏東縣", "台東縣", "花蓮縣", "連江縣");
        if(!counties.contains(request.getCity())){
            throw new IncorrectFormatCityException();
        }

        referral.setHospitalChineseName(request.getHospitalChineseName());
        referral.setHospitalEnglishName(request.getHospitalEnglishName());
        referral.setNumber(request.getNumber());
        referral.setCity(request.getCity());

        return referralRepository.save(referral);
    }

    @Override
    public String createReferralExportExcel(List<String> referralId) throws ReferralNotFoundException, DownloadExportExcelException{
        List<String> title = new ArrayList<String>(
            Arrays.asList(new String[]{"單位縣市", "中文名稱", "英文名稱", "轉介編號"}));
        List<List<String>> referralExportData = new ArrayList<List<String>>();
        List<Integer> columnWidth = new ArrayList<Integer>(
            Arrays.asList(new Integer[]{10, 20, 30, 15}));

        for(int i = 0; i < referralId.size(); i++){
            Referral referral = referralRepository.findById(referralId.get(i)).orElseThrow(ReferralNotFoundException::new);

            List<String> referralRow = new ArrayList<>();
            referralRow.add(referral.getCity());
            referralRow.add(referral.getHospitalChineseName());
            referralRow.add(referral.getHospitalEnglishName());
            referralRow.add(referral.getNumber());

            referralExportData.add(referralRow);
        }

        return excelUtil.getDataExcel(new ExportExcelRequest(title, referralExportData, columnWidth));
    }

    // Partners - Firm
    @Override
    public Firm insertFirm(AdminUpdateFirm request) throws RepeatedFirmNameException{
        Firm firm = new Firm();

        int countByName = firmRepository.countByName(request.getName());
        if(countByName == 1 && !request.getName().equals(firm.getName())){
            throw new RepeatedFirmNameException();
        }else if(countByName > 0 && countByName != 1){
            throw new RepeatedFirmNameException();
        }

        firm.setName(request.getName());
        firm.setAddress(request.getAddress());
        firm.setPhone(request.getPhone());
        firm.setContactPeople(request.getContactPeople());
        firm.setContactTitle(request.getContactTitle());
        firm.setContactPhone(request.getContactPhone());
        firm.setContactEmail(request.getContactEmail());

        return firmRepository.save(firm);
    }

    @Override
    public Firm updateFirm(AdminUpdateFirm request, String id) throws FirmNotFoundException, RepeatedFirmNameException {
        Firm firm = firmRepository.findById(id).orElseThrow(FirmNotFoundException::new);

        int countByName = firmRepository.countByName(request.getName());
        if(countByName == 1 && !request.getName().equals(firm.getName())){
            throw new RepeatedFirmNameException();
        }else if(countByName > 0 && countByName != 1){
            throw new RepeatedFirmNameException();
        }

        firm.setName(request.getName());
        firm.setAddress(request.getAddress());
        firm.setPhone(request.getPhone());
        firm.setContactPeople(request.getContactPeople());
        firm.setContactTitle(request.getContactTitle());
        firm.setContactPhone(request.getContactPhone());
        firm.setContactEmail(request.getContactEmail());

        return firmRepository.save(firm);
    }

    public String createFirmExportExcel(List<String> firmsId) throws FirmNotFoundException, DownloadExportExcelException {
        List<String> title = new ArrayList<String>(
            Arrays.asList(new String[]{"廠商名稱", "廠商地址", "廠商電話",
                "聯絡人姓名", "聯絡人頭銜", "聯絡人電話", "聯絡人信箱"}));
        List<List<String>> firmsExportData = new ArrayList<List<String>>();
        List<Integer> columnWidth = new ArrayList<Integer>(
            Arrays.asList(new Integer[]{20, 30, 12, 10, 10, 16, 30}));


        for(int i = 0; i < firmsId.size(); i++){
            Firm firm = firmRepository.findById(firmsId.get(i)).orElseThrow(FirmNotFoundException::new);

            List<String> firmRow = new ArrayList<>();
            firmRow.add(firm.getName());
            firmRow.add(firm.getAddress());
            firmRow.add(firm.getPhone());
            firmRow.add(firm.getContactPeople());
            firmRow.add(firm.getContactTitle());
            firmRow.add(firm.getContactPhone());
            firmRow.add(firm.getContactEmail());

            firmsExportData.add(firmRow);
        }

        return excelUtil.getDataExcel(new ExportExcelRequest(title, firmsExportData, columnWidth));
    }

    //Partners - Landlord
    @Override
    public Landlord updateLandlordById(AdminUpdateLandlordContactRequest request, String id) throws LandlordNotFoundException {
        Landlord landlord = landlordRepository.findById(id).orElseThrow(LandlordNotFoundException::new);

        landlord.setEmail(request.getEmail());
        landlord.setAddress(request.getAddress());
        landlord.setPhone(request.getPhone());
        landlord.setCellphone(request.getCellphone());

        return  landlordRepository.save(landlord);
    }

    @Override
    public String createLandlordExportExcel(List<String> landlordId) throws LandlordNotFoundException, DownloadExportExcelException{
        List<String> title = new ArrayList<String>(
            Arrays.asList(new String[]{"房東姓名", "房東性別", "房東棧點",
                "房東信箱", "房東住址", "房東手機", "房東電話"}));
        List<List<String>> exportData = new ArrayList<List<String>>();
        List<Integer> columnWidth = new ArrayList<Integer>(
            Arrays.asList(new Integer[]{12, 10, 15, 30, 30, 15, 15}));

        for(int i = 0; i < landlordId.size(); i++){
            Landlord landlord = landlordRepository.findById(landlordId.get(i)).orElseThrow(LandlordNotFoundException::new);
            List<HouseName> houseNameList = houseRepository.findAllByLandlordId(landlord.getId());

            String houseNameString = "";
            for(int j = 0; j < houseNameList.size(); j++){
                HouseName houseName = houseNameList.get(j);
                if(j == houseNameList.size()-1){
                    houseNameString += houseName.getName();
                }else{
                    houseNameString += houseName.getName() + ", ";
                }
            }

            List<String> landlordRow = new ArrayList<>();
            landlordRow.add(landlord.getChineseName());
            landlordRow.add(landlord.getGender());
            landlordRow.add(houseNameString);
            landlordRow.add(landlord.getEmail());
            landlordRow.add(landlord.getAddress());
            landlordRow.add(landlord.getCellphone());
            landlordRow.add(landlord.getPhone());

            exportData.add(landlordRow);
        }

        return excelUtil.getDataExcel(new ExportExcelRequest(title, exportData, columnWidth));
    }

    // Partners - Volunteer
    @Override
    public Volunteer updateVolunteerContactById(AdminUpdateVolunteerContact request, String id) throws VolunteerNotFoundException{
        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(VolunteerNotFoundException::new);

        volunteer.setEmail(request.getEmail());
        volunteer.setAddress(request.getAddress());
        volunteer.setPhone(request.getPhone());
        volunteer.setCellphone(request.getCellphone());

        return volunteerRepository.save(volunteer);
    }

    @Override
    public String createVolunteerExportExcel(List<String> volunteerId) throws VolunteerNotFoundException, DownloadExportExcelException{
        List<String> title = new ArrayList<String>(
            Arrays.asList(new String[]{"志工姓名", "志工性別",
                "志工信箱", "志工住址", "志工手機", "志工電話"}));
        List<List<String>> exportData = new ArrayList<List<String>>();
        List<Integer> columnWidth = new ArrayList<Integer>(
            Arrays.asList(new Integer[]{12, 10, 30, 30, 15, 15}));

        for(int i = 0; i < volunteerId.size(); i++){
            Volunteer volunteer = volunteerRepository.findById(volunteerId.get(i)).orElseThrow(VolunteerNotFoundException::new);


            List<String> volunteerRow = new ArrayList<>();
            volunteerRow.add(volunteer.getChineseName());
            volunteerRow.add(volunteer.getGender());
            volunteerRow.add(volunteer.getEmail());
            volunteerRow.add(volunteer.getAddress());
            volunteerRow.add(volunteer.getCellphone());
            volunteerRow.add(volunteer.getPhone());

            exportData.add(volunteerRow);
        }

        return excelUtil.getDataExcel(new ExportExcelRequest(title, exportData, columnWidth));
    }



    // Feedback
    @Override
    public AdminCheckinApplicationSearchResultPagedResponse findAdminCheckinApplicationSearchPaged(String userChineseName, Date startDate, Date endDate, Integer roomNumber, String houseName, int currentPage, String id) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException {
        if (userChineseName == null) {
            userChineseName = "";
        }
        if (roomNumber == null) {
            roomNumber = 0;
        }
//        Admin admin = adminRepository.findById(id).orElseThrow(AdminNotFoundException::new);
        Page<AdminCheckinApplicationSearchResult> pageResult = checkinApplicationRepository.findAllBySearchCondition(userChineseName, startDate, endDate, roomNumber, houseName, PageRequest.of(currentPage,
            5,
            Sort.by("created_at").descending()));
        return new AdminCheckinApplicationSearchResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public Map<String, Object> getCheckinForm(String checkinApplicationId) throws CheckinFormNotFoundException, CheckinFormConfirmNotFoundException, CheckinApplicationNotFoundException {
        //Find the data row of CheckinApplication
        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinApplicationId).orElseThrow(CheckinApplicationNotFoundException::new);

        //Find the data row of form, and verify id
        CheckinForm checkinForm = checkinFormRepository.findByRoomState(checkinApplication.getRoomState()).orElseThrow(CheckinFormNotFoundException::new);

        //Find the data row of checkin_confirm
        CheckinFormConfirm checkinFormConfirm = checkinFormConfirmRepository.findByCheckinForm(checkinForm).orElseThrow(CheckinFormConfirmNotFoundException::new);

        //Find the data row of checkin_publicFurniture
        List<String> brokenPublicFurnitures = new ArrayList<>();
        List<CheckinFormPublicFurniture> checkinFormPublicFurniture = checkinFormPublicFurnitureRepository.findAllByCheckinForm(checkinForm);
        for(int i = 0; i < checkinFormPublicFurniture.size(); i++){
            if(checkinFormPublicFurniture.get(i).getBroken() == true){
                brokenPublicFurnitures.add(checkinFormPublicFurniture.get(i).getPublicFurniture().getName());
            }
        }

        //Find the data row of checkin_privateFurniture
        List<String> brokenPrivateFurnitures = new ArrayList<>();
        List<CheckinFormPrivateFurniture> checkinFormPrivateFurnitures = checkinFormPrivateFurnitureRepository.findAllByCheckinForm(checkinForm);
        for(int i = 0; i < checkinFormPrivateFurnitures.size(); i++){
            if(checkinFormPrivateFurnitures.get(i).getBroken() == true){
                brokenPrivateFurnitures.add(checkinFormPrivateFurnitures.get(i).getPrivateFurniture().getName());
            }
        }

        Map<String, Object> checkinFormResult = new HashMap<String, Object>();
        checkinFormResult.put("roomStateId", checkinForm.getRoomState().getId());
        checkinFormResult.put("roomId",checkinForm.getRoomState().getRoom().getId());
        checkinFormResult.put("brokenPublicFurnitures", brokenPublicFurnitures);
        checkinFormResult.put("brokenPrivateFurnitures", brokenPrivateFurnitures);
        checkinFormResult.put("lock", checkinFormConfirm.getLock());
        checkinFormResult.put("power", checkinFormConfirm.getPower());
        checkinFormResult.put("convention", checkinFormConfirm.getConvention());
        checkinFormResult.put("contract", checkinFormConfirm.getContract());
        checkinFormResult.put("security", checkinFormConfirm.getSecurity());
        checkinFormResult.put("heater", checkinFormConfirm.getHeater());

        return checkinFormResult;
    }

    @Override
    public CheckoutFeedbackResponse getUserFeedback(String checkinAppId) throws RoomStateNotFoundException, CheckinApplicationNotFoundException, CheckoutFormNotFoundException {
        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinAppId).orElseThrow(CheckinApplicationNotFoundException::new);
        RoomState roomState = roomStateRepository.findById(checkinApplication.getRoomState().getId()).orElseThrow(RoomStateNotFoundException::new);
        List<CheckoutFeedbackDetail> checkoutFeedbackDetailList = checkoutFeedbackRepository.findAllByRoomStates(roomState);

        if(checkoutFeedbackDetailList.size() == 0){
            throw new CheckoutFormNotFoundException();
        }

        return new CheckoutFeedbackResponse(checkinApplication.getRoomState().getId(), checkoutFeedbackDetailList);
    }

    @Override
    public Map<String, Object> getCheckoutInvestigation(String checkoutAppId) throws RoomStateNotFoundException, CheckinApplicationNotFoundException, InvestigationNotFoundException {

        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkoutAppId).orElseThrow(CheckinApplicationNotFoundException::new);
        Investigation investigation = investigationRepository.findByRoomStatesId(checkinApplication.getRoomState().getId()).orElseThrow(InvestigationNotFoundException::new);

        Map<String, Object> investigationResult = new HashMap<>();
        investigationResult.put("roomStateId", investigation.getRoomStates().getId());
        investigationResult.put("serviceEfficiency", investigation.getServiceEfficiency());
        investigationResult.put("serviceAttitude", investigation.getServiceAttitude());
        investigationResult.put("serviceQuality", investigation.getServiceQuality());
        investigationResult.put("equipmentFurniture", investigation.getEquipmentFurniture());
        investigationResult.put("equipmentElectricDevice", investigation.getEquipmentElectricDevice());
        investigationResult.put("equipmentAssistive", investigation.getEquipmentAssistive());
        investigationResult.put("equipmentBedding", investigation.getEquipmentBedding());
        investigationResult.put("equipmentBarrierFreeEnvironment", investigation.getEquipmentBarrierFreeEnvironment());
        investigationResult.put("environmentClean", investigation.getEnvironmentClean());
        investigationResult.put("environmentComfort", investigation.getEnvironmentComfort());
        investigationResult.put("safetyFirefighting", investigation.getSafetyFirefighting());
        investigationResult.put("safetySecomEmergencySystem", investigation.getSafetySecomEmergencySystem());

        return investigationResult;
    }





    // Service
    @Override
    public List<LandlordServiceRecordDetail> getLandlordServiceRecords(String houseCity, String houseId, String roomId, String landlordId, Date startDate, Date endDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
        SimpleDateFormat dateFormatWithoutTime = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式

        if(startDate == null){
            startDate = dateFormat.parse("2000-01-01 00:00:00");
        }else{
            startDate = dateFormat.parse( dateFormatWithoutTime.format(startDate) + " 00:00:00");
        }


        if(endDate == null){
            endDate = dateFormat.parse("2100-12-21 23:59:59");
        }else{
            endDate = dateFormat.parse( dateFormatWithoutTime.format(endDate) + " 23:59:59");
        }

        return landlordServiceRecordRepository.findAllBySearchCondition(houseCity, houseId, roomId, landlordId, startDate, endDate);
    }

    @Override
    public List<LandlordServiceRecordDetail> getLandlordServiceRecords_nonView(){

        return landlordServiceRecordRepository.findAllNonView();
    }

    @Override
    public String createLandlordsRecordExportExcel(List<String> serviceId) throws LandlordServiceRecordNotFoundException, DownloadExportExcelException{
        List<String> title = new ArrayList<String>(
            Arrays.asList(new String[]{"棧點名稱", "房間號碼", "服務日期",
                "愛心房東", "服務項目", "備註說明", "業管確認"}));
        List<List<String>> exportData = new ArrayList<List<String>>();
        List<Integer> columnWidth = new ArrayList<Integer>(
            Arrays.asList(new Integer[]{15, 8, 12, 12, 20, 30, 30}));

        for(int i = 0; i < serviceId.size(); i++){
            LandlordServiceRecord landlordServiceRecord = landlordServiceRecordRepository.findById(serviceId.get(i)).orElseThrow(LandlordServiceRecordNotFoundException::new);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<String> landlordServiceRow = new ArrayList<>();
            landlordServiceRow.add(landlordServiceRecord.getHouse().getName());
            landlordServiceRow.add(String.valueOf(landlordServiceRecord.getRoom().getNumber()));
            landlordServiceRow.add(sdf.format(landlordServiceRecord.createdAt));
            landlordServiceRow.add(landlordServiceRecord.getLandlord().getChineseName());
            landlordServiceRow.add(landlordServiceRecord.getService());
            landlordServiceRow.add(landlordServiceRecord.getRemark());
            landlordServiceRow.add(landlordServiceRecord.getViewed());

            exportData.add(landlordServiceRow);
        }

        return excelUtil.getDataExcel(new ExportExcelRequest(title, exportData, columnWidth));
    }

    @Override
    public void landlordServiceRecordsViewed(List<String> serviceId) throws LandlordServiceRecordNotFoundException {

        for(int i = 0; i < serviceId.size(); i++){
            LandlordServiceRecord landlordServiceRecord = landlordServiceRecordRepository.findById(serviceId.get(i)).orElseThrow(LandlordServiceRecordNotFoundException::new);
            if(landlordServiceRecord.getViewed().equals("未檢視")){
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strDate = dateFormat.format(date);

                landlordServiceRecord.setViewed(strDate+"，已檢視");
                landlordServiceRecordRepository.save(landlordServiceRecord);
            }
        }

    }

    @Override
    public List<VolunteerServiceRecordDetail> getVolunteerServiceRecords(String houseCity, String houseId, String roomId, String volunteerId, Date startDate, Date endDate) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
        SimpleDateFormat dateFormatWithoutTime = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式

        if(startDate == null){
            startDate = dateFormat.parse("2000-01-01 00:00:00");
        }else{
            startDate = dateFormat.parse( dateFormatWithoutTime.format(startDate) + " 00:00:00");
        }


        if(endDate == null){
            endDate = dateFormat.parse("2100-12-21 23:59:59");
        }else{
            endDate = dateFormat.parse( dateFormatWithoutTime.format(endDate) + " 23:59:59");
        }

        return volunteerServiceRecordRepository.findAllBySearchCondition(houseCity, houseId, roomId, volunteerId, startDate, endDate);
    }

    @Override
    public List<VolunteerServiceRecordDetail> getVolunteerServiceRecords_nonView(){
        return volunteerServiceRecordRepository.findAllNonView();
    }

    @Override
    public void volunteerServiceRecordsViewed(List<String> serviceId) throws VolunteerServiceRecordNotFoundException{
        for(int i = 0; i < serviceId.size(); i++){
            VolunteerServiceRecord volunteerServiceRecord = volunteerServiceRecordRepository.findById(serviceId.get(i)).orElseThrow(VolunteerServiceRecordNotFoundException::new);
            if(volunteerServiceRecord.getViewed().equals("未檢視")){
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strDate = dateFormat.format(date);

                volunteerServiceRecord.setViewed(strDate+"，已檢視");
                volunteerServiceRecordRepository.save(volunteerServiceRecord);
            }
        }
    }

    @Override
    public String createVolunteersRecordExportExcel(List<String> serviceId) throws VolunteerServiceRecordNotFoundException, DownloadExportExcelException{
        List<String> title = new ArrayList<String>(
            Arrays.asList(new String[]{"棧點名稱", "房間號碼", "服務日期",
                "愛心志工", "服務項目", "備註說明", "業管確認"}));
        List<List<String>> exportData = new ArrayList<List<String>>();
        List<Integer> columnWidth = new ArrayList<Integer>(
            Arrays.asList(new Integer[]{15, 8, 12, 12, 20, 30, 30}));

        for(int i = 0; i < serviceId.size(); i++){
            VolunteerServiceRecord volunteerServiceRecord = volunteerServiceRecordRepository.findById(serviceId.get(i)).orElseThrow(VolunteerServiceRecordNotFoundException::new);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<String> volunteerServiceRow = new ArrayList<>();
            volunteerServiceRow.add(volunteerServiceRecord.getHouse().getName());
            volunteerServiceRow.add(String.valueOf(volunteerServiceRecord.getRoom().getNumber()));
            volunteerServiceRow.add(sdf.format(volunteerServiceRecord.createdAt));
            volunteerServiceRow.add(volunteerServiceRecord.getVolunteer().getChineseName());
            volunteerServiceRow.add(volunteerServiceRecord.getService());
            volunteerServiceRow.add(volunteerServiceRecord.getRemark());
            volunteerServiceRow.add(volunteerServiceRecord.getViewed());

            exportData.add(volunteerServiceRow);
        }

        return excelUtil.getDataExcel(new ExportExcelRequest(title, exportData, columnWidth));
    }

    @Override
    public List<FirmEmployeeServiceRecordDetailByAdmin> getFirmServiceRecords(String houseCity, String houseId, String roomId, String firmId, Date startDate, Date endDate) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
        SimpleDateFormat dateFormatWithoutTime = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式

        if(startDate == null){
            startDate = dateFormat.parse("2000-01-01 00:00:00");
        }else{
            startDate = dateFormat.parse( dateFormatWithoutTime.format(startDate) + " 00:00:00");
        }


        if(endDate == null){
            endDate = dateFormat.parse("2100-12-21 23:59:59");
        }else{
            endDate = dateFormat.parse( dateFormatWithoutTime.format(endDate) + " 23:59:59");
        }

        return firmEmployeesRepository.findAllBySearchCondition(houseCity, houseId, roomId, firmId, startDate, endDate);
    }

    @Override
    public List<FirmEmployeeServiceRecordDetailByAdmin> getFirmServiceRecords_nonView(){
        return firmEmployeeServiceRecordRepository.findAllNonView();
    }

    @Override
    public void firmServiceRecordsViewed(List<String> serviceId) throws FirmServiceRecordNotFoundException{
        for(int i = 0; i < serviceId.size(); i++){
            FirmEmployeeServiceRecord firmEmployeeServiceRecord = firmEmployeeServiceRecordRepository.findById(serviceId.get(i)).orElseThrow(FirmServiceRecordNotFoundException::new);
            if(firmEmployeeServiceRecord.getViewed().equals("未檢視")){
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strDate = dateFormat.format(date);

                firmEmployeeServiceRecord.setViewed(strDate+"，已檢視");
                firmEmployeeServiceRecordRepository.save(firmEmployeeServiceRecord);
            }
        }
    }

    @Override
    public String createFirmsRecordExportExcel(List<String> serviceId) throws FirmServiceRecordNotFoundException, DownloadExportExcelException{
        List<String> title = new ArrayList<String>(
            Arrays.asList(new String[]{"棧點名稱", "房間號碼", "施作日期", "施作單位", "施作人員", "施作內容", "業管確認"}));
        List<List<String>> exportData = new ArrayList<List<String>>();
        List<Integer> columnWidth = new ArrayList<Integer>(
            Arrays.asList(new Integer[]{15, 8, 15, 15, 15, 30, 30}));

        for(int i = 0; i < serviceId.size(); i++){
            FirmEmployeeServiceRecord firmEmployeeServiceRecord = firmEmployeeServiceRecordRepository.findById(serviceId.get(i)).orElseThrow(FirmServiceRecordNotFoundException::new);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<String> firmServiceRow = new ArrayList<>();
            firmServiceRow.add(firmEmployeeServiceRecord.getHouse().getName());
            firmServiceRow.add(String.valueOf(firmEmployeeServiceRecord.getRoom().getNumber()));
            firmServiceRow.add(sdf.format(firmEmployeeServiceRecord.createdAt));
            firmServiceRow.add(firmEmployeeServiceRecord.getFirmEmployees().getFirm().getName());
            firmServiceRow.add(firmEmployeeServiceRecord.getFirmEmployees().getChineseName());
            firmServiceRow.add(firmEmployeeServiceRecord.getService());
            firmServiceRow.add(firmEmployeeServiceRecord.getViewed());

            exportData.add(firmServiceRow);
        }

        return excelUtil.getDataExcel(new ExportExcelRequest(title, exportData, columnWidth));
    }




    //House
    @Override
    public House insertNewHouse(HouseInsert request) throws RepeatedHouseNameException, LandlordNotFoundException{
        int countByName = houseRepository.countByName(request.getName());
        if(countByName > 0){
            throw new RepeatedHouseNameException();
        }

        Landlord landlord = landlordRepository.findById(request.getLandlordId()).orElseThrow(LandlordNotFoundException::new);

        List<HouseDetail> existData = houseRepository.findAllSimpleDetail();

        House house = new House();
        house.setSerial(existData.get(0).getSerial() + 1);
        house.setCity(request.getCity());
        house.setName(request.getName());
        house.setIntroduction(request.getIntroduction());
        house.setSquareFootage(String.valueOf(request.getSquareFootage()) + "坪");
        house.setRoomLayout(request.getRoomLayout());
        house.setTotalFloor(String.valueOf(request.getTotalFloor()) + "樓");
        house.setRoomDescription(request.getRoomDescription());
        house.setStyle(request.getStyle());
        house.setFeature(request.getFeature());
        house.setLandlord(landlord);
        house.setTraffic(request.getTraffic());
        house.setAddress(request.getAddress());
        house.setNearHospital(request.getNearHospital());
        house.setLifeFunction(request.getLifeFunction());
        house.setPlanimetricMap("系統建置中");
        house.setFullDegreePanorama("系統建置中");
        houseRepository.save(house);


        return houseRepository.save(house);
    }

    @Override
    public House updateHouse(String houseId, HouseEdit request) throws HouseNotFoundException, LandlordNotFoundException {
        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);

        Landlord landlord = landlordRepository.findById(request.getLandlordId()).orElseThrow(LandlordNotFoundException::new);

        house.setCity(request.getCity());
        house.setName(request.getName());
        house.setIntroduction(request.getIntroduction());
        house.setSquareFootage(String.valueOf(request.getSquareFootage()) + "坪");
        house.setRoomLayout(request.getRoomLayout());
        house.setTotalFloor(String.valueOf(request.getTotalFloor()) + "樓");
        house.setRoomDescription(request.getRoomDescription());
        house.setStyle(request.getStyle());
        house.setFeature(request.getFeature());
        house.setLandlord(landlord);
        house.setTraffic(request.getTraffic());
        house.setAddress(request.getAddress());
        house.setNearHospital(request.getNearHospital());
        house.setLifeFunction(request.getLifeFunction());

        return houseRepository.save(house);
    }

    @Override
    public House updateHouseDisable(String houseId) throws HouseNotFoundException{
        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);
        house.setDisable(!house.isDisable());

        return houseRepository.save(house);
    }

    @Override
    public void uploadHouseImage(String houseId, String host, UploadHouseImagesRequest request) throws HouseNotFoundException, HouseImageTypeMismatchException, UploadHouseImageException {
        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);

        String filePaths =
            storageUtil.storeHouseImageMetaData(new HouseImageMetaData(host, request.getHouseImage()));

        HouseImage houseImage = new HouseImage();
        houseImage.setImage(filePaths);
        houseImage.setHouse(house);
        houseImageRepository.save(houseImage);
    }

    @Override
    public void deleteHouseImage(String houseImageId) throws HouseImageNotFoundException, DeleteHouseImageException{
        HouseImage houseImage = houseImageRepository.findById(houseImageId).orElseThrow(HouseImageNotFoundException::new);

        try {
            String houseImageFilePath = "." + houseImage.getImage();
            Path houseImagePath = Paths.get(houseImageFilePath);
            Files.deleteIfExists(houseImagePath);
        } catch (IOException e) {
            throw new DeleteHouseImageException();
        }

        houseImageRepository.delete(houseImage);
    }

    @Override
    public void uploadHousePlanimetricMap(String houseId, String host, UploadHousePlanimetricMapRequest request) throws HouseNotFoundException, HousePlanimetricMapExistedException, HousePlanimetricMapTypeMismatchException, UploadHousePlanimetricMapException {
        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);

        if(house.getPlanimetricMap().indexOf("house-panimetric-map") != -1 ){
            throw new HousePlanimetricMapExistedException();
        }

        String filePaths = storageUtil.storeHousePlanimetricMapMetaData(new HousePlanimetricMapMetaData(host, request.getHousePlanimetricMap()));

        house.setPlanimetricMap(filePaths);
        houseRepository.save(house);
    }

    @Override
    public void deleteHousePlanimetricMap(String houseId) throws HouseNotFoundException, HousePlanimetricMapNotFoundException, DeleteHousePlanimetricMapException{
        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);

        if(house.getPlanimetricMap().indexOf("house-panimetric-map") == -1 ){
            throw new HousePlanimetricMapNotFoundException();
        }

        try {
            String houseImageFilePath = "." + house.getPlanimetricMap();
            Path houseImagePath = Paths.get(houseImageFilePath);
            Files.deleteIfExists(houseImagePath);
        } catch (IOException e) {
            throw new DeleteHousePlanimetricMapException();
        }

        house.setPlanimetricMap("系統建置中");
        houseRepository.save(house);
    }

    @Override
    public void uploadHouseFullDegreePanorama(String houseId, String host, UploadHouseFullDegreePanoramaRequest request) throws HouseNotFoundException, HouseFullDegreePanoramaExistedException, HouseFullDegreePanoramaTypeMismatchException, UploadHouseFullDegreePanoramaException {
        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);

        if(house.getFullDegreePanorama().indexOf("house-full-degree-panorama") != -1 ){
            throw new HouseFullDegreePanoramaExistedException();
        }

        String filePaths = storageUtil.storeHouseFullDegreePanoramaMetaData(new HouseFullDegreePanoramaMetaData(host, request.getHouseFullDegreePanorama()));

        house.setFullDegreePanorama(filePaths);
        houseRepository.save(house);
    }

    @Override
    public void deleteHouseFullDegreePanorama(String houseId) throws HouseNotFoundException, HouseFullDegreePanoramaNotFoundException, DeleteHouseFullDegreePanoramaException{
        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);

        if(house.getFullDegreePanorama().indexOf("house-full-degree-panorama") == -1 ){
            throw new HouseFullDegreePanoramaNotFoundException();
        }

        try {
            String houseImageFilePath = "." + house.getFullDegreePanorama();
            Path houseImagePath = Paths.get(houseImageFilePath);
            Files.deleteIfExists(houseImagePath);
        } catch (IOException e) {
            throw new DeleteHouseFullDegreePanoramaException();
        }

        house.setFullDegreePanorama("系統建置中");
        houseRepository.save(house);
    }

    @Override
    public Room insertNewRoom(String houseId, int roomNumber) throws HouseNotFoundException, RepeatedRoomNumberException{
        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);

        int countByNumber = roomRepository.countByNumber(roomNumber);
        if(countByNumber != 0){
            throw new RepeatedRoomNumberException();
        }

        Room room = new Room();
        room.setNumber(roomNumber);
        room.setHouse(house);

        return roomRepository.save(room);
    }



    // Room
    @Override
    public Room updateRoomInfo(String roomId, int roomNumber, boolean roomDisable)throws RoomNotFoundException, RepeatedRoomNumberException{
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);

        int countByNumber = roomRepository.countByNumber(roomNumber);
        if(countByNumber != 0){
            if(countByNumber == 1){
                List<Room> room_check = roomRepository.findAllByNumber(roomNumber);

                if(room_check.get(0) != room){
                    throw new RepeatedRoomNumberException();
                }
            }else {
                throw new RepeatedRoomNumberException();
            }
        }

        room.setNumber(roomNumber);
        room.setDisable(roomDisable);

        return roomRepository.save(room);
    }



    // Furniture
    @Override
    public org.eden.lovestation.dao.model.HousePublicFurniture insertHousePublicFurniture(String houseId, String furnitureName) throws HouseNotFoundException,  FurnitureNotFoundException {
        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);
        PublicFurniture publicFurniture = publicFurnitureRepository.findByName(furnitureName).orElseThrow(FurnitureNotFoundException::new);

        org.eden.lovestation.dao.model.HousePublicFurniture housePublicFurniture = new org.eden.lovestation.dao.model.HousePublicFurniture();
        housePublicFurniture.setHouse(house);
        housePublicFurniture.setPublicFurniture(publicFurniture);

        return housePublicFurnitureRepository.save(housePublicFurniture);
    }

    @Override
    public org.eden.lovestation.dao.model.HousePrivateFurniture insertHousePrivateFurniture(String roomId, String furnitureName) throws RoomNotFoundException, FurnitureNotFoundException{
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        PrivateFurniture privateFurniture = privateFurnitureRepository.findByName(furnitureName).orElseThrow(FurnitureNotFoundException::new);

        org.eden.lovestation.dao.model.HousePrivateFurniture housePrivateFurniture = new org.eden.lovestation.dao.model.HousePrivateFurniture();
        housePrivateFurniture.setRoom(room);
        housePrivateFurniture.setPrivateFurniture(privateFurniture);

        return housePrivateFurnitureRepository.save(housePrivateFurniture);
    }

    @Override
    public void deleteHousePublicFurniture(String publicFurnitureId) throws FurnitureNotFoundException{
        org.eden.lovestation.dao.model.HousePublicFurniture housePublicFurniture = housePublicFurnitureRepository.findById(publicFurnitureId).orElseThrow(FurnitureNotFoundException::new);

        housePublicFurnitureRepository.delete(housePublicFurniture);
    }

    @Override
    public void deleteHousePrivateFurniture(String privateFurnitureId) throws FurnitureNotFoundException{
        org.eden.lovestation.dao.model.HousePrivateFurniture housePrivateFurniture = housePrivateFurnitureRepository.findById(privateFurnitureId).orElseThrow(FurnitureNotFoundException::new);

        housePrivateFurnitureRepository.delete(housePrivateFurniture);
    }

    @Override
    public PublicFurniture insertPublicFurniture(String furnitureName) throws RepeatedPublicFurnitureNameException {
        // Check if there is exist
        int publicFurniture_check = publicFurnitureRepository.countByName(furnitureName);
        if(publicFurniture_check != 0){
            throw new RepeatedPublicFurnitureNameException();
        }

        // Get the Serial Number
        List<PublicFurniture> publicFurniture_data = publicFurnitureRepository.findAll(Sort.by("precedence").descending());

        PublicFurniture publicFurniture = new PublicFurniture();
        publicFurniture.setName(furnitureName);
        publicFurniture.setPrecedence((publicFurniture_data.get(0).getPrecedence() + 1));

        return publicFurnitureRepository.save(publicFurniture);
    }

    @Override
    public PrivateFurniture insertPrivateFurniture(String furnitureName) throws RepeatedPrivateFurnitureNameException {
        // Check if there is exist
        int privateFurniture_check = privateFurnitureRepository.countByName(furnitureName);
        if(privateFurniture_check != 0){
            throw new RepeatedPrivateFurnitureNameException();
        }

        // Get the Serial Number
        List<PrivateFurniture> privateFurnitures_date = privateFurnitureRepository.findAll(Sort.by("precedence").descending());

        PrivateFurniture privateFurniture = new PrivateFurniture();
        privateFurniture.setName(furnitureName);
        privateFurniture.setPrecedence((privateFurnitures_date.get(0).getPrecedence() + 1));

        return privateFurnitureRepository.save(privateFurniture);
    }

    @Override
    public void exchangePublicFurnitureIndex(String furnitureId_A, String furnitureId_B) throws PublicFurnitureNotFoundException{
        PublicFurniture publicFurniture_a = publicFurnitureRepository.findById(furnitureId_A).orElseThrow(PublicFurnitureNotFoundException::new);
        PublicFurniture publicFurniture_b = publicFurnitureRepository.findById(furnitureId_B).orElseThrow(PublicFurnitureNotFoundException::new);

        int index_a = publicFurniture_a.getPrecedence();
        publicFurniture_a.setPrecedence(publicFurniture_b.getPrecedence());
        publicFurniture_b.setPrecedence(index_a);

        publicFurnitureRepository.save(publicFurniture_a);
        publicFurnitureRepository.save(publicFurniture_b);
    }

    @Override
    public void exchangePrivateFurnitureIndex(String furnitureId_A, String furnitureId_B) throws PrivateFurnitureNotFoundException{
        PrivateFurniture privateFurniture_a = privateFurnitureRepository.findById(furnitureId_A).orElseThrow(PrivateFurnitureNotFoundException::new);
        PrivateFurniture privateFurniture_b = privateFurnitureRepository.findById(furnitureId_B).orElseThrow(PrivateFurnitureNotFoundException::new);

        int index_a = privateFurniture_a.getPrecedence();
        privateFurniture_a.setPrecedence(privateFurniture_b.getPrecedence());
        privateFurniture_b.setPrecedence(index_a);

        privateFurnitureRepository.save(privateFurniture_a);
        privateFurnitureRepository.save(privateFurniture_b);
    }

    @Override
    public PublicFurniture renamePublicFurnitureIndex(String furnitureId, String furnitureName)throws PublicFurnitureNotFoundException{
        PublicFurniture publicFurniture = publicFurnitureRepository.findById(furnitureId).orElseThrow(PublicFurnitureNotFoundException::new);
        publicFurniture.setName(furnitureName);

        return publicFurnitureRepository.save(publicFurniture);
    }

    @Override
    public PrivateFurniture renamePrivateFurnitureIndex(String furnitureId, String furnitureName)throws PrivateFurnitureNotFoundException{
        PrivateFurniture privateFurniture = privateFurnitureRepository.findById(furnitureId).orElseThrow(PrivateFurnitureNotFoundException::new);
        privateFurniture.setName(furnitureName);

        return privateFurnitureRepository.save(privateFurniture);
    }

    @Override
    public void deletePublicFurniture(String furnitureId)throws PublicFurnitureNotFoundException{
        PublicFurniture publicFurniture = publicFurnitureRepository.findById(furnitureId).orElseThrow(PublicFurnitureNotFoundException::new);

        // delete house_public_furniture
        List<org.eden.lovestation.dao.model.HousePublicFurniture> housePublicFurnitureList = housePublicFurnitureRepository.findAllByPublicFurniture(publicFurniture.getId());
        for(int i = 0; i < housePublicFurnitureList.size(); i++){
            org.eden.lovestation.dao.model.HousePublicFurniture housePublicFurniture = housePublicFurnitureList.get(i);
            housePublicFurnitureRepository.deleteById(housePublicFurniture.getId());
        }

        // delete checkin_form_public_furniture
        List<CheckinFormPublicFurniture> checkinFormPublicFurnitureList = checkinFormPublicFurnitureRepository.findAllByPublicFurniture(publicFurniture.getId());
        for(int i = 0; i < checkinFormPublicFurnitureList.size(); i++){
            CheckinFormPublicFurniture checkinFormPublicFurniture = checkinFormPublicFurnitureList.get(i);
            checkinFormPublicFurnitureRepository.delete(checkinFormPublicFurniture);
        }

        publicFurnitureRepository.delete(publicFurniture);

        //Reset PublicFurniture.precedence
        List<PublicFurniture> publicFurnitureList = publicFurnitureRepository.findAll(Sort.by("precedence"));
        for(int i = 0; i < publicFurnitureList.size(); i++){
            if(publicFurnitureList.get(i).getPrecedence() == (i+1)){ continue; }

            publicFurnitureList.get(i).setPrecedence((i+1));
            publicFurnitureRepository.save(publicFurnitureList.get(i));
        }

    }

    @Override
    public void deletePrivateFurniture(String furnitureId)throws PrivateFurnitureNotFoundException{
        PrivateFurniture privateFurniture = privateFurnitureRepository.findById(furnitureId).orElseThrow(PrivateFurnitureNotFoundException::new);

        // delete house_private_furniture
        List<org.eden.lovestation.dao.model.HousePrivateFurniture> housePrivateFurnitureList = housePrivateFurnitureRepository.findAllByPrivateFurniture(privateFurniture.getId());
        for(int i = 0; i < housePrivateFurnitureList.size(); i++){
            org.eden.lovestation.dao.model.HousePrivateFurniture housePrivateFurniture = housePrivateFurnitureList.get(i);
            housePrivateFurnitureRepository.deleteById(housePrivateFurniture.getId());
        }

        // delete checkin_form_private_furniture
        List<CheckinFormPrivateFurniture> checkinFormPrivateFurnitureList = checkinFormPrivateFurnitureRepository.findAllByPrivateFurniture(privateFurniture.getId());
        for(int i = 0; i < checkinFormPrivateFurnitureList.size(); i++){
            CheckinFormPrivateFurniture checkinFormPrivateFurniture = checkinFormPrivateFurnitureList.get(i);
            checkinFormPrivateFurnitureRepository.delete(checkinFormPrivateFurniture);
        }

        privateFurnitureRepository.delete(privateFurniture);

        //Reset PrivateFurniture.precedence
        List<PrivateFurniture> privateFurnitureList = privateFurnitureRepository.findAll(Sort.by("precedence"));
        for(int i = 0; i < privateFurnitureList.size(); i++){
            if(privateFurnitureList.get(i).getPrecedence() == (i+1)){ continue; }

            privateFurnitureList.get(i).setPrecedence((i+1));
            privateFurnitureRepository.save(privateFurnitureList.get(i));
        }

    }



    // Authority
    @Override
    public AdminAccountSearchResultPagedResponse findAdminsByKeyword(String role, String keyword, int currentPage, String admin_id) throws AdminNotFoundException {

        Admin admin = adminRepository.findById(admin_id).orElseThrow(AdminNotFoundException::new);

        Page<AdminAccountDetail> pageResult = adminRepository.findAllBySearchCondition(admin.getId(), role, keyword, PageRequest.of(currentPage,
            10));

        return new AdminAccountSearchResultPagedResponse(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public void changeAdminAuthority(String admin_id, String role_name) throws  AdminNotFoundException, RoleNotFoundException{
        Admin admin = adminRepository.findById(admin_id).orElseThrow(AdminNotFoundException::new);
        Role role = roleRepository.findByName(role_name).orElseThrow(RoleNotFoundException::new);

        admin.setRole(role);
        adminRepository.save(admin);
    }

    @Override
    public List<AdminRegisterVerification> findAdminRegisterVerification(){
        List<AdminRegisterVerification> adminRegisterVerifications = adminRepository.findAllByVerifiedIsNull();
        return adminRegisterVerifications;
    }

    @Override
    public AdminRegisterVerification findAdminRegisterVerificationById(String id) throws AdminNotFoundException {
        AdminRegisterVerification adminRegisterVerification = adminRepository.findAllByIdAndVerifiedIsNull(id).orElseThrow(AdminNotFoundException::new);

        return adminRegisterVerification;
    }

    @Override
    public void updateAdminRegisterVerification(String admin_id, UpdateAdministratorRegisterVerificationRequest request) throws AdminNotFoundException, RoleNotFoundException {
        Admin admin = adminRepository.findById(admin_id).orElseThrow(AdminNotFoundException::new);

        if(admin.getVerified() != null){
            throw new AdminNotFoundException();
        }

        if(request.getReason().length() > 0 || !request.getVerified()){
            // 拒絕
            emailUtil.sendAdminRegisterEmail(admin.getEmail(), 3, admin.getName(), admin.getAccount(), "", request.getReason());

            adminRepository.delete(admin);
        }else if(request.getChangeTo().length() > 0){
            // 提高權限
            Role role = roleRepository.findByName(request.getChangeTo()).orElseThrow(RoleNotFoundException::new);

            admin.setVerified(true);
            admin.setRole(role);
            adminRepository.save(admin);

            String newRole_chinese = "";

            if(request.getChangeTo().equals("admin")){
                newRole_chinese = "系統管理員";
            }else if(request.getChangeTo().equals("admin_readonly")){
                newRole_chinese = "唯讀管理員";
            }else if(request.getChangeTo().equals("admin_writable")){
                newRole_chinese = "可寫管理員";
            }

            emailUtil.sendAdminRegisterEmail(admin.getEmail(), 2, admin.getName(), admin.getAccount(), newRole_chinese, "");
        }else{
            // 純通過
            admin.setVerified(true);
            adminRepository.save(admin);

            emailUtil.sendAdminRegisterEmail(admin.getEmail(), 1, admin.getName(), admin.getAccount(), "", "");
        }
    }

    @Override
    public Admin insertNewAdmin(RegisterAdministratorByAdministratorRequest request) throws RepeatedAccountException, RepeatedEmailException, RoleNotFoundException {
        Admin admin = new Admin();

        checkerUtil.checkRepeatedAccount(admin.getAccount());
        checkerUtil.checkRepeatedEmail(admin.getEmail());

        Role role = roleRepository.findByName(request.getRoleName()).orElseThrow(RoleNotFoundException::new);

        String password = getRandomPassword();
        String hashPassword = passwordUtil.generateHashPassword(password);

        admin.setAccount(request.getAccount());
        admin.setPassword(hashPassword);
        admin.setRole(role);
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setVerified(true);

        adminRepository.save(admin);

        emailUtil.sendAdminRegisterByAdminEmail(admin.getEmail(), admin.getName(), admin.getAccount(), password);

        return admin;
    }

    private String getRandomPassword() {
        int z;
        StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < 8; i++) {
            z = (int) ((Math.random() * 7) % 3);

            if (z == 1) { // 放數字
                sb.append((int) ((Math.random() * 10) + 48));
            } else if (z == 2) { // 放大寫英文
                sb.append((char) (((Math.random() * 26) + 65)));
            } else {// 放小寫英文
                sb.append(((char) ((Math.random() * 26) + 97)));
            }
        }
        return sb.toString();
    }
}