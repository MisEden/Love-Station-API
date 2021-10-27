package org.eden.lovestation.service;

import org.eden.lovestation.dao.model.CheckinApplication;
import org.eden.lovestation.dao.model.User;
import org.eden.lovestation.dto.enums.CheckinApplicationStage;
import org.eden.lovestation.dto.projection.CheckinApplicationBeforeCheckoutBrief;
import org.eden.lovestation.dto.projection.CheckinApplicationBrief;
import org.eden.lovestation.dto.projection.CheckinApplicationDetail;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.dto.response.*;
import org.eden.lovestation.exception.business.*;
import org.hibernate.annotations.Check;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface CheckinApplicationService {

    CheckinApplication applyFirstStageCheckinApplication(ApplyFirstStageCheckinApplicationRequest request, String referralEmployeeId) throws DateFormatParseException, RoomNotFoundException, RoomOccupiedException, UserNotFoundException, ReferralEmployeeNotFoundException;

    List<CheckinApplicationBeforeCheckoutBrief> findAllUserCheckinApplicationsBeforeCheckout(String userId) throws UserNotFoundException, CheckinApplicationNotFoundException, RoomStateNotFoundException;

    CheckinApplicationBrief findUserLatestCheckinApplication(String userId) throws UserNotFoundException, CheckinApplicationNotFoundException;

    Object findUserLatestFinishedCheckinApplication(String identityCard, String referralEmployeeId) throws UserNotFoundException;

    CheckinApplicationDetail findOwnedCheckinApplication(String checkinApplicationId, String id) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException;

    UserCheckinApplicationSearchResultPagedResponse findUserCheckinApplicationSearchPaged(Date startDate, Date endDate, String houseName, int currentPage, String id) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException;

    UserCheckinApplicationSearchResultPagedResponse findUserCheckinApplicationIntervalSearchPaged(String userId, Date startDate, Date endDate, int currentPage) throws UserNotFoundException, CheckinApplicationNotFoundException;

    ReferralEmployeeCheckinApplicationSearchResultPagedResponse findReferralEmployeeCheckinApplicationSearchPaged(String userChineseName, Date startDate, Date endDate, String houseName, int currentPage, String id) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException, ReferralNotFoundException;

    LandlordCheckinApplicationSearchResultPagedResponse findLandlordCheckinApplicationSearchPaged(String landlordId, String houseName, Integer roomNumber, Date startDate, Date endDate, int currentPage) throws ParseException;

    VolunteerCheckinApplicationSearchResultPagedResponse findVolunteerCheckinApplicationSearchPaged(String volunteerId, String houseName, Integer roomNumber, String yearAndMonth, int currentPage) throws ParseException;

    FirmEmployeeCheckinApplicationResultPagedResponse findFirmEmployeeCheckinApplicationSearchPaged(String FirmEmployeeId, String houseName, Integer roomNumber, String yearAndMonth, int currentPage) throws ParseException;

    void uploadRentAndAffidavitImageFile(UploadRentAndAffidavitImageFileRequest request, String checkinApplicationId, String host, String userId) throws AffidavitImageTypeMismatchException, RentImageTypeMismatchException, UploadRentAndAffidavitImageException, UserNotFoundException;

    String sendNewRoomState(String checkinAppId, SendNewRoomStateRequest request) throws CheckinApplicationNotFoundException, RoomStateChangeExistException, RoomStateDuplicatedException, LineNotificationException, ParseException;

}
