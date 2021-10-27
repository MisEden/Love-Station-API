package org.eden.lovestation.service;

import org.eden.lovestation.dao.model.Volunteer;
import org.eden.lovestation.dto.projection.CheckinApplicationBrief;
import org.eden.lovestation.dto.projection.VolunteerContact;
import org.eden.lovestation.dto.projection.VolunteerName;
import org.eden.lovestation.dto.projection.VolunteerServiceRecordDetail;
import org.eden.lovestation.dto.request.RegisterVolunteerRequest;
import org.eden.lovestation.dto.request.SendServiceRecordRequest;
import org.eden.lovestation.dto.request.AdminUpdateVolunteerContact;
import org.eden.lovestation.dto.response.VolunteerContactSearchResultPagedResponse;
import org.eden.lovestation.dto.response.VolunteerSearchResultPagedResponse;
import org.eden.lovestation.exception.business.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface VolunteerService {

    Volunteer register(RegisterVolunteerRequest request) throws RepeatedAccountException, RoleNotFoundException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException;

    List<CheckinApplicationBrief> getAssignedCheckinApplications(String volunteerId, String yearAndMonth) throws ParseException;

    String addVolunteerServiceRecord(SendServiceRecordRequest request, String volunteerId) throws CheckinApplicationNotFoundException, VolunteerNotFoundException, HouseNotFoundException, RoomNotFoundException;

    List<VolunteerServiceRecordDetail> getVolunteerServiceRecord(String volunteerId, String yearAndMonth) throws VolunteerServiceRecordNotFoundException, ParseException;

}

