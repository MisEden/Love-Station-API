package org.eden.lovestation.controller;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.repository.HousePrivateFurnitureRepository;
import org.eden.lovestation.dao.repository.HousePublicFurnitureRepository;
import org.eden.lovestation.dto.enums.CheckinApplicationStage;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/admins")
public class AdminController {
    
    private final AdminService adminService;

    private final HousePublicFurnitureRepository housePublicFurnitureRepository;
    private final HousePrivateFurnitureRepository housePrivateFurnitureRepository;

    @Autowired
    public AdminController(AdminService adminService,
                           HousePublicFurnitureRepository housePublicFurnitureRepository,
                           HousePrivateFurnitureRepository housePrivateFurnitureRepository) {
        this.adminService = adminService;
        this.housePublicFurnitureRepository = housePublicFurnitureRepository;
        this.housePrivateFurnitureRepository = housePrivateFurnitureRepository;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterAdministratorRequest request) throws RepeatedAccountException, RoleNotFoundException, RepeatedEmailException {
        Admin admin = adminService.register(request);
        return new ResponseEntity<>(Map.of("id", admin.getId()), HttpStatus.CREATED);
    }



    //password
    @PutMapping(value = "/password")
    public ResponseEntity<?> updateAdminPassword(@RequestBody @Valid UpdateAdministratorPasswordRequest request, Principal principal) throws PasswordResetConfirmException, AdminNotFoundException, PasswordResetOriginalException {
        adminService.updateAdminPassword(principal.getName(), request);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    // index
    @GetMapping(value = "/index/checkin-applications/detail")
    public ResponseEntity<?> findCheckinApplicationPaged(@RequestParam(value = "stage", required = false, defaultValue = "all") CheckinApplicationStage stage, @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage, Principal principal) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException {
        return ResponseEntity.ok(adminService.findCheckinApplicationPaged(stage, currentPage, principal.getName()));
    }

    @GetMapping(value = "/index/scheduled-checkin/{startDate}")
    public ResponseEntity<?> getScheduledCheckin(@PathVariable String startDate,
                                                 @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) throws RoomNotFoundException, CheckinApplicationNotFoundException, UserNotFoundException, ParseException {
        return ResponseEntity.ok(adminService.getScheduledCheckinThatDay(startDate, currentPage));
    }

    @GetMapping(value = "/index/scheduled-checkout/{endDate}")
    public ResponseEntity<?> getScheduledCheckout(@PathVariable String endDate,
                                                  @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) throws RoomNotFoundException, CheckinApplicationNotFoundException, UserNotFoundException, ParseException {
        return new ResponseEntity<>(adminService.getScheduledCheckoutThatDay(endDate, currentPage), HttpStatus.OK);
    }

    @GetMapping(value = "/index/scheduled-checkin/actual-checkin-time/{roomStateId}")
    public ResponseEntity<?> getCheckinFormCreatedAt(@PathVariable String roomStateId) throws RoomStateNotFoundException, ParseException{
        return new ResponseEntity<>(adminService.getCheckinActualTime(roomStateId), HttpStatus.OK);
    }

    @GetMapping(value = "/index/scheduled-checkout/actual-checkout-time/{roomStateId}")
    public ResponseEntity<?> getCheckoutFeedbackCreatedAt(@PathVariable String roomStateId) throws RoomStateNotFoundException, ParseException{
        return new ResponseEntity<>(adminService.getCheckoutActualTime(roomStateId), HttpStatus.OK);
    }

    @GetMapping(value = "/index/service/number")
    public ResponseEntity<?> getNonViewService(){
        return new ResponseEntity<>(adminService.getNonViewServiceNumber(), HttpStatus.OK);
    }



    // CheckinApplication
    @GetMapping(value = "/checkin-applications/{id}")
    public ResponseEntity<?> getCheckinApplicationById(@PathVariable String id) throws CheckinApplicationNotFoundException {
        return new ResponseEntity<>(adminService.findCheckinApplicationById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/checkin-applications/")
    public ResponseEntity<?> findCheckinApplicationPagedForAssignedChange(@RequestParam(value = "houseName", required = false) String houseName,
                                                                          @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                          @RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                                          @RequestParam(value = "roomNumber", required = false) Integer roomNumber,
                                                                          @RequestParam(value = "userChineseName", required = false) String userChineseName,
                                                                          @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage, Principal principal) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException {
        return ResponseEntity.ok(adminService.findCheckinApplicationSearchPagedForAssignedChange(userChineseName, startDate, endDate, roomNumber, houseName, currentPage, principal.getName()));
    }

    @PatchMapping(value = "/checkin-applications/{id}/first-stage/verification")
    public ResponseEntity<?> updateFirstStageCheckinApplications(@PathVariable String id, @RequestBody @Valid UpdateFirstStageCheckinApplicationVerificationRequest request, Principal principal) throws CheckinApplicationNotFoundException, RoomOccupiedException, RoomNotFoundException, DateFormatParseException, AdminNotFoundException {
        adminService.updateFirstStageCheckinApplicationVerification(request, id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/checkin-applications/{id}/second-stage/verification")
    public ResponseEntity<?> updateSecondStageCheckinApplications(@PathVariable String id, @RequestBody @Valid UpdateSecondStageCheckinApplicationVerificationRequest request) throws CheckinApplicationNotFoundException {
        adminService.updateSecondStageCheckinApplicationVerification(request, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/checkin-applications/room-states/change")
    public ResponseEntity<?> getCheckinApplicationsRoomStateChanges(@RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
        return new ResponseEntity<>(adminService.getRoomStateChange(currentPage), HttpStatus.OK);
    }

    @GetMapping(value = "/checkin-applications/{checkinAppId}/room-states/change")
    public ResponseEntity<?> getCheckinApplicationsRoomStateChangesDetail(@PathVariable String checkinAppId, Principal principal) throws CheckinApplicationNotFoundException, RoomStateChangeNotFoundException{
        return new ResponseEntity<>(adminService.getRoomStateChangeDetail(checkinAppId), HttpStatus.OK);
    }

    @PatchMapping(value = "/{checkinAppId}/update/room-states/change")
    public ResponseEntity<?> updateCheckinApplicationRoomStateChange(@PathVariable String checkinAppId,
                                                                     @RequestParam(value = "verified") Boolean verified,
                                                                     @RequestParam(value = "newStartDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date newStartDate,
                                                                     @RequestParam(value = "newEndDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date newEndDate,
                                                                     @RequestParam(value = "deniedReason", required = false, defaultValue = "") String deniedReason,
                                                                     Principal principal) throws CheckinApplicationNotFoundException, RoomStateChangeNotFoundException, RoomStateNotFoundException{
        return new ResponseEntity<>(adminService.updateRoomStateWithRoomStateChange(checkinAppId, verified, newStartDate, newEndDate, deniedReason), HttpStatus.OK);
    }

    @GetMapping(value = "/checkin-applications/volunteer-date/firm-employee-date")
    public ResponseEntity<?> getCheckinApplicationWithVolunteerDateAndFirmEmployeeDate(@RequestParam(value = "yearAndMonth") String yearAndMonth,
                                                                                       @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage,
                                                                                       Principal principal) throws CheckinApplicationNotFoundException, ParseException{
        return new ResponseEntity<>(adminService.getCheckinApplicationWithVolunteerDateAndFirmEmployeeDate(yearAndMonth, currentPage), HttpStatus.OK);
    }

    @PatchMapping(value = "/checkin-applications/assign/volunteer/firm-employee")
    public ResponseEntity<?> setCheckinApplicationVolunteerAndFirmEmployee(@RequestBody @Valid AssignVolunteerAndFirmEmployeeRequest request,
                                                                           Principal principal) throws CheckinApplicationNotFoundException, VolunteerNotFoundException, FirmEmployeeNotFoundException, LineNotificationException{
        return new ResponseEntity<>(adminService.assignVolunteerAndFirmEmployee(request), HttpStatus.OK);
    }

    @GetMapping(value = "/checkin-applications/search")
    public ResponseEntity<?> findCheckinApplicationPaged(@RequestParam(value = "houseName", required = false) String houseName,
                                                                          @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                          @RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                                          @RequestParam(value = "roomNumber", required = false) Integer roomNumber,
                                                                          @RequestParam(value = "userChineseName", required = false) String userChineseName,
                                                                          @RequestParam(value = "first", required = false) String resultFirst,
                                                                          @RequestParam(value = "second", required = false) String resultSecond,
                                                                          @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage, Principal principal) {
        return ResponseEntity.ok(adminService.findAdminCheckinApplicationSearchPaged_allHouse(userChineseName, startDate, endDate, roomNumber, houseName, resultFirst, resultSecond, currentPage, principal.getName()));
    }

    @GetMapping(value = "/checkin-applications/id/search")
    public ResponseEntity<?> findCheckinApplicationId(@RequestParam(value = "houseName", required = false) String houseName,
                                                                          @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                          @RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                                          @RequestParam(value = "roomNumber", required = false) Integer roomNumber,
                                                                          @RequestParam(value = "userChineseName", required = false) String userChineseName,
                                                                          @RequestParam(value = "first", required = false) String resultFirst,
                                                                          @RequestParam(value = "second", required = false) String resultSecond, Principal principal) {
        return ResponseEntity.ok(adminService.findAdminCheckinApplicationSearchPaged_allHouse_ID(userChineseName, startDate, endDate, roomNumber, houseName, resultFirst, resultSecond, principal.getName()));
    }
    @PostMapping(value = "/checkin-applications/export")
    public ResponseEntity<?> exportCheckinApplicationDataExcel(@RequestBody @Valid DataExport request) throws CheckinApplicationNotFoundException, DownloadExportExcelException {
        String downloadText = adminService.createCheckinApplicationHistoryExportExcel(Arrays.asList(request.getId()));
        System.out.println("[Created Export Excel File] " + downloadText);
        return new ResponseEntity<>(Map.of("downloadUrl", downloadText),HttpStatus.OK);
    }



    // check_account
    @GetMapping(value = "/check-account/users")
    public ResponseEntity<?> getUserRegisterVerifications(@RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
        return ResponseEntity.ok(adminService.findUserRegisterVerificationPaged(currentPage));
    }

    @PatchMapping(value = "/check-account/users/{id}")
    public ResponseEntity<?> updateUserVerification(@PathVariable String id, @RequestBody @Valid UpdateUserRegisterVerificationRequest request) {
        adminService.updateUserRegisterVerification(request, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/check-account/referral-employees")
    public ResponseEntity<?> getReferralEmployeeRegisterVerifications(@RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
        return new ResponseEntity<>(adminService.findReferralEmployeeRegisterVerificationPaged(currentPage), HttpStatus.OK);
    }

    @PatchMapping(value = "/check-account/referral-employees/{id}")
    public ResponseEntity<?> updateReferralEmployeeRegisterVerification(@PathVariable String id, @RequestBody @Valid UpdateReferralEmployeeRegisterVerificationRequest request) {
        adminService.updateReferralEmployeeRegisterVerification(request, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/check-account/landlords")
    public ResponseEntity<?> getLandlordRegisterVerifications(@RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
        return ResponseEntity.ok(adminService.findLandlordRegisterVerificationPaged(currentPage));
    }

    @PatchMapping(value = "/check-account/landlords/{id}")
    public ResponseEntity<?> updateLandlordVerification(@PathVariable String id, @RequestBody @Valid UpdateLandlordRegisterVerificationRequest request) {
        adminService.updateLandlordRegisterVerification(request, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/check-account/volunteers")
    public ResponseEntity<?> getVolunteerRegisterVerifications(@RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
        return ResponseEntity.ok(adminService.findVolunteerRegisterVerificationPaged(currentPage));
    }

    @PatchMapping(value = "/check-account/volunteers/{id}")
    public ResponseEntity<?> updateVolunteerRegisterVerification(@PathVariable String id, @RequestBody @Valid UpdateVolunteerRegisterVerificationRequest request) {
        adminService.updateVolunteerRegisterVerification(request, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/check-account/firm-employees")
    public ResponseEntity<?> getFirmEmployeeRegisterVerifications(@RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) {
        return ResponseEntity.ok(adminService.findFirmEmployeeRegisterVerification(currentPage));
    }

    @PatchMapping(value = "/check-account/firm-employees/{id}")
    public ResponseEntity<?> updateFirmEmployeeRegisterVerification(@PathVariable String id, @RequestBody @Valid UpdateFirmEmployeeRegisterVerificationRequest request) {
        adminService.updateFirmEmployeeRegisterVerification(request, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    // Role
    @GetMapping(value = "/role/referrals/search")
    public ResponseEntity<?> getAllReferralsDetailByKeyword(@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage){
        return new ResponseEntity<>(adminService.findReferralByKeyword(keyword, currentPage), HttpStatus.OK);
    }

    @GetMapping(value = "/role/referrals/search/all")
    public ResponseEntity<?> getAllReferralsDetailByKeywordWithoutPage(@RequestParam(value = "keyword", required = false) String keyword){
        return new ResponseEntity<>(adminService.findReferralByKeywordWithoutPage(keyword), HttpStatus.OK);
    }

    @GetMapping(value = "/role/landlords/names/all")
    public ResponseEntity<?> getAllLandlordNames() throws LandlordNotFoundException {
        return new ResponseEntity<>(adminService.getLandlordNames(), HttpStatus.OK);
    }

    @GetMapping(value = "/role/landlords/search/contact")
    public ResponseEntity<?> getAllLandlordContactByKeyword(@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage){
        return new ResponseEntity<>(adminService.findLandlordContactByKeyword(keyword, currentPage), HttpStatus.OK);
    }

    @GetMapping(value = "/role/landlords/search/contact/all")
    public ResponseEntity<?> getAllLandlordContactByKeywordWithoutPage(@RequestParam(value = "keyword", required = false) String keyword){
        return new ResponseEntity<>(adminService.findLandlordContactByKeywordWithoutPage(keyword), HttpStatus.OK);
    }

    @GetMapping(value = "/role/volunteers/names/all")
    public ResponseEntity<?> getAllVolunteerNames() {
        return new ResponseEntity<>(adminService.getVolunteerNames(), HttpStatus.OK);
    }

    @GetMapping(value = "/role/volunteers/search/contact")
    public ResponseEntity<?> getAllVolunteerContactByKeyword(@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage){
        return new ResponseEntity<>(adminService.findVolunteerContactByKeyword(keyword, currentPage), HttpStatus.OK);
    }

    @GetMapping(value = "/role/volunteers/search/contact/all")
    public ResponseEntity<?> getAllVolunteerContactByKeywordWithoutPage(@RequestParam(value = "keyword", required = false) String keyword){
        return new ResponseEntity<>(adminService.findVolunteerContactByKeywordWithoutPage(keyword), HttpStatus.OK);
    }

    @GetMapping(value = "/role/firms/names/all")
    public ResponseEntity<?> getAllFirmNames() {
        return new ResponseEntity<>(adminService.getFirmNames(), HttpStatus.OK);
    }

    @GetMapping(value = "/role/firms/search")
    public ResponseEntity<?> getAllFirmsDetailByKeyword(@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage){
        return new ResponseEntity<>(adminService.findFirmsByKeyword(keyword, currentPage), HttpStatus.OK);
    }

    @GetMapping(value = "/role/firms/search/all")
    public ResponseEntity<?> getAllFirmsDetailByKeywordWithoutPage(@RequestParam(value = "keyword", required = false) String keyword){
        return new ResponseEntity<>(adminService.findFirmsByKeywordWithoutPage(keyword), HttpStatus.OK);
    }

    @GetMapping(value = "/role/firm-employees/names/all")
    public ResponseEntity<?> getAllFirmEmployees(){
        return new ResponseEntity<>(adminService.getFirmEmployeeNames(), HttpStatus.OK);
    }



    // Partners - Referrals
    @PostMapping(value = "/partners/referrals/")
    public ResponseEntity<?> insertReferral(@RequestBody @Valid AdminUpdateReferral request) throws RepeatedReferralHospitalEnglishNameException, RepeatedReferralNumberException, IncorrectFormatCityException, RepeatedReferralHospitalChineseNameException {
        return new ResponseEntity<>(Map.of("id", adminService.insertReferral(request).getId()), HttpStatus.CREATED);
    }

    @PutMapping(value = "/partners/referrals/{id}")
    public ResponseEntity<?> updateReferral(@RequestBody @Valid AdminUpdateReferral request, @PathVariable String id) throws ReferralNotFoundException, RepeatedReferralHospitalEnglishNameException, IncorrectFormatCityException, RepeatedReferralNumberException, RepeatedReferralHospitalChineseNameException {
        return new ResponseEntity<>(Map.of("id", adminService.updateReferral(request, id).getId()), HttpStatus.OK);
    }

    @PostMapping(value = "/partners/referrals/export")
    public ResponseEntity<?> exportReferralDataExcel(@RequestBody @Valid DataExport request) throws ReferralNotFoundException, DownloadExportExcelException {
        String downloadText = adminService.createReferralExportExcel(Arrays.asList(request.getId()));
        System.out.println("[Created Export Excel File] " + downloadText);
        return new ResponseEntity<>(Map.of("downloadUrl", downloadText),HttpStatus.OK);
    }



    // Partners - Firm
    @PostMapping(value = "/partners/firms")
    public ResponseEntity<?> insertFirm(@RequestBody @Valid AdminUpdateFirm request) throws RepeatedFirmNameException {
        return new ResponseEntity<>(Map.of("id", adminService.insertFirm(request).getId()), HttpStatus.CREATED);
    }

    @PutMapping(value = "/partners/firms/{id}")
    public ResponseEntity<?> updateFirm(@RequestBody @Valid AdminUpdateFirm request, @PathVariable String id) throws FirmNotFoundException, RepeatedFirmNameException {
        return new ResponseEntity<>(Map.of("id", adminService.updateFirm(request, id).getId()), HttpStatus.OK);
    }

    @PostMapping(value = "/partners/firms/export")
    public ResponseEntity<?> exportFirmDataExcel(@RequestBody @Valid DataExport request) throws FirmNotFoundException, DownloadExportExcelException {
        String downloadText = adminService.createFirmExportExcel(Arrays.asList(request.getId()));
        System.out.println("[Created Export Excel File] " + downloadText);
        return new ResponseEntity<>(Map.of("downloadUrl", downloadText),HttpStatus.OK);
    }



    // Partners - Landlord
    @PutMapping(value = "/partners/landlords/{id}/contact")
    public ResponseEntity<?> updateLandlordById(@RequestBody @Valid AdminUpdateLandlordContactRequest request, @PathVariable String id) throws LandlordNotFoundException {
        return new ResponseEntity<>(Map.of("id", adminService.updateLandlordById(request, id).getId()), HttpStatus.OK);
    }

    @PostMapping(value = "/partners/landlords/export")
    public ResponseEntity<?> exportLandlordDataExcel(@RequestBody @Valid DataExport request) throws LandlordNotFoundException, DownloadExportExcelException {
        String downloadText = adminService.createLandlordExportExcel(Arrays.asList(request.getId()));
        System.out.println("[Created Export Excel File] " + downloadText);
        return new ResponseEntity<>(Map.of("downloadUrl", downloadText),HttpStatus.OK);
    }



    // Partners - Volunteer
    @PutMapping(value = "/partners/volunteers/{id}/contact")
    public ResponseEntity<?> updateVolunteerContactById(@RequestBody @Valid AdminUpdateVolunteerContact request, @PathVariable String id) throws VolunteerNotFoundException {
        return new ResponseEntity<>(Map.of("id", adminService.updateVolunteerContactById(request, id).getId()), HttpStatus.OK);
    }

    @PostMapping(value = "/partners/volunteers/export")
    public ResponseEntity<?> exportVolunteerDataExcel(@RequestBody @Valid DataExport request) throws VolunteerNotFoundException, DownloadExportExcelException {
        String downloadText = adminService.createVolunteerExportExcel(Arrays.asList(request.getId()));
        System.out.println("[Created Export Excel File] " + downloadText);
        return new ResponseEntity<>(Map.of("downloadUrl", downloadText),HttpStatus.OK);
    }



    // Feedback
    @GetMapping(value = "/feedback/checkin-applications/search")
    public ResponseEntity<?> findCheckinApplicationPaged(@RequestParam(value = "houseName", required = false) String houseName,
                                                         @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                         @RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                         @RequestParam(value = "roomNumber", required = false) Integer roomNumber,
                                                         @RequestParam(value = "userChineseName", required = false) String userChineseName,
                                                         @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage, Principal principal) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException {
        return ResponseEntity.ok(adminService.findAdminCheckinApplicationSearchPaged(userChineseName, startDate, endDate, roomNumber, houseName, currentPage, principal.getName()));
    }

    @GetMapping(value = "/feedback/checkin/{checkinApplicationId}")
    public ResponseEntity<?> getCheckinForm(@PathVariable String checkinApplicationId) throws CheckinFormNotFoundException, CheckinFormConfirmNotFoundException, CheckinApplicationNotFoundException {
        return new ResponseEntity<>(adminService.getCheckinForm(checkinApplicationId), HttpStatus.OK);
    }

    @GetMapping(value = "/feedback/checkout/{checkinAppId}")
    public ResponseEntity<?> getUserCheckoutFeedback(@PathVariable String checkinAppId) throws RoomStateNotFoundException, CheckinApplicationNotFoundException, CheckoutFormNotFoundException {
        return new ResponseEntity<>(adminService.getUserFeedback(checkinAppId), HttpStatus.OK);
    }

    @GetMapping(value = "/feedback/investigation/{checkinAppId}")
    public ResponseEntity<?> getUserCheckoutInvestigation(@PathVariable String checkinAppId) throws RoomStateNotFoundException, CheckinApplicationNotFoundException, InvestigationNotFoundException {
        return new ResponseEntity<>(adminService.getCheckoutInvestigation(checkinAppId), HttpStatus.OK);
    }



    // Service
    @GetMapping(value = "/service/landlords/record")
    public ResponseEntity<?> getLandlordServiceRecords(@RequestParam(value = "houseCity", required = false) String houseCity,
                                                       @RequestParam(value = "houseId", required = false) String houseId,
                                                       @RequestParam(value = "roomId", required = false) String roomId,
                                                       @RequestParam(value = "landlordId", required = false) String landlordId,
                                                       @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                       @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws ParseException {

        return new ResponseEntity<>(adminService.getLandlordServiceRecords(houseCity, houseId, roomId, landlordId, startDate, endDate), HttpStatus.OK);
    }

    @GetMapping(value = "/service/landlords/record/non-view")
    public ResponseEntity<?> getLandlordServiceRecords_nonView(){
        return new ResponseEntity<>(adminService.getLandlordServiceRecords_nonView(), HttpStatus.OK);
    }

    @PostMapping(value = "/service/landlords/record/viewed")
    public ResponseEntity<?> viewedLandlordServiceRecord(@RequestBody ViewedLandlordServiceRecords request) throws LandlordServiceRecordNotFoundException {
        adminService.landlordServiceRecordsViewed(Arrays.asList(request.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/service/landlords/record/export")
    public ResponseEntity<?> exportLandlordsRecordDataExcel(@RequestBody @Valid DataExport request) throws LandlordServiceRecordNotFoundException, DownloadExportExcelException {
        String downloadText = adminService.createLandlordsRecordExportExcel(Arrays.asList(request.getId()));
        System.out.println("[Created Export Excel File] " + downloadText);
        return new ResponseEntity<>(Map.of("downloadUrl", downloadText),HttpStatus.OK);
    }

    @GetMapping(value = "/service/volunteers/record")
    public ResponseEntity<?> getVolunteerServiceRecords(@RequestParam(value = "houseCity", required = false) String houseCity,
                                                        @RequestParam(value = "houseId", required = false) String houseId,
                                                        @RequestParam(value = "roomId", required = false) String roomId,
                                                        @RequestParam(value = "volunteerId", required = false) String volunteerId,
                                                        @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                        @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws ParseException {

        return new ResponseEntity<>(adminService.getVolunteerServiceRecords(houseCity, houseId, roomId, volunteerId, startDate, endDate), HttpStatus.OK);
    }

    @GetMapping(value = "/service/volunteers/record/non-view")
    public ResponseEntity<?> getVolunteerServiceRecords_nonView(){
        return new ResponseEntity<>(adminService.getVolunteerServiceRecords_nonView(), HttpStatus.OK);
    }

    @PostMapping(value = "/service/volunteers/record/viewed")
    public ResponseEntity<?> viewedVolunteersServiceRecord(@RequestBody ViewedLandlordServiceRecords request) throws VolunteerServiceRecordNotFoundException {
        adminService.volunteerServiceRecordsViewed(Arrays.asList(request.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/service/volunteers/record/export")
    public ResponseEntity<?> exportVolunteersRecordDataExcel(@RequestBody @Valid DataExport request) throws VolunteerServiceRecordNotFoundException, DownloadExportExcelException {
        String downloadText = adminService.createVolunteersRecordExportExcel(Arrays.asList(request.getId()));
        System.out.println("[Created Export Excel File] " + downloadText);
        return new ResponseEntity<>(Map.of("downloadUrl", downloadText),HttpStatus.OK);
    }
    @GetMapping(value = "/service/firms/record")
    public ResponseEntity<?> getFirmsServiceRecords(@RequestParam(value = "houseCity", required = false) String houseCity,
                                                       @RequestParam(value = "houseId", required = false) String houseId,
                                                       @RequestParam(value = "roomId", required = false) String roomId,
                                                       @RequestParam(value = "firmId", required = false) String firmId,
                                                       @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                       @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws ParseException {

        return new ResponseEntity<>(adminService.getFirmServiceRecords(houseCity, houseId, roomId, firmId, startDate, endDate), HttpStatus.OK);
    }

    @GetMapping(value = "/service/firms/record/non-view")
    public ResponseEntity<?> getFirmEmployeeServiceRecords_nonView(){
        return new ResponseEntity<>(adminService.getFirmServiceRecords_nonView(), HttpStatus.OK);
    }

    @PostMapping(value = "/service/firms/record/viewed")
    public ResponseEntity<?> viewedFirmServiceRecord(@RequestBody ViewedLandlordServiceRecords request) throws FirmServiceRecordNotFoundException {
        adminService.firmServiceRecordsViewed(Arrays.asList(request.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/service/firms/record/export")
    public ResponseEntity<?> exportFirmsRecordDataExcel(@RequestBody @Valid DataExport request) throws DownloadExportExcelException, FirmServiceRecordNotFoundException {
        String downloadText = adminService.createFirmsRecordExportExcel(Arrays.asList(request.getId()));
        System.out.println("[Created Export Excel File] " + downloadText);
        return new ResponseEntity<>(Map.of("downloadUrl", downloadText),HttpStatus.OK);
    }



    // house
    @PostMapping(value = "/houses")
    public ResponseEntity<?> insertNewHouse(@RequestBody @Valid HouseInsert request) throws RepeatedHouseNameException, LandlordNotFoundException {
        return new ResponseEntity<>(Map.of("id", adminService.insertNewHouse(request).getId()), HttpStatus.CREATED);
    }

    @PutMapping(value = "/houses/{id}")
    public ResponseEntity<?> updateHouse(@PathVariable String id, @RequestBody @Valid HouseEdit request) throws HouseNotFoundException, LandlordNotFoundException {
        return new ResponseEntity<>(Map.of("id", adminService.updateHouse(id, request).getId()), HttpStatus.OK);
    }

    @PutMapping(value = "/houses/{id}/disable")
    public ResponseEntity<?> updateDisableStatus(@PathVariable String id) throws HouseNotFoundException{
        return new ResponseEntity<>(Map.of("id", adminService.updateHouseDisable(id).getId()), HttpStatus.OK);
    }

    @PostMapping(value = "/houses/{id}/images")
    public ResponseEntity<?> uploadHouseImages(@PathVariable String id, @RequestHeader("host") String host, @ModelAttribute @Valid UploadHouseImagesRequest request) throws HouseNotFoundException, HouseImageTypeMismatchException, UploadHouseImageException {
        adminService.uploadHouseImage(id, host, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/houses/images/{id}")
    public ResponseEntity<?> deleteHouseImages(@PathVariable String id) throws HouseImageNotFoundException, DeleteHouseImageException {
        adminService.deleteHouseImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/houses/{id}/planimetric-map")
    public ResponseEntity<?> uploadHousePlanimetricMap(@PathVariable String id, @RequestHeader("host") String host, @ModelAttribute @Valid UploadHousePlanimetricMapRequest request) throws HouseNotFoundException, HousePlanimetricMapExistedException, HousePlanimetricMapTypeMismatchException, UploadHousePlanimetricMapException {
        adminService.uploadHousePlanimetricMap(id, host, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/houses/{id}/planimetric-map")
    public ResponseEntity<?> deleteHousePlanimetricMap(@PathVariable String id) throws HouseNotFoundException, HousePlanimetricMapNotFoundException, DeleteHousePlanimetricMapException {
        adminService.deleteHousePlanimetricMap(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(value = "/houses/{id}/full-degree-panorama")
    public ResponseEntity<?> uploadHouseFullDegreePanorama(@PathVariable String id, @RequestHeader("host") String host, @ModelAttribute @Valid UploadHouseFullDegreePanoramaRequest request) throws HouseNotFoundException, HouseFullDegreePanoramaExistedException, HouseFullDegreePanoramaTypeMismatchException, UploadHouseFullDegreePanoramaException {
        adminService.uploadHouseFullDegreePanorama(id, host, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/houses/{id}/full-degree-panorama")
    public ResponseEntity<?> deleteHouseFullDegreePanorama(@PathVariable String id) throws HouseNotFoundException, HouseFullDegreePanoramaNotFoundException, DeleteHouseFullDegreePanoramaException {
        adminService.deleteHouseFullDegreePanorama(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/houses/{id}/rooms")
    public ResponseEntity<?> insertNewRoom(@PathVariable String id, @RequestBody @Valid RoomInsert request) throws HouseNotFoundException, RepeatedRoomNumberException {
        return new ResponseEntity<>(adminService.insertNewRoom(id, request.getRoomNumber()), HttpStatus.OK);
    }



    // Room
    @PutMapping(value = "/rooms/{id}")
    public ResponseEntity<?> updateRoomInfo(@PathVariable String id, @RequestBody @Valid RoomInfomationUpdate request) throws RoomNotFoundException, RepeatedRoomNumberException{
        Room room = adminService.updateRoomInfo(id, request.getRoomNumber(), request.isRoomDisable());

        Map<String, Object> simpleRoomInfo = new HashMap<>();
        simpleRoomInfo.put("id", room.getId());
        simpleRoomInfo.put("number", room.getNumber());
        simpleRoomInfo.put("disable", room.isDisable());

        return new ResponseEntity<>(simpleRoomInfo, HttpStatus.OK);
    }



    // Furniture
    @PutMapping(value = "/furniture/public/status")
    public ResponseEntity<?> savePublicFurnitre(@RequestBody @Valid HousePublicFurnitureSave request) throws HouseNotFoundException, FurnitureNotFoundException {

        HousePublicFurniture housePublicFurniture =housePublicFurnitureRepository.findByFurnitureName(request.getHouseId(),request.getFurnitureName());
        if(housePublicFurniture == null){
            // Insert A HousePublicFurniture Record
            return new ResponseEntity<>(adminService.insertHousePublicFurniture(request.getHouseId(), request.getFurnitureName()), HttpStatus.OK);

        }else{
            // Delete The HousePublicFurniture Record
            adminService.deleteHousePublicFurniture(housePublicFurniture.getId());

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping(value = "/furniture/private/status")
    public ResponseEntity<?> savePrivateFurnitre(@RequestBody @Valid HousePrivateFurnitureSave request) throws RoomNotFoundException, FurnitureNotFoundException {

        HousePrivateFurniture housePrivateFurniture =housePrivateFurnitureRepository.findByFurnitureName(request.getRoomId(),request.getFurnitureName());
        if(housePrivateFurniture == null){
            // Insert A HousePrivateFurniture Record
            return new ResponseEntity<>(adminService.insertHousePrivateFurniture(request.getRoomId(), request.getFurnitureName()), HttpStatus.OK);

        }else{
            // Delete The HousePrivateFurniture Record
            adminService.deleteHousePrivateFurniture(housePrivateFurniture.getId());

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping(value = "/furniture/public")
    public ResponseEntity<?> insertNewPublicFurniture(@RequestBody @Valid PublicFurnitureInsert request) throws RepeatedPublicFurnitureNameException {
        return new ResponseEntity<>(adminService.insertPublicFurniture(request.getFurnitureName()), HttpStatus.OK);
    }

    @PostMapping(value = "/furniture/private")
    public ResponseEntity<?> insertNewPrivateFurniture(@RequestBody @Valid PrivateFurnitureInsert request) throws RepeatedPrivateFurnitureNameException {
        return new ResponseEntity<>(adminService.insertPrivateFurniture(request.getFurnitureName()), HttpStatus.OK);
    }

    @PutMapping(value = "/furniture/public/exchange")
    public ResponseEntity<?> exchangePublicFurnitureIndex(@RequestBody @Valid ExchangeFurniture request)throws PublicFurnitureNotFoundException{
        adminService.exchangePublicFurnitureIndex(request.getFurnitureId_A(), request.getFurnitureId_B());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/furniture/private/exchange")
    public ResponseEntity<?> exchangePrivateFurnitureIndex(@RequestBody @Valid ExchangeFurniture request)throws PrivateFurnitureNotFoundException{
        adminService.exchangePrivateFurnitureIndex(request.getFurnitureId_A(), request.getFurnitureId_B());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/furniture/public/rename")
    public ResponseEntity<?> renamePublicFurnitureIndex(@RequestBody @Valid FurnitureRename request)throws PublicFurnitureNotFoundException{
        return new ResponseEntity<>(adminService.renamePublicFurnitureIndex(request.getFurnitureId(), request.getFurnitureName_new()) ,HttpStatus.OK);
    }

    @PutMapping(value = "/furniture/private/rename")
    public ResponseEntity<?> renamePrivateFurnitureIndex(@RequestBody @Valid FurnitureRename request)throws PrivateFurnitureNotFoundException{
        return new ResponseEntity<>(adminService.renamePrivateFurnitureIndex(request.getFurnitureId(), request.getFurnitureName_new()) ,HttpStatus.OK);
    }

    @DeleteMapping(value = "/furniture/public/{id}")
    public ResponseEntity<?> deletePublicFurniture(@PathVariable String id)throws PublicFurnitureNotFoundException{
        adminService.deletePublicFurniture(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/furniture/private/{id}")
    public ResponseEntity<?> deletePrivateFurniture(@PathVariable String id)throws PrivateFurnitureNotFoundException{
        adminService.deletePrivateFurniture(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Authority
    @GetMapping(value = "/authority/account/search")
    public ResponseEntity<?> getAllAdmin(@RequestParam(value = "role", required = false) String role, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage, Principal principal) throws AdminNotFoundException {
        return new ResponseEntity<>(adminService.findAdminsByKeyword(role, keyword, currentPage, principal.getName()), HttpStatus.OK);
    }
    @PutMapping(value = "/authority/account/{id}")
    public ResponseEntity<?> changeAdminAuthority(@PathVariable String id, @RequestBody @Valid ChangeAdminAuthority request) throws AdminNotFoundException, RoleNotFoundException {
        adminService.changeAdminAuthority(id, request.getNewRoleName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/authority/register")
    public ResponseEntity<?> getAdminRegisterVerifications() {
        return ResponseEntity.ok(adminService.findAdminRegisterVerification());
    }

    @PostMapping(value = "/authority/register")
    public ResponseEntity<?> getAdminRegisterVerifications(@RequestBody @Valid RegisterAdministratorByAdministratorRequest request) throws RepeatedEmailException, RepeatedAccountException, RoleNotFoundException {
        Admin admin = adminService.insertNewAdmin(request);
        return new ResponseEntity<>(Map.of("id", admin.getId()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/authority/register/{id}")
    public ResponseEntity<?> getAdminRegisterVerificationsByid(@PathVariable String id) throws AdminNotFoundException {
        return ResponseEntity.ok(adminService.findAdminRegisterVerificationById(id));
    }

    @PutMapping(value = "/authority/register/{id}")
    public ResponseEntity<?> updateAdminRegisterVerification(@PathVariable String id, @RequestBody @Valid UpdateAdministratorRegisterVerificationRequest request) throws AdminNotFoundException, RoleNotFoundException {
        adminService.updateAdminRegisterVerification(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
