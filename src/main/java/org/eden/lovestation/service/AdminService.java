package org.eden.lovestation.service;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dto.enums.CheckinApplicationStage;
import org.eden.lovestation.dto.projection.*;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.dto.response.*;
import org.eden.lovestation.exception.business.*;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AdminService {

    Admin register(RegisterAdministratorRequest request) throws RepeatedAccountException, RoleNotFoundException, RepeatedEmailException;

    //password
    void updateAdminPassword(String id, UpdateAdministratorPasswordRequest request) throws AdminNotFoundException, PasswordResetConfirmException, PasswordResetOriginalException;


    // index
    CheckinApplicationDetailPagedResponse findCheckinApplicationPaged(CheckinApplicationStage stage, int currentPage, String id) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException;
    AdminGetScheduledCheckinResponse getScheduledCheckinThatDay(String startDate, int currentPage) throws RoomNotFoundException, CheckinApplicationNotFoundException, UserNotFoundException, ParseException;
    AdminGetScheduledCheckoutResponse getScheduledCheckoutThatDay(String endDate, int currentpage) throws RoomNotFoundException, CheckinApplicationNotFoundException, UserNotFoundException, ParseException;
    CheckinActualTimeResponse getCheckinActualTime(String roomStateId) throws RoomStateNotFoundException, ParseException;
    CheckoutActualTimeResponse getCheckoutActualTime(String roomStateId) throws RoomStateNotFoundException, ParseException;
    Map<String, Integer> getNonViewServiceNumber();

    // CheckinApplication
    CheckinApplicationDetail findCheckinApplicationById(String id) throws CheckinApplicationNotFoundException;
    AdminCheckinApplicationSearchResultPagedResponse findAdminCheckinApplicationSearchPaged_allHouse(String userChineseName, Date startDate, Date endDate, Integer roomNumber, String houseName, String first, String second, int currentPage, String id);
    List<CheckinApplicationID> findAdminCheckinApplicationSearchPaged_allHouse_ID(String userChineseName, Date startDate, Date endDate, Integer roomNumber, String houseName, String first, String second, String id);
    String createCheckinApplicationHistoryExportExcel(List<String> checkinApplicationId) throws CheckinApplicationNotFoundException, DownloadExportExcelException;
    void updateFirstStageCheckinApplicationVerification(UpdateFirstStageCheckinApplicationVerificationRequest request, String checkinApplication, String adminId) throws RoomNotFoundException, DateFormatParseException, RoomOccupiedException, CheckinApplicationNotFoundException, AdminNotFoundException;
    void updateSecondStageCheckinApplicationVerification(UpdateSecondStageCheckinApplicationVerificationRequest request, String checkinApplicationId) throws CheckinApplicationNotFoundException;
    CheckinApplicationWithRoomStateChangeInfoAdminNeedResponse getRoomStateChange(int currentPage);
    CheckinApplicationWithRoomStateChangeDetail getRoomStateChangeDetail(String checkinAppId) throws CheckinApplicationNotFoundException, RoomStateChangeNotFoundException;
    String updateRoomStateWithRoomStateChange(String checkinAppId, Boolean verified, Date newStartDate, Date newEndDate, String deniedReason) throws CheckinApplicationNotFoundException, RoomStateChangeNotFoundException, RoomStateNotFoundException;
    AdminCheckinApplicationBriefPagedResponse getCheckinApplicationWithVolunteerDateAndFirmEmployeeDate(String yearAndMonth, int currentPage) throws CheckinApplicationNotFoundException, ParseException;
    String assignVolunteerAndFirmEmployee(AssignVolunteerAndFirmEmployeeRequest request) throws CheckinApplicationNotFoundException, VolunteerNotFoundException, FirmEmployeeNotFoundException, LineNotificationException;
    AdminCheckinApplicationSearchResultPagedResponse findCheckinApplicationSearchPagedForAssignedChange(String userChineseName, Date startDate, Date endDate, Integer roomNumber, String houseName, int currentPage, String id) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException;

    // check_account
    UserRegisterVerificationPagedResponse findUserRegisterVerificationPaged(int currentPage);
    void updateUserRegisterVerification(UpdateUserRegisterVerificationRequest request, String userId);
    ReferralEmployeeRegisterVerificationPagedResponse findReferralEmployeeRegisterVerificationPaged(int currentPage);
    void updateReferralEmployeeRegisterVerification(UpdateReferralEmployeeRegisterVerificationRequest request, String referralEmployeeId);
    LandlordRegisterVerificationPagedResponse findLandlordRegisterVerificationPaged(int currentPage);
    void updateLandlordRegisterVerification(UpdateLandlordRegisterVerificationRequest request, String landlordId);
    VolunteerRegisterVerificationPagedResponse findVolunteerRegisterVerificationPaged(int currentPage);
    void updateVolunteerRegisterVerification(UpdateVolunteerRegisterVerificationRequest request, String volunteerId);
    FirmEmployeeRegisterVerificationPagedResponse findFirmEmployeeRegisterVerification(int currentPage);
    void updateFirmEmployeeRegisterVerification(UpdateFirmEmployeeRegisterVerificationRequest request, String firmEmployeeId);


    // Role - Referral
    ReferralSearchResultPagedResponse findReferralByKeyword(String keyword, int currentPage);
    List<ReferralSearchResult> findReferralByKeywordWithoutPage(String keyword);

    // Role - Landlord
    List<LandlordName> getLandlordNames() throws LandlordNotFoundException;
    LandlordContactSearchResultPagedResponse findLandlordContactByKeyword(String keyword, int currentPage);
    List<LandlordContact> findLandlordContactByKeywordWithoutPage(String keyword);

    // Role - Volunteer
    List<VolunteerName> getVolunteerNames();
    VolunteerContactSearchResultPagedResponse findVolunteerContactByKeyword(String keyword, int currentPage);
    List<VolunteerContact> findVolunteerContactByKeywordWithoutPage(String keyword);

    // Role - Firm
    List<FirmName> getFirmNames();
    FirmSearchResultPagedResponse findFirmsByKeyword(String keyword, int currentPage);
    List<FirmDetail> findFirmsByKeywordWithoutPage(String keyword);
    List<FirmEmployeeName> getFirmEmployeeNames();

    // Partners - Referrals
    Referral insertReferral(AdminUpdateReferral request) throws IncorrectFormatCityException, RepeatedReferralHospitalChineseNameException, RepeatedReferralHospitalEnglishNameException, RepeatedReferralNumberException;
    Referral updateReferral(AdminUpdateReferral request, String id) throws ReferralNotFoundException, IncorrectFormatCityException, RepeatedReferralHospitalChineseNameException, RepeatedReferralHospitalEnglishNameException, RepeatedReferralNumberException;
    String createReferralExportExcel(List<String> referralId) throws ReferralNotFoundException, DownloadExportExcelException;

    // Partners - Firm
    Firm insertFirm(AdminUpdateFirm request) throws RepeatedFirmNameException;
    Firm updateFirm(AdminUpdateFirm request, String id) throws FirmNotFoundException, RepeatedFirmNameException;
    String createFirmExportExcel(List<String> firmsId) throws FirmNotFoundException, DownloadExportExcelException;

    // Partners - Landlord
    Landlord updateLandlordById(AdminUpdateLandlordContactRequest request, String id) throws LandlordNotFoundException;
    String createLandlordExportExcel(List<String> landlordId) throws LandlordNotFoundException, DownloadExportExcelException;

    // Partners - Volunteer
    Volunteer updateVolunteerContactById(AdminUpdateVolunteerContact request, String id) throws VolunteerNotFoundException;
    String createVolunteerExportExcel(List<String> volunteerId) throws VolunteerNotFoundException, DownloadExportExcelException;



    // Feedback
    AdminCheckinApplicationSearchResultPagedResponse findAdminCheckinApplicationSearchPaged(String userChineseName, Date startDate, Date endDate, Integer roomNumber, String houseName, int currentPage, String id) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException;
    Map<String, Object> getCheckinForm(String checkinApplicationId) throws CheckinFormNotFoundException, CheckinFormConfirmNotFoundException, CheckinApplicationNotFoundException;
    CheckoutFeedbackResponse getUserFeedback(String checkinAppId) throws RoomStateNotFoundException, CheckinApplicationNotFoundException, CheckoutFormNotFoundException;
    Map<String, Object> getCheckoutInvestigation(String checkinAppId) throws RoomStateNotFoundException, CheckinApplicationNotFoundException, InvestigationNotFoundException;


    // Service
    List<LandlordServiceRecordDetail> getLandlordServiceRecords(String houseCity, String houseId, String roomId, String landlordId, Date startDate, Date endDate) throws ParseException;
    List<LandlordServiceRecordDetail> getLandlordServiceRecords_nonView();
    String createLandlordsRecordExportExcel(List<String> serviceId) throws LandlordServiceRecordNotFoundException, DownloadExportExcelException;
    void landlordServiceRecordsViewed(List<String> serviceId) throws LandlordServiceRecordNotFoundException;
    List<VolunteerServiceRecordDetail> getVolunteerServiceRecords(String houseCity, String houseId, String roomId, String volunteerId, Date startDate, Date endDate) throws ParseException;
    List<VolunteerServiceRecordDetail> getVolunteerServiceRecords_nonView();
    void volunteerServiceRecordsViewed(List<String> serviceId) throws VolunteerServiceRecordNotFoundException;
    String createVolunteersRecordExportExcel(List<String> serviceId) throws VolunteerServiceRecordNotFoundException, DownloadExportExcelException;
    List<FirmEmployeeServiceRecordDetailByAdmin> getFirmServiceRecords(String houseCity, String houseId, String roomId, String firmId, Date startDate, Date endDate) throws ParseException;
    List<FirmEmployeeServiceRecordDetailByAdmin> getFirmServiceRecords_nonView();
    void firmServiceRecordsViewed(List<String> serviceId) throws FirmServiceRecordNotFoundException;
    String createFirmsRecordExportExcel(List<String> serviceId) throws FirmServiceRecordNotFoundException, DownloadExportExcelException;


    // House
    House insertNewHouse(HouseInsert request) throws RepeatedHouseNameException, LandlordNotFoundException;
    House updateHouse(String houseId, HouseEdit request) throws HouseNotFoundException, LandlordNotFoundException;
    House updateHouseDisable(String houseId) throws HouseNotFoundException;
    void uploadHouseImage(String houseId, String host, UploadHouseImagesRequest request) throws HouseNotFoundException, HouseImageTypeMismatchException, UploadHouseImageException;
    void deleteHouseImage(String houseImageId) throws HouseImageNotFoundException, DeleteHouseImageException;
    void uploadHousePlanimetricMap(String houseId, String host, UploadHousePlanimetricMapRequest request) throws HouseNotFoundException, HousePlanimetricMapExistedException, HousePlanimetricMapTypeMismatchException, UploadHousePlanimetricMapException;
    void deleteHousePlanimetricMap(String houseId) throws HouseNotFoundException, HousePlanimetricMapNotFoundException, DeleteHousePlanimetricMapException;
    void uploadHouseFullDegreePanorama(String houseId, String host, UploadHouseFullDegreePanoramaRequest request) throws HouseNotFoundException, HouseFullDegreePanoramaExistedException, HouseFullDegreePanoramaTypeMismatchException, UploadHouseFullDegreePanoramaException;
    void deleteHouseFullDegreePanorama(String houseId) throws HouseNotFoundException, HouseFullDegreePanoramaNotFoundException, DeleteHouseFullDegreePanoramaException;
    Room insertNewRoom(String houseId, int roomNumber) throws HouseNotFoundException, RepeatedRoomNumberException;


    // Room
    Room updateRoomInfo(String roomId, int roomNumber, boolean roomDisable)throws RoomNotFoundException, RepeatedRoomNumberException;


    // Furniture
    org.eden.lovestation.dao.model.HousePublicFurniture insertHousePublicFurniture(String houseId, String furnitureName) throws HouseNotFoundException, FurnitureNotFoundException;
    void deleteHousePublicFurniture(String publicFurnitureId) throws FurnitureNotFoundException;
    org.eden.lovestation.dao.model.HousePrivateFurniture insertHousePrivateFurniture(String roomId, String furnitureName) throws RoomNotFoundException, FurnitureNotFoundException;
    void deleteHousePrivateFurniture(String privateFurnitureId) throws FurnitureNotFoundException;
    PublicFurniture insertPublicFurniture(String furnitureName) throws RepeatedPublicFurnitureNameException;
    PrivateFurniture insertPrivateFurniture(String furnitureName) throws RepeatedPrivateFurnitureNameException;
    void exchangePublicFurnitureIndex(String furnitureId_A, String furnitureId_B) throws PublicFurnitureNotFoundException;
    void exchangePrivateFurnitureIndex(String furnitureId_A, String furnitureId_B) throws PrivateFurnitureNotFoundException;
    PublicFurniture renamePublicFurnitureIndex(String furnitureId, String furnitureName)throws PublicFurnitureNotFoundException;
    PrivateFurniture renamePrivateFurnitureIndex(String furnitureId, String furnitureName)throws PrivateFurnitureNotFoundException;
    void deletePublicFurniture(String furnitureId)throws PublicFurnitureNotFoundException;
    void deletePrivateFurniture(String furnitureId)throws PrivateFurnitureNotFoundException;


    // Authority
    AdminAccountSearchResultPagedResponse findAdminsByKeyword(String role, String keyword, int currentPage, String admin_id) throws AdminNotFoundException;
    void changeAdminAuthority(String admin_id, String role_name) throws  AdminNotFoundException, RoleNotFoundException;
    List<AdminRegisterVerification> findAdminRegisterVerification();
    AdminRegisterVerification findAdminRegisterVerificationById(String id) throws AdminNotFoundException;
    void updateAdminRegisterVerification(String admin_id, UpdateAdministratorRegisterVerificationRequest request) throws AdminNotFoundException, RoleNotFoundException;
    Admin insertNewAdmin(RegisterAdministratorByAdministratorRequest request) throws RepeatedAccountException, RepeatedEmailException, RoleNotFoundException;
}
