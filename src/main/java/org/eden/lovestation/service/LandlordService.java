package org.eden.lovestation.service;

import org.eden.lovestation.dao.model.Landlord;
import org.eden.lovestation.dto.projection.*;
import org.eden.lovestation.dto.request.AdminUpdateLandlordContactRequest;
import org.eden.lovestation.dto.request.RegisterLandlordRequest;
import org.eden.lovestation.dto.request.SendServiceRecordRequest;
import org.eden.lovestation.dto.response.LandlordContactSearchResultPagedResponse;
import org.eden.lovestation.dto.response.LandlordSearchResultPagedResponse;
import org.eden.lovestation.exception.business.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface LandlordService {

    Landlord register(RegisterLandlordRequest request) throws RepeatedAccountException, RoleNotFoundException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException;

    List<HouseName> getHouseName(String landlordId) throws HouseNotFoundException;

    List<CheckinApplicationForHouse> getCheckinAppForHouse(String houseId, String yearAndMonth) throws HouseNotFoundException, CheckinApplicationNotFoundException, ParseException;

    String addServiceRecord(SendServiceRecordRequest request, String landlordId) throws CheckinApplicationNotFoundException, LandlordNotFoundException, HouseNotFoundException, RoomNotFoundException;

}
