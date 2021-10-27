package org.eden.lovestation.controller;

import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/v1/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping(value = "/users/register-verification")
    public ResponseEntity<?> sendUserRegisterVerification(@RequestBody @Valid SendUserRegisterVerificationRequest request) throws UserNotFoundException, LineNotificationException {
        notificationService.sendUserRegisterVerification(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/referral-employees/register-verification")
    public ResponseEntity<?> sendReferralEmployeeRegisterVerification(@RequestBody @Valid SendReferralEmployeeRegisterVerificationRequest request) throws UserNotFoundException, LineNotificationException, ReferralEmployeeNotFoundException, IOException, InterruptedException {
        notificationService.sendReferralEmployeeRegisterVerification(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/landlords/register-verification")
    public ResponseEntity<?> sendLandlordRegisterVerification(@RequestBody @Valid SendLandlordRegisterVerificationRequest request) throws UserNotFoundException, LineNotificationException, ReferralEmployeeNotFoundException, IOException, InterruptedException, LandlordNotFoundException {
        notificationService.sendLandlordRegisterVerification(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/volunteers/register-verification")
    public ResponseEntity<?> sendVolunteerRegisterVerification(@RequestBody @Valid SendVolunteerRegisterVerificationRequest request) throws UserNotFoundException, LineNotificationException, ReferralEmployeeNotFoundException, IOException, InterruptedException, LandlordNotFoundException, VolunteerNotFoundException {
        notificationService.sendVolunteerRegisterVerification(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/firm-employees/register-verification")
    public ResponseEntity<?> sendFirmEmployeeRegisterVerification(@RequestBody @Valid SendFirmEmployeeRegisterVerificationRequest request) throws LineNotificationException, FirmEmployeeNotFoundException {
        notificationService.sendFirmEmployeeRegisterVerification(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(value = "/users/checkin-applications/first-stage/verification")
    public ResponseEntity<?> sendUserFirstStageCheckinApplicationVerification(@RequestBody @Valid SendUserFirstStageCheckinApplicationVerificationRequest request) throws UserNotFoundException, LineNotificationException, CheckinApplicationNotFoundException {
        notificationService.sendUserFirstStageCheckinApplicationVerification(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/users/checkin-applications/second-stage/verification")
    public ResponseEntity<?> sendUserSecondStageCheckinApplicationVerification(@RequestBody @Valid SendUserSecondStageCheckinApplicationVerificationRequest request) throws UserNotFoundException, LineNotificationException, CheckinApplicationNotFoundException {
        notificationService.sendUserSecondStageCheckinApplicationVerification(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/referral-employees/checkin-applications/second-stage/verification")
    public ResponseEntity<?> sendReferralEmployeeSecondStageCheckinApplicationVerification(@RequestBody @Valid SendReferralEmployeeSecondStageCheckinApplicationVerificationRequest request) throws UserNotFoundException, LineNotificationException, CheckinApplicationNotFoundException {
        notificationService.sendReferralEmployeeSecondStageCheckinApplicationVerification(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/finish-checkin")
    public ResponseEntity<?> sendCheckinFinish(@RequestParam @Valid String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException, RoomStateNotFoundException {
        return new ResponseEntity<>(notificationService.sendCheckinFinish(checkinAppId), HttpStatus.OK);
    }

    @PostMapping(value = "/finish-checkout")
    public ResponseEntity<?> sendCheckoutFinish(@RequestParam @Valid String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException, RoomStateNotFoundException {
        return new ResponseEntity<>(notificationService.sendCheckoutFinish(checkinAppId), HttpStatus.OK);
    }

    @PostMapping(value = "/{checkinAppId}/room-states/change/verified")
    public ResponseEntity<?> sendRoomStateChangeVerified(@PathVariable String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException, RoomStateNotFoundException, RoomStateChangeNotFoundException {
        notificationService.sendRoomStateChangeVerified(checkinAppId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/{checkinAppId}/room-states/change/verify-denied")
    public ResponseEntity<?> sendRoomStateChangeVerifyDenied(@PathVariable String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException, RoomStateNotFoundException, RoomStateChangeNotFoundException {
        notificationService.sendRoomStateChangeVerifyDenied(checkinAppId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/{checkinAppId}/checkout/remind")
    public ResponseEntity<?> sendUserCheckoutRemind(@PathVariable String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException{
        notificationService.sendCheckoutRemind(checkinAppId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
