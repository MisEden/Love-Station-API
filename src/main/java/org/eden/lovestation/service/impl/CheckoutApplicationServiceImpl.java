package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.request.SendCheckoutFeedbackRequest;
import org.eden.lovestation.dto.request.SendCheckoutInvestigationRequest;
import org.eden.lovestation.dto.response.CheckoutApplicationRoomStateResponse;
import org.eden.lovestation.dto.response.CheckoutStatusResponse;
import org.eden.lovestation.dto.response.IsExistedResponse;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.CheckoutApplicationService;
import org.eden.lovestation.util.checker.CheckerUtil;
import org.eden.lovestation.util.storage.StorageUtil;
import org.hibernate.annotations.Check;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

@Service
public class CheckoutApplicationServiceImpl implements CheckoutApplicationService {

    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;
    private final RoomStateRepository roomStateRepository;
    private final UserRepository userRepository;
    private final ReferralEmployeeRepository referralEmployeeRepository;
    private final CheckinApplicationRepository checkinApplicationRepository;
    private final AdminRepository adminRepository;
    private final HouseRepository houseRepository;
    private final InvestigationRepository investigationRepository;
    private final CheckoutFeedbackRepository checkoutFeedbackRepository;
    private final CheckinFormRepository checkinFormRepository;
    private final CheckerUtil checkerUtil;
    private final StorageUtil storageUtil;

    @Autowired
    public CheckoutApplicationServiceImpl(ModelMapper modelMapper,
                                          RoomRepository roomRepository,
                                          RoomStateRepository roomStateRepository,
                                          UserRepository userRepository,
                                          ReferralEmployeeRepository referralEmployeeRepository,
                                          CheckinApplicationRepository checkinApplicationRepository,
                                          CheckerUtil checkerUtil,
                                          AdminRepository adminRepository,
                                          HouseRepository houseRepository,
                                          InvestigationRepository investigationRepository,
                                          CheckoutFeedbackRepository checkoutFeedbackRepository,
                                          CheckinFormRepository checkinFormRepository,
                                          StorageUtil storageUtil) {
        this.modelMapper = modelMapper;
        this.roomRepository = roomRepository;
        this.roomStateRepository = roomStateRepository;
        this.userRepository = userRepository;
        this.referralEmployeeRepository = referralEmployeeRepository;
        this.checkinApplicationRepository = checkinApplicationRepository;
        this.checkerUtil = checkerUtil;
        this.adminRepository = adminRepository;
        this.houseRepository = houseRepository;
        this.investigationRepository = investigationRepository;
        this.checkoutFeedbackRepository = checkoutFeedbackRepository;
        this.checkinFormRepository = checkinFormRepository;
        this.storageUtil = storageUtil;
    }

    @Transactional
    @Override
    public IsExistedResponse uploadCheckoutApplicationInvestigation(SendCheckoutInvestigationRequest request, String roomStatesId) throws RoomStateNotFoundException {

        String message;

        RoomState roomState = roomStateRepository.findById(roomStatesId).orElseThrow(RoomStateNotFoundException::new);
        Investigation investigation = new Investigation();
        Optional<Investigation> investigationOptional = investigationRepository.findByRoomStatesId(roomState.getId());

        //check investigation data is existed
        if (investigationOptional.isPresent()){

            investigation = investigationOptional.get();
            message = "roomStateId: " + roomState.getId() + " already existed at " + investigation.getCreatedAt();

            return new IsExistedResponse(true, message);

        }else{
            //upload investigation data
            investigation.setRoomStates(roomState);
            investigation.setServiceEfficiency(request.getServiceEfficiency());
            investigation.setServiceAttitude(request.getServiceAttitude());

            short serviceQuality = request.getServiceQuality();
            short equipmentFurniture = request.getEquipmentFurniture();
            short equipmentElectricDevice = request.getEquipmentElectricDevice();
            short equipmentAssistive = request.getEquipmentAssistive();
            short equipmentBedding = request.getEquipmentBedding();
            short equipmentBarrierFreeEnvironment = request.getEquipmentBarrierFreeEnvironment();
            short environmentClean = request.getEnvironmentClean();
            short environmentComfort = request.getEnvironmentComfort();
            short safetyFirefighting = request.getSafetyFirefighting();
            short safetySecomEmergencySystem = request.getSafetySecomEmergencySystem();

            investigation.setServiceQuality(serviceQuality);
            investigation.setEquipmentFurniture(equipmentFurniture);
            investigation.setEquipmentElectricDevice(equipmentElectricDevice);
            investigation.setEquipmentAssistive(equipmentAssistive);
            investigation.setEquipmentBedding(equipmentBedding);
            investigation.setEquipmentBarrierFreeEnvironment(equipmentBarrierFreeEnvironment);
            investigation.setEnvironmentClean(environmentClean);
            investigation.setEnvironmentComfort(environmentComfort);
            investigation.setSafetyFirefighting(safetyFirefighting);
            investigation.setSafetySecomEmergencySystem(safetySecomEmergencySystem);

            investigationRepository.save(investigation);
            message = "roomStatesId: " + roomStatesId + " upload investigation success";
        }

        return new IsExistedResponse(false, message);
    }

    @Override
    public IsExistedResponse checkInvestigationIsExisted(String roomStateId) throws RoomStateNotFoundException{

        String message;

        RoomState roomState = roomStateRepository.findById(roomStateId).orElseThrow(RoomStateNotFoundException::new);
        Investigation investigation = new Investigation();
        Optional<Investigation> investigationOptional = investigationRepository.findByRoomStatesId(roomState.getId());

        if (investigationOptional.isPresent()){
            investigation = investigationOptional.get();
            message = "roomStateId : " + roomState.getId() + " investigation already existed at " + investigation.getCreatedAt();
            return new IsExistedResponse(true, message);
        }

        message = "roomStateId : " + roomStateId + " investigation does not existed";

        return new IsExistedResponse(false, message);
    }

    @Transactional
    @Override
    public IsExistedResponse sendCheckoutApplicationFeedback(SendCheckoutFeedbackRequest request, String roomStatesId) throws RoomStateNotFoundException {
//        CheckoutFeedback checkoutFeedback = modelMapper.map(request, CheckoutFeedback.class);

        String message;

        RoomState roomState = roomStateRepository.findById(roomStatesId).orElseThrow(RoomStateNotFoundException::new);
        CheckoutFeedback checkoutFeedback = new CheckoutFeedback();
        Optional<CheckoutFeedback> checkoutFeedbackOptional = checkoutFeedbackRepository.findByRoomStates(roomState);

        //check feedback data is existed
        if (checkoutFeedbackOptional.isPresent()){

            checkoutFeedback = checkoutFeedbackOptional.get();
            message = roomStatesId + " already existed at : " + checkoutFeedback.getCreatedAt();

            return new IsExistedResponse(true, message);

        }else{

            //upload checkoutFeedback data
            checkoutFeedback.setRoomStates(roomState);

            checkoutFeedback.setBedding(request.getBedding());
            checkoutFeedback.setBeddingFeedback(request.getBeddingFeedback());

            checkoutFeedback.setBathroom(request.getBathroom());
            checkoutFeedback.setBathroomFeedback(request.getBathroomFeedback());

            checkoutFeedback.setRefrigerator(request.getRefrigerator());
            checkoutFeedback.setRefrigeratorFeedback(request.getRefrigeratorFeedback());

            checkoutFeedback.setPrivateItem(request.getPrivateItem());
            checkoutFeedback.setPrivateItemFeedback(request.getPrivateItemFeedback());

            checkoutFeedback.setGarbage(request.getGarbage());
            checkoutFeedback.setGarbageFeedback(request.getGarbageFeedback());

            checkoutFeedback.setDoorsWindowsPower(request.getDoorsWindowsPower());
            checkoutFeedback.setDoorsWindowsPowerFeedback(request.getDoorsWindowsPowerFeedback());

            checkoutFeedback.setSecurityNotification(request.getSecurityNotification());
            checkoutFeedback.setSecurityNotificationFeedback(request.getSecurityNotificationFeedback());

            checkoutFeedback.setReturnKey(request.getReturnKey());
            checkoutFeedback.setReturnKeyFeedback(request.getReturnKeyFeedback());

            checkoutFeedback.setReturnCheckinFile(request.getReturnCheckinFile());
            checkoutFeedback.setReturnCheckinFileFeedback(request.getReturnCheckinFileFeedback());

            checkoutFeedbackRepository.save(checkoutFeedback);

            message = roomStatesId + " send feedback success";

        }

        return new IsExistedResponse(false, message);

//        return roomStatesId + " send feedback success";
    }

    @Override
    public IsExistedResponse checkFeedbackIsExisted(String roomStateId) throws RoomStateNotFoundException{

        String message;

        RoomState roomState = roomStateRepository.findById(roomStateId).orElseThrow(RoomStateNotFoundException::new);
        CheckoutFeedback checkoutFeedback = new CheckoutFeedback();
        Optional<CheckoutFeedback> checkoutFeedbackOptional = checkoutFeedbackRepository.findByRoomStates(roomState);

        if (checkoutFeedbackOptional.isPresent()){
            checkoutFeedback = checkoutFeedbackOptional.get();
            message = "roomStateId : " + roomStateId + " feedback already existed at : " + checkoutFeedback.getCreatedAt();
            return new IsExistedResponse(true, message);
        }

        message = "roomStateId : " + roomStateId + " feedback does not existed";

        return new IsExistedResponse(false, message);
    }

    @Transactional
    @Override
    public CheckoutApplicationRoomStateResponse getDaysOfStay(String roomStatesId) throws RoomNotFoundException {

        RoomState roomState = roomStateRepository.findById(roomStatesId).orElseThrow(RoomNotFoundException::new);

        String result = "start date : " + roomState.getStartDate().toString() + " end date : " + roomState.getEndDate().toString();

        Date nowDate = new Date();

        DateTime startDateTime = new DateTime(roomState.getStartDate());
        DateTime nowDateTime = new DateTime(nowDate);

        int diff = Days.daysBetween(startDateTime, nowDateTime).getDays();

        return new CheckoutApplicationRoomStateResponse(roomState.getStartDate(), roomState.getEndDate(), nowDate, diff);
    }

    @Override
    public CheckoutStatusResponse checkStatusToCheckout(String userId) throws UserNotFoundException, RoomStateNotFoundException, CheckinApplicationNotFoundException, CheckinFormNotFoundException{

        Boolean overdue = true;

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        RoomState roomState = roomStateRepository.findByUser(user).orElseThrow(RoomStateNotFoundException::new);
        CheckinApplication checkinApplication = checkinApplicationRepository.findByRoomState(roomState).orElseThrow(CheckinApplicationNotFoundException::new);
        CheckinForm checkinForm = checkinFormRepository.findByRoomState(roomState).orElseThrow(CheckinFormNotFoundException::new);

        Date nowDate = new Date();
        DateTime startDateTime = new DateTime(checkinForm.getCreatedAt());
        DateTime nowDateTime = new DateTime(nowDate);

        int diff = Days.daysBetween(startDateTime, nowDateTime).getDays();

        if (diff >= 0 && diff <= 7){
            overdue = false;
        }

        return new CheckoutStatusResponse(checkinApplication.getId(), roomState.getId(), roomState.getStartDate(), roomState.getEndDate(), checkinForm.getCreatedAt(), diff, overdue);
    }

}
