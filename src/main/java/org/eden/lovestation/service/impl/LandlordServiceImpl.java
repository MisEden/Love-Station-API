package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.model.LandlordServiceRecord;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.projection.*;
import org.eden.lovestation.dto.request.AdminUpdateLandlordContactRequest;
import org.eden.lovestation.dto.request.ExportExcelRequest;
import org.eden.lovestation.dto.request.RegisterLandlordRequest;
import org.eden.lovestation.dto.request.SendServiceRecordRequest;
import org.eden.lovestation.dto.response.LandlordContactSearchResultPagedResponse;
import org.eden.lovestation.dto.response.LandlordSearchResultPagedResponse;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.LandlordService;
import org.eden.lovestation.util.checker.CheckerUtil;
import org.eden.lovestation.util.excel.ExcelUtil;
import org.eden.lovestation.util.password.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LandlordServiceImpl implements LandlordService {

    private final LandlordRepository landlordRepository;
    private final LandlordServiceRecordRepository landlordServiceRecordRepository;
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final CheckinApplicationRepository checkinApplicationRepository;
    private final RoomStateRepository roomStateRepository;
    private final CheckerUtil checkerUtil;
    private final PasswordUtil passwordUtil;
    private final ExcelUtil excelUtil;

    @Autowired
    public LandlordServiceImpl(LandlordRepository landlordRepository,
                               LandlordServiceRecordRepository landlordServiceRecordRepository,
                               HouseRepository houseRepository,
                               RoomRepository roomRepository,
                               CheckinApplicationRepository checkinApplicationRepository,
                               RoomStateRepository roomStateRepository,
                               CheckerUtil checkerUtil,
                               PasswordUtil passwordUtil,
                               ExcelUtil excelUtil) {
        this.landlordRepository = landlordRepository;
        this.landlordServiceRecordRepository = landlordServiceRecordRepository;
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.checkinApplicationRepository = checkinApplicationRepository;
        this.roomStateRepository = roomStateRepository;
        this.checkerUtil = checkerUtil;
        this.passwordUtil = passwordUtil;
        this.excelUtil = excelUtil;
    }




    @Override
    public Landlord register(RegisterLandlordRequest request) throws RepeatedAccountException, RoleNotFoundException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException {
        Landlord landlord = checkerUtil.checkRegisterLandlordMappingAndReturn(request);
        checkerUtil.checkRepeatedAccount(landlord.getAccount());
//        checkerUtil.checkRepeatedEmail(landlord.getEmail());
//        checkerUtil.checkRepeatedLineId(landlord.getLineId());
        checkerUtil.checkRepeatedIdentityCard(landlord.getIdentityCard());
        String plainPassword = landlord.getPassword();
        String hashPassword = passwordUtil.generateHashPassword(plainPassword);
        landlord.setPassword(hashPassword);
        return landlordRepository.save(landlord);
    }



    @Override
    public List<HouseName> getHouseName(String landlordId) throws HouseNotFoundException {

        List<HouseName> houseNameList = houseRepository.findAllByLandlordId(landlordId);
        if (houseNameList.isEmpty()){
            throw new HouseNotFoundException();
        }

        return houseNameList;
    }

    @Override
    public List<CheckinApplicationForHouse> getCheckinAppForHouse(String houseId, String yearAndMonth) throws HouseNotFoundException, CheckinApplicationNotFoundException, ParseException {

        String startDateString = yearAndMonth + "-01 00:00:00";
        String endDateString = yearAndMonth + "-31 23:59:59";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = simpleDateFormat.parse(startDateString);
        Date endDate = simpleDateFormat.parse(endDateString);

        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);
        List<RoomState> roomStateList = roomStateRepository.findAllByStartDateBetweenOrEndDateBetween(startDate, endDate, startDate, endDate);
        List<CheckinApplicationForHouse> checkinApplicationForHouseList = checkinApplicationRepository.findAllByHouseAndSecondVerifiedAndRoomStateIn(house, true, roomStateList);

        if (checkinApplicationForHouseList.size() == 0){
            throw new CheckinApplicationNotFoundException();
        }

        return checkinApplicationForHouseList;
    }

    @Override
    public String addServiceRecord(SendServiceRecordRequest request, String landlordId) throws CheckinApplicationNotFoundException, LandlordNotFoundException, HouseNotFoundException, RoomNotFoundException {

        CheckinApplication checkinApplication = checkinApplicationRepository.findById(request.getCheckinAppId()).orElseThrow(CheckinApplicationNotFoundException::new);

        Landlord landlord = landlordRepository.findById(landlordId).orElseThrow(LandlordNotFoundException::new);

        House house = houseRepository.findById(request.getHouseId()).orElseThrow(HouseNotFoundException::new);

        Room room = roomRepository.findByHouseAndNumber(house, request.getRoomNumber()).orElseThrow();

        LandlordServiceRecord landlordServiceRecord = new LandlordServiceRecord();
        landlordServiceRecord.setCheckinApplication(checkinApplication);
        landlordServiceRecord.setLandlord(landlord);
        landlordServiceRecord.setHouse(house);
        landlordServiceRecord.setRoom(room);
        landlordServiceRecord.setService(request.getService());
        landlordServiceRecord.setRemark(request.getRemark());
        landlordServiceRecordRepository.save(landlordServiceRecord);

        return "landlord : " + landlordId + " add service record success ! ";
    }


}
