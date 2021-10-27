package org.eden.lovestation.service;

import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.dto.response.NotificationDateResponse;
import org.eden.lovestation.exception.business.*;

import java.util.Map;

public interface NotificationService {

    void sendUserRegisterVerification(SendUserRegisterVerificationRequest request) throws LineNotificationException, UserNotFoundException;

    void sendReferralEmployeeRegisterVerification(SendReferralEmployeeRegisterVerificationRequest request) throws ReferralEmployeeNotFoundException, LineNotificationException;

    void sendLandlordRegisterVerification(SendLandlordRegisterVerificationRequest request) throws LandlordNotFoundException, LineNotificationException;

    void sendFirmEmployeeRegisterVerification(SendFirmEmployeeRegisterVerificationRequest request) throws FirmEmployeeNotFoundException, LineNotificationException;

    void sendVolunteerRegisterVerification(SendVolunteerRegisterVerificationRequest request) throws VolunteerNotFoundException, LineNotificationException;

    void sendUserFirstStageCheckinApplicationVerification(SendUserFirstStageCheckinApplicationVerificationRequest request) throws LineNotificationException, CheckinApplicationNotFoundException, UserNotFoundException;

    void sendUserSecondStageCheckinApplicationVerification(SendUserSecondStageCheckinApplicationVerificationRequest request) throws LineNotificationException, CheckinApplicationNotFoundException;

    void sendReferralEmployeeSecondStageCheckinApplicationVerification(SendReferralEmployeeSecondStageCheckinApplicationVerificationRequest request) throws CheckinApplicationNotFoundException, LineNotificationException;

    NotificationDateResponse sendCheckinFinish(String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException, RoomStateNotFoundException;

    NotificationDateResponse sendCheckoutFinish(String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException, RoomStateNotFoundException;

    void sendRoomStateChangeVerified(String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException, RoomStateNotFoundException, RoomStateChangeNotFoundException;

    void sendRoomStateChangeVerifyDenied(String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException, RoomStateNotFoundException, RoomStateChangeNotFoundException;

    void sendNotificationForget(Map<String, String> userInfo) throws LineNotificationException, UserNotFoundException, ReferralEmployeeNotFoundException;

    void sendNotificationRepassword(Map<String, String> userInfo) throws LineNotificationException, UserNotFoundException, ReferralEmployeeNotFoundException;

    void sendCheckoutRemind(String CheckinAppId) throws LineNotificationException, CheckinApplicationNotFoundException;
}
