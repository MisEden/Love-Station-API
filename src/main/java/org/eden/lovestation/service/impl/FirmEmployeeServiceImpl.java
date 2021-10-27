package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.projection.CheckinApplicationBrief;
import org.eden.lovestation.dto.projection.FirmEmployeeServiceRecordDetail;
import org.eden.lovestation.dto.request.RegisterFirmEmployeeRequest;
import org.eden.lovestation.dto.request.SendFirmEmployeeServiceRecordRequest;
import org.eden.lovestation.dto.response.FirmAndFirmEmployeeNameResponse;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.FirmEmployeeService;
import org.eden.lovestation.util.checker.CheckerUtil;
import org.eden.lovestation.util.password.PasswordUtil;
import org.eden.lovestation.util.storage.FirmEmployeeCleaningImageMetaData;
import org.eden.lovestation.util.storage.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FirmEmployeeServiceImpl implements FirmEmployeeService {

    private final FirmRepository firmRepository;
    private final FirmEmployeesRepository firmEmployeesRepository;
    private final FirmEmployeeServiceRecordRepository firmEmployeeServiceRecordRepository;
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final CheckinApplicationRepository checkinApplicationRepository;
    private final RoomStateRepository roomStateRepository;
    private final StorageUtil storageUtil;
    private final PasswordUtil passwordUtil;
    private final CheckerUtil checkerUtil;

    @Autowired
    public FirmEmployeeServiceImpl(FirmRepository firmRepository,
                                   FirmEmployeesRepository firmEmployeesRepository,
                                   FirmEmployeeServiceRecordRepository firmEmployeeServiceRecordRepository,
                                   HouseRepository houseRepository,
                                   RoomRepository roomRepository,
                                   CheckinApplicationRepository checkinApplicationRepository,
                                   RoomStateRepository roomStateRepository,
                                   StorageUtil storageUtil,
                                   PasswordUtil passwordUtil,
                                   CheckerUtil checkerUtil) {
        this.firmRepository = firmRepository;
        this.firmEmployeesRepository = firmEmployeesRepository;
        this.firmEmployeeServiceRecordRepository = firmEmployeeServiceRecordRepository;
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.checkinApplicationRepository = checkinApplicationRepository;
        this.roomStateRepository = roomStateRepository;
        this.storageUtil = storageUtil;
        this.passwordUtil = passwordUtil;
        this.checkerUtil = checkerUtil;
    }

    @Override
    public FirmEmployees register(RegisterFirmEmployeeRequest request) throws RepeatedAccountException, RoleNotFoundException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException, FirmNotFoundException {
        FirmEmployees firmEmployees = checkerUtil.checkRegisterFirmMappingAndReturn(request);
        checkerUtil.checkRepeatedAccount(firmEmployees.getAccount());
//        checkerUtil.checkRepeatedEmail(firmEmployees.getEmail());
//        checkerUtil.checkRepeatedLineId(firmEmployees.getLineId());
        checkerUtil.checkRepeatedIdentityCard(firmEmployees.getIdentityCard());
        String plainPassword = firmEmployees.getPassword();
        String hashPassword = passwordUtil.generateHashPassword(plainPassword);
        firmEmployees.setPassword(hashPassword);
        return firmEmployeesRepository.save(firmEmployees);
    }

    @Override
    public List<CheckinApplicationBrief> getAssignedCheckinApplications(String firmEmployeeId, String yearAndMonth) throws ParseException{

        String startDateString = yearAndMonth + "-01 00:00:00";
        String endDateString = yearAndMonth + "-31 23:59:59";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = simpleDateFormat.parse(startDateString);
        Date endDate = simpleDateFormat.parse(endDateString);

        List<RoomState> roomStateList = roomStateRepository.findAllByStartDateBetween(startDate, endDate);
        List<CheckinApplicationBrief> checkinApplicationBriefList = checkinApplicationRepository.findAllByFirmEmployeesIdAndRoomStateIn(firmEmployeeId, roomStateList);

        return checkinApplicationBriefList;
    }


    @Override
    public String addServiceRecord(String firmEmployeeId, String host, SendFirmEmployeeServiceRecordRequest request) throws CheckinApplicationNotFoundException, UploadCleaningImageException, CleaningImageTypeMismatchException, HouseNotFoundException, RoomNotFoundException, FirmEmployeeNotFoundException {

        CheckinApplication checkinApplication = checkinApplicationRepository.findById(request.getCheckinAppId()).orElseThrow(CheckinApplicationNotFoundException::new);
        House house = houseRepository.findByName(request.getHouseName()).orElseThrow(HouseNotFoundException::new);
        Room room = roomRepository.findByHouseAndNumber(house, request.getRoomNumber()).orElseThrow(RoomNotFoundException::new);
        FirmEmployees firmEmployees = firmEmployeesRepository.findById(firmEmployeeId).orElseThrow(FirmEmployeeNotFoundException::new);

        String[] filePaths = storageUtil.storeFirmEmployeeCleaningImageMetaData(new FirmEmployeeCleaningImageMetaData(host, request.getBeforeImage(), request.getAfterImage()));

        FirmEmployeeServiceRecord firmEmployeeServiceRecord = new FirmEmployeeServiceRecord();
        firmEmployeeServiceRecord.setCheckinApplication(checkinApplication);
        firmEmployeeServiceRecord.setFirmEmployees(firmEmployees);
        firmEmployeeServiceRecord.setHouse(house);
        firmEmployeeServiceRecord.setRoom(room);
        firmEmployeeServiceRecord.setService(request.getService());
        firmEmployeeServiceRecord.setRemark(request.getRemark());
        firmEmployeeServiceRecord.setBeforeImage(filePaths[0]);
        firmEmployeeServiceRecord.setAfterImage(filePaths[1]);
        firmEmployeeServiceRecordRepository.save(firmEmployeeServiceRecord);

        return "firmEmployeeId : " + firmEmployeeId + " send service record success ! ";
    }

    @Override
    public List<FirmEmployeeServiceRecordDetail> getFirmEmployeeServiceRecord(String firmEmployeeId, String houseName, Integer roomNumber, String yearAndMonth) throws ParseException {

        String startDateString = yearAndMonth + "-01 00:00:00";
        String endDateString = yearAndMonth + "-31 23:59:59";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = simpleDateFormat.parse(startDateString);
        Date endDate = simpleDateFormat.parse(endDateString);

        List<FirmEmployeeServiceRecordDetail> firmEmployeeServiceRecordDetailList = firmEmployeeServiceRecordRepository.findAllBySearchCondition(firmEmployeeId, startDate, endDate, houseName, roomNumber);

        return firmEmployeeServiceRecordDetailList;
    }

    @Override
    public FirmAndFirmEmployeeNameResponse getFirmAndFirmEmployeeName(String firmEmployeeId) throws FirmNotFoundException, FirmEmployeeNotFoundException {

        FirmEmployees firmEmployees = firmEmployeesRepository.findByIdAndVerified(firmEmployeeId, true).orElseThrow(FirmEmployeeNotFoundException::new);

        return new FirmAndFirmEmployeeNameResponse(firmEmployees.getFirm().getId(), firmEmployees.getFirm().getName(), firmEmployees.getId(), firmEmployees.getChineseName());
    }

}
