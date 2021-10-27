package org.eden.lovestation.controller;

import org.eden.lovestation.dao.model.CheckinApplication;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.CheckinApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/checkin-applications")
public class CheckinApplicationController {

    private final CheckinApplicationService checkinApplicationService;

    @Autowired
    public CheckinApplicationController(CheckinApplicationService checkinApplicationService) {
        this.checkinApplicationService = checkinApplicationService;
    }

    @PostMapping(value = "/first-stage")
    public ResponseEntity<?> applyFirstStageCheckinApplication(@RequestBody ApplyFirstStageCheckinApplicationRequest request, Principal principal) throws UserNotFoundException, RoomOccupiedException, ReferralEmployeeNotFoundException, DateFormatParseException, RoomNotFoundException {
        CheckinApplication checkinApplication = checkinApplicationService.applyFirstStageCheckinApplication(request, principal.getName());
        return new ResponseEntity<>(Map.of("id", checkinApplication.getId()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/before-checkout/all")
    public ResponseEntity<?> findUserCheckinApplication(Principal principal) throws UserNotFoundException, CheckinApplicationNotFoundException, RoomStateNotFoundException{
        return new ResponseEntity<>(checkinApplicationService.findAllUserCheckinApplicationsBeforeCheckout(principal.getName()), HttpStatus.OK);
    }

    @GetMapping(value = "/latest")
    public ResponseEntity<?> findUserLatestCheckinApplication(Principal principal) throws UserNotFoundException, CheckinApplicationNotFoundException{
        return new ResponseEntity<>(checkinApplicationService.findUserLatestCheckinApplication(principal.getName()), HttpStatus.OK);
    }

    @GetMapping(value = "/identity-card/finished/latest")
    public ResponseEntity<?> findUserLatestFinishedCheckinApplication(@RequestHeader(value = "x-eden-identity-card") String identityCard, Principal principal) throws UserNotFoundException {
        return new ResponseEntity<>(checkinApplicationService.findUserLatestFinishedCheckinApplication(identityCard, principal.getName()), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/detail")
    public ResponseEntity<?> findOwnedCheckinApplication(@PathVariable String id, Principal principal) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException {
        return ResponseEntity.ok(checkinApplicationService.findOwnedCheckinApplication(id, principal.getName()));
    }

    @GetMapping(value = "/user/search")
    public ResponseEntity<?> findCheckinApplicationPaged(@RequestParam(value = "houseName", required = false) String houseName,
                                                         @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                         @RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                         @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage,
                                                         Principal principal) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException {
        return ResponseEntity.ok(checkinApplicationService.findUserCheckinApplicationSearchPaged(startDate, endDate, houseName, currentPage, principal.getName()));
    }

    @GetMapping(value = "/user/search/interval")
    public ResponseEntity<?> findCheckinApplicationPaged(@RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                         @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                         @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage,
                                                         Principal principal) throws UserNotFoundException, CheckinApplicationNotFoundException{
        return ResponseEntity.ok(checkinApplicationService.findUserCheckinApplicationIntervalSearchPaged(principal.getName(), startDate, endDate, currentPage));
    }

    @GetMapping(value = "/referral-employee/search")
    public ResponseEntity<?> findCheckinApplicationPaged(@RequestParam(value = "houseName", required = false) String houseName, @RequestParam(value = "startDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam(value = "endDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam(value = "userChineseName", required = false) String userChineseName, @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage, Principal principal) throws CheckinApplicationStageEnumConvertException, UserNotFoundException, ReferralEmployeeNotFoundException, AdminNotFoundException, RoleNotFoundException, ReferralNotFoundException {
        return ResponseEntity.ok(checkinApplicationService.findReferralEmployeeCheckinApplicationSearchPaged(userChineseName, startDate, endDate, houseName, currentPage, principal.getName()));
    }

    @GetMapping(value = "/landlord/search")
    public ResponseEntity<?> findCheckinApplicationPaged(@RequestParam(value = "houseName") String houseName,
                                                         @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                         @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                         @RequestParam(value = "roomNumber", required = false, defaultValue = "0") Integer roomNumber,
                                                         @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage,
                                                         Principal principal) throws ParseException{
        return ResponseEntity.ok(checkinApplicationService.findLandlordCheckinApplicationSearchPaged(principal.getName(), houseName, roomNumber, startDate, endDate, currentPage));
    }

    @GetMapping(value = "/volunteer/search")
    public ResponseEntity<?> findCheckinApplicationPaged(@RequestParam(value = "houseName") String houseName,
                                                         @RequestParam(value = "yearAndMonth") String yearAndMonth,
                                                         @RequestParam(value = "roomNumber") Integer roomNumber,
                                                         @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage,
                                                         Principal principal) throws ParseException{
        return ResponseEntity.ok(checkinApplicationService.findVolunteerCheckinApplicationSearchPaged(principal.getName(), houseName, roomNumber, yearAndMonth, currentPage));
    }

    @GetMapping(value = "/firm-employee/search")
    public ResponseEntity<?> findCheckinApplicationPaged(@RequestParam(value = "houseName") String houseName,
                                                         @RequestParam(value = "roomNumber", required = false, defaultValue = "0") Integer roomNumber,
                                                         @RequestParam(value = "yearAndMonth") String yearAndMonth,
                                                         @RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage,
                                                         Principal principal) throws ParseException{
        return ResponseEntity.ok(checkinApplicationService.findFirmEmployeeCheckinApplicationSearchPaged(principal.getName(), houseName, roomNumber, yearAndMonth, currentPage));
    }

    @PostMapping(value = "/{id}/first-stage/files")
    public ResponseEntity<?> uploadFileToFirstStageCheckinApplications(@PathVariable String id, @RequestHeader("host") String host, @ModelAttribute @Valid UploadRentAndAffidavitImageFileRequest request, Principal principal) throws RentImageTypeMismatchException, AffidavitImageTypeMismatchException, UploadRentAndAffidavitImageException, UserNotFoundException {
        checkinApplicationService.uploadRentAndAffidavitImageFile(request, id, host, principal.getName());
        checkinApplicationService.uploadRentAndAffidavitImageFile(request, id, host, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{checkinAppId}/roomState/new")
    public ResponseEntity<?> sendUserNewRoomState(@PathVariable String checkinAppId, @RequestBody SendNewRoomStateRequest request) throws CheckinApplicationNotFoundException, RoomStateChangeExistException, RoomStateDuplicatedException, LineNotificationException, ParseException {
        return new ResponseEntity<>(checkinApplicationService.sendNewRoomState(checkinAppId, request), HttpStatus.OK);
    }

}
