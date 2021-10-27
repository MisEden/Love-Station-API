package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.projection.*;
import org.eden.lovestation.dto.request.ExportExcelRequest;
import org.eden.lovestation.dto.request.RegisterVolunteerRequest;
import org.eden.lovestation.dto.request.SendServiceRecordRequest;
import org.eden.lovestation.dto.request.AdminUpdateVolunteerContact;
import org.eden.lovestation.dto.response.VolunteerContactSearchResultPagedResponse;
import org.eden.lovestation.dto.response.VolunteerSearchResultPagedResponse;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.VolunteerService;
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
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final VolunteerServiceRecordRepository volunteerServiceRecordRepository;
    private final CheckinApplicationRepository checkinApplicationRepository;
    private final RoomStateRepository roomStateRepository;
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final CheckerUtil checkerUtil;
    private final PasswordUtil passwordUtil;
    private final ExcelUtil excelUtil;

    @Autowired
    public VolunteerServiceImpl(VolunteerRepository volunteerRepository,
                                VolunteerServiceRecordRepository volunteerServiceRecordRepository,
                                CheckinApplicationRepository checkinApplicationRepository,
                                RoomStateRepository roomStateRepository,
                                HouseRepository houseRepository,
                                RoomRepository roomRepository,
                                CheckerUtil checkerUtil,
                                PasswordUtil passwordUtil,
                                ExcelUtil excelUtil) {
        this.volunteerRepository = volunteerRepository;
        this.volunteerServiceRecordRepository = volunteerServiceRecordRepository;
        this.checkinApplicationRepository = checkinApplicationRepository;
        this.roomStateRepository = roomStateRepository;
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.checkerUtil = checkerUtil;
        this.passwordUtil = passwordUtil;
        this.excelUtil = excelUtil;
    }

    @Override
    public Volunteer register(RegisterVolunteerRequest request) throws RepeatedAccountException, RoleNotFoundException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException {
        Volunteer volunteer = checkerUtil.checkRegisterVolunteerMappingAndReturn(request);
        checkerUtil.checkRepeatedAccount(volunteer.getAccount());
//        checkerUtil.checkRepeatedEmail(volunteer.getEmail());
//        checkerUtil.checkRepeatedLineId(volunteer.getLineId());
        checkerUtil.checkRepeatedIdentityCard(volunteer.getIdentityCard());
        String plainPassword = volunteer.getPassword();
        String hashPassword = passwordUtil.generateHashPassword(plainPassword);
        volunteer.setPassword(hashPassword);
        return volunteerRepository.save(volunteer);
    }

    @Override
    public List<CheckinApplicationBrief> getAssignedCheckinApplications(String volunteerId, String yearAndMonth) throws ParseException{

        String startDateString = yearAndMonth + "-01 00:00:00";
        String endDateString = yearAndMonth + "-31 23:59:59";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = simpleDateFormat.parse(startDateString);
        Date endDate = simpleDateFormat.parse(endDateString);

        List<RoomState> roomStateList = roomStateRepository.findAllByStartDateBetween(startDate, endDate);
        List<CheckinApplicationBrief> checkinApplicationBriefList = checkinApplicationRepository.findAllByVolunteerIdAndRoomStateIn(volunteerId, roomStateList);
        return checkinApplicationBriefList;
    }

    @Override
    public String addVolunteerServiceRecord(SendServiceRecordRequest request, String volunteerId) throws CheckinApplicationNotFoundException, VolunteerNotFoundException, HouseNotFoundException, RoomNotFoundException{

        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow(VolunteerNotFoundException::new);

        House house = houseRepository.findById(request.getHouseId()).orElseThrow(HouseNotFoundException::new);

        Room room = roomRepository.findByHouseAndNumber(house, request.getRoomNumber()).orElseThrow(RoomNotFoundException::new);

        CheckinApplication checkinApplication = checkinApplicationRepository.findById(request.getCheckinAppId()).orElseThrow(CheckinApplicationNotFoundException::new);

        VolunteerServiceRecord volunteerServiceRecord = new VolunteerServiceRecord();
        volunteerServiceRecord.setCheckinApplication(checkinApplication);
        volunteerServiceRecord.setVolunteer(volunteer);
        volunteerServiceRecord.setHouse(house);
        volunteerServiceRecord.setRoom(room);
        volunteerServiceRecord.setService(request.getService());
        volunteerServiceRecord.setRemark(request.getRemark());
        volunteerServiceRecordRepository.save(volunteerServiceRecord);

        return "volunteer : " + volunteerId + " add service record success ! ";
    }

    @Override
    public List<VolunteerServiceRecordDetail> getVolunteerServiceRecord(String volunteerId, String yearAndMonth) throws VolunteerServiceRecordNotFoundException, ParseException {

        String startDateString = yearAndMonth + "-01 00:00:00";
        String endDateString = yearAndMonth + "-31 23:59:59";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = simpleDateFormat.parse(startDateString);
        Date endDate = simpleDateFormat.parse(endDateString);

        List<VolunteerServiceRecordDetail> volunteerServiceRecordList = volunteerServiceRecordRepository.findAllBySearchCondition(volunteerId, startDate, endDate);

        return volunteerServiceRecordList;
    }


}
