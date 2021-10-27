package org.eden.lovestation.service;

import org.eden.lovestation.dao.model.FirmEmployees;
import org.eden.lovestation.dto.projection.CheckinApplicationBrief;
import org.eden.lovestation.dto.projection.FirmEmployeeServiceRecordDetail;
import org.eden.lovestation.dto.request.RegisterFirmEmployeeRequest;
import org.eden.lovestation.dto.request.SendFirmEmployeeServiceRecordRequest;
import org.eden.lovestation.dto.response.FirmAndFirmEmployeeNameResponse;
import org.eden.lovestation.exception.business.*;

import java.text.ParseException;
import java.util.List;

public interface FirmEmployeeService {
    FirmEmployees register(RegisterFirmEmployeeRequest request) throws RepeatedAccountException, RoleNotFoundException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException, FirmNotFoundException;

    List<CheckinApplicationBrief> getAssignedCheckinApplications(String firmEmployeeId, String yearAndMonth) throws ParseException;

    String addServiceRecord(String firmEmployeeId, String host, SendFirmEmployeeServiceRecordRequest request) throws CheckinApplicationNotFoundException, UploadCleaningImageException, CleaningImageTypeMismatchException, HouseNotFoundException, RoomNotFoundException, FirmEmployeeNotFoundException;

    List<FirmEmployeeServiceRecordDetail> getFirmEmployeeServiceRecord(String firmEmployeeId, String houseName, Integer roomNumber, String yearAndMonth) throws ParseException;

    FirmAndFirmEmployeeNameResponse getFirmAndFirmEmployeeName(String firmEmployeeId) throws FirmNotFoundException, FirmEmployeeNotFoundException;
}


