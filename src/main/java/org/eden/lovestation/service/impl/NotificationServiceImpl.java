package org.eden.lovestation.service.impl;

import lombok.SneakyThrows;
import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.dto.response.NotificationDateResponse;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.NotificationService;
import org.eden.lovestation.util.line.LineUtil;
import org.eden.lovestation.util.line.Message;
import org.eden.lovestation.util.line.MessageType;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.Line;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final ReferralEmployeeRepository referralEmployeeRepository;
    private final HouseRepository houseRepository;
    private final LandlordRepository landlordRepository;
    private final FirmEmployeesRepository firmEmployeesRepository;
    private final VolunteerRepository volunteerRepository;
    private final ReferralNumberRepository referralNumberRepository;
    private final CheckinApplicationRepository checkinApplicationRepository;
    private final RoomStateRepository roomStateRepository;
    private final RoomStateChangeRepository roomStateChangeRepository;
    private final LineUtil lineUtil;

    private final String successRegisterVerificationMessage = "已成功註冊「愛心棧住房系統 EHomebot」服務!\n\n如欲使用本服務，請點擊下方選單圖示。\n" + "敬祝平安順心!";

    private final String failRegisterVerificationMessage = "註冊「愛心棧住房系統 EHomebot」服務失敗! 請再試一次";

    private final String commonMessage = "您好，在此通知您 ";

    private final String checkinFinishMessage = " 先生/小姐已完成入住程序了。\n";
    private final String checkoutFinishMessage = " 先生/小姐已完成退房程序了。\n";

    private final String roomStateChangeVerified = " 先生/小姐 申請變更 已通過。\n";
    private final String roomStateChangeVerifyDenied = " 先生/小姐 申請變更 已被拒絕。\n";

    @Autowired
    public NotificationServiceImpl(UserRepository userRepository,
                                   ReferralEmployeeRepository referralEmployeeRepository,
                                   HouseRepository houseRepository,
                                   LandlordRepository landlordRepository,
                                   FirmEmployeesRepository firmEmployeesRepository,
                                   VolunteerRepository volunteerRepository,
                                   ReferralNumberRepository referralNumberRepository,
                                   CheckinApplicationRepository checkinApplicationRepository,
                                   RoomStateRepository roomStateRepository,
                                   RoomStateChangeRepository roomStateChangeRepository,
                                   LineUtil lineUtil) {
        this.userRepository = userRepository;
        this.referralEmployeeRepository = referralEmployeeRepository;
        this.firmEmployeesRepository = firmEmployeesRepository;
        this.houseRepository = houseRepository;
        this.landlordRepository = landlordRepository;
        this.volunteerRepository = volunteerRepository;
        this.referralNumberRepository = referralNumberRepository;
        this.checkinApplicationRepository = checkinApplicationRepository;
        this.roomStateRepository = roomStateRepository;
        this.roomStateChangeRepository = roomStateChangeRepository;
        this.lineUtil = lineUtil;
    }

    @Transactional
    @Override
    public void sendUserRegisterVerification(SendUserRegisterVerificationRequest request) throws LineNotificationException, UserNotFoundException {
        String userId = request.getId();
        User user = userRepository.findByIdAndVerifiedIsNotNull(userId).orElseThrow(UserNotFoundException::new);
        ReferralEmployee referralEmployee = user.getReferralNumber().getReferralEmployee();

        String referral_employee_msg = new String();
        if (!user.getVerified()) {
            userRepository.deleteById(userId);
            referralNumberRepository.deleteByIdentityCard(user.getIdentityCard());
            referral_employee_msg = "您好，就醫民眾 " + user.getChineseName() + "註冊「愛心棧住房系統 EHomebot」服務失敗! 請再試一次";
        }else{
            referral_employee_msg = "您好，就醫民眾 " + user.getChineseName() + "已成功註冊「愛心棧住房系統 EHomebot」服務!\n\n" +
                    "提醒您~如欲繼續協助就醫民眾入住申請，請點 擊下方「入住申請」選單，謝謝您。\n\n" +
                    "感謝您使用本服務!";
        }

        String user_msg = "就醫民眾 " + user.getChineseName() + "，您好!\n" + prepareRegisterVerificationText(user.getVerified());

        lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, user_msg)));
        lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
    }

    @Transactional
    @Override
    public void sendReferralEmployeeRegisterVerification(SendReferralEmployeeRegisterVerificationRequest request) throws ReferralEmployeeNotFoundException, LineNotificationException {
        String referralEmployeeId = request.getId();
        ReferralEmployee referralEmployee = referralEmployeeRepository.findByIdAndVerifiedIsNotNull(referralEmployeeId).orElseThrow(ReferralEmployeeNotFoundException::new);
        if (!referralEmployee.getVerified()) {
            referralEmployeeRepository.deleteById(referralEmployeeId);
        }
        String referral_employee_msg = "轉介人員 " + referralEmployee.getName() + "，您好!\n" + prepareRegisterVerificationText(referralEmployee.getVerified());
        lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
    }

    @Transactional
    @Override
    public void sendLandlordRegisterVerification(SendLandlordRegisterVerificationRequest request) throws LandlordNotFoundException, LineNotificationException {
        String landlordId = request.getId();
        Landlord landlord = landlordRepository.findByIdAndVerifiedIsNotNull(landlordId).orElseThrow(LandlordNotFoundException::new);
        if (!landlord.getVerified()) {
            landlordRepository.deleteById(landlordId);
            referralNumberRepository.deleteByIdentityCard(landlord.getIdentityCard());
        }
        String landlord_msg = "房東 " + landlord.getChineseName() + "，您好!\n" + prepareRegisterVerificationText(landlord.getVerified());
        lineUtil.sendLandlordBotNotification(landlord.getLineId(), List.of(new Message(MessageType.TEXT, landlord_msg)));
    }

    @Transactional
    @Override
    public void sendFirmEmployeeRegisterVerification(SendFirmEmployeeRegisterVerificationRequest request) throws FirmEmployeeNotFoundException, LineNotificationException {
        String firmId = request.getId();
        FirmEmployees firmEmployees = firmEmployeesRepository.findByIdAndVerifiedIsNotNull(firmId).orElseThrow(FirmEmployeeNotFoundException::new);
        if (!firmEmployees.getVerified()) {
            firmEmployeesRepository.deleteById(firmId);
            referralNumberRepository.deleteByIdentityCard(firmEmployees.getIdentityCard());
        }
        String firm_employee_msg = "廠商人員 " + firmEmployees.getChineseName() + "，您好!\n" + prepareRegisterVerificationText(firmEmployees.getVerified());
        lineUtil.sendFirmEmployeeBotNotification(firmEmployees.getLineId(), List.of(new Message(MessageType.TEXT, firm_employee_msg)));
    }

    @Transactional
    @Override
    public void sendVolunteerRegisterVerification(SendVolunteerRegisterVerificationRequest request) throws VolunteerNotFoundException, LineNotificationException {
        String volunteerId = request.getId();
        Volunteer volunteer = volunteerRepository.findByIdAndVerifiedIsNotNull(volunteerId).orElseThrow(VolunteerNotFoundException::new);
        if (!volunteer.getVerified()) {
            volunteerRepository.deleteById(volunteerId);
            referralNumberRepository.deleteByIdentityCard(volunteer.getIdentityCard());
        }
        String volunteer_msg = "志工 " + volunteer.getChineseName() + "，您好!\n" + prepareRegisterVerificationText(volunteer.getVerified());
        lineUtil.sendVolunteerBotNotification(volunteer.getLineId(), List.of(new Message(MessageType.TEXT, volunteer_msg)));
    }

    @Override
    public void sendUserFirstStageCheckinApplicationVerification(SendUserFirstStageCheckinApplicationVerificationRequest request) throws LineNotificationException, CheckinApplicationNotFoundException, UserNotFoundException {
        CheckinApplication checkinApplication = checkinApplicationRepository.findByIdAndFirstVerifiedIsNotNull(request.getId()).orElseThrow(CheckinApplicationNotFoundException::new);
        Boolean result = checkinApplication.getFirstVerified();
        User user = checkinApplication.getUser();
        ReferralEmployee referralEmployee = checkinApplication.getReferralEmployee();
        String lineId = user.getLineId();

        String user_msg = new String();
        String referral_employee_msg = new String();

        if (result){
            user_msg = "您好，您已審核通過由轉介單位申請的第一階段入住程序!\n\n如欲繼續申請，請點擊下方「申請進度」選單，下載及上傳「入住契約書」及「切結書」。\n\n感謝您使用本服務!";
            referral_employee_msg = "您好，就醫民眾「" + user.getChineseName() + "」第一階段已審核通過，謝謝。\n\n提醒您~第二階段申請由就醫民眾提供文件上傳，系統已同步通知就醫民眾。\n\n感謝您使用本服務!";
        }else{
            user_msg = "您好，您的「入住申請」未審核通過!\n\n審核未通過原因:\n" + checkinApplication.getFirstVerifiedReason() + "\n\n感謝您使用本服務!";
            referral_employee_msg = "您好，就醫民眾「" + user.getChineseName() + "」未審核通過入住申 請的第一階段!\n\n審核未通過原因:\n" + checkinApplication.getFirstVerifiedReason() + "\n\n感謝您使用本服務!";
        }

        List<Message> messages = List.of(new Message(MessageType.TEXT, user_msg));
        lineUtil.sendUserBotNotification(lineId, messages);
        lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
    }

    @Override
    public void sendUserSecondStageCheckinApplicationVerification(SendUserSecondStageCheckinApplicationVerificationRequest request) throws LineNotificationException, CheckinApplicationNotFoundException {
        CheckinApplication checkinApplication = checkinApplicationRepository.findByIdAndSecondVerifiedIsNotNull(request.getId()).orElseThrow(CheckinApplicationNotFoundException::new);
        Boolean result = checkinApplication.getSecondVerified();
        User user = checkinApplication.getUser();
        ReferralEmployee referralEmployee = checkinApplication.getReferralEmployee();
        String lineId = user.getLineId();

        String user_msg = new String();
//        String referral_employee_msg = new String();
        int roomNumber = checkinApplication.getRoom().getNumber();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        String roomNumberStr = "房間編號: " + roomNumber + "\n";
        String startDateStr = "入住日期: " + sdf.format(checkinApplication.getRoomState().getStartDate()) + "\n";
        String endDateStr = "退房日期: " + sdf.format(checkinApplication.getRoomState().getEndDate()) + "\n\n";

        if (result){
            user_msg = "您好，您已審核通過入住「" + checkinApplication.getHouse().getName() + "」!\n入住前，我們將會以電話聯絡帶您入住。\n\n提醒您~請於預定時間前往入住!\n" + roomNumberStr + startDateStr + endDateStr + "感謝您使用本服務!";
//            referral_employee_msg = "您好，提醒您~就醫民眾「" + user.getChineseName() + "」已完成及成功入住申請「" + checkinApplication.getHouse().getName() + "」!謝謝。\n\n入住申請資訊\n" + roomNumberStr + startDateStr + endDateStr + "感謝您使用本服務!";
        }else{
            user_msg = "您好，第二階段入住程序未通過! 請再試一次";
//            referral_employee_msg = "您好，提醒您~就醫民眾 " + user.getChineseName() + " 第二階段入住程序未通過! 請再試一次";
        }

        List<Message> messages = List.of(new Message(MessageType.TEXT, user_msg));
        lineUtil.sendUserBotNotification(lineId, messages);
//        lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
    }

    @Override
    public void sendReferralEmployeeSecondStageCheckinApplicationVerification(SendReferralEmployeeSecondStageCheckinApplicationVerificationRequest request) throws CheckinApplicationNotFoundException, LineNotificationException {
        CheckinApplication checkinApplication = checkinApplicationRepository.findByIdAndSecondVerifiedIsNotNull(request.getId()).orElseThrow(CheckinApplicationNotFoundException::new);
        ReferralEmployee referralEmployee = checkinApplication.getReferralEmployee();
        User user = checkinApplication.getUser();
        String lineId = referralEmployee.getLineId();

        String referral_employee_msg = new String();

        int roomNumber = checkinApplication.getRoom().getNumber();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        String roomNumberStr = "房間編號: " + roomNumber + "\n";
        String startDateStr = "入住日期: " + sdf.format(checkinApplication.getRoomState().getStartDate()) + "\n";
        String endDateStr = "退房日期: " + sdf.format(checkinApplication.getRoomState().getEndDate()) + "\n\n";

        if (checkinApplication.getSecondVerified()){
            referral_employee_msg = "您好，提醒您~就醫民眾「" + user.getChineseName() + "」已完成及成功入住申請「" + checkinApplication.getHouse().getName() + "」!謝謝。\n\n入住申請資訊\n" + roomNumberStr + startDateStr + endDateStr + "感謝您使用本服務!";
        }else{
            referral_employee_msg = "您好，提醒您~就醫民眾「" + user.getChineseName() + "」第二階段入住程序未通過! 請再試一次";
        }

        List<Message> messages = List.of(new Message(MessageType.TEXT, referral_employee_msg));
        lineUtil.sendReferralEmployeeBotNotification(lineId, messages);
    }

    private String prepareRegisterVerificationText(boolean isVerified) {
        return isVerified ? successRegisterVerificationMessage : failRegisterVerificationMessage;
    }

//    private String prepareFirstStageCheckinVerificationText(boolean isVerified, String reason) {
//        return isVerified ? successUserFirstStageCheckinApplicationVerificationMessage : failUserFirstStageCheckinApplicationVerificationMessage + reason;
//    }

//    private String prepareSecondStageCheckinVerificationText(boolean isVerified) {
//        return isVerified ? successUserSecondStageCheckinApplicationVerificationMessage : failUserSecondStageCheckinApplicationVerificationMessage;
//    }

    @Transactional
    @Override
    public NotificationDateResponse sendCheckinFinish(String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException, RoomStateNotFoundException {

        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinAppId).orElseThrow(CheckinApplicationNotFoundException::new);
        User user = checkinApplication.getUser();
        ReferralEmployee referralEmployee = checkinApplication.getReferralEmployee();
        Landlord landlord = checkinApplication.getHouse().getLandlord();
        Volunteer volunteer = checkinApplication.getVolunteer();
        FirmEmployees firmEmployees = checkinApplication.getFirmEmployees();

        //set notification date
        checkinApplication.setCheckinNotificationDate(new Date());
        checkinApplicationRepository.save(checkinApplication);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = sdf.format(checkinApplication.getRoomState().getStartDate());
        String endDateStr = sdf.format(checkinApplication.getRoomState().getEndDate());
        String checkinInfo = "\n入住棧點 : " + checkinApplication.getHouse().getName() + "\n入住日期 : " + startDateStr + "\n退房日期 : " + endDateStr + "\n";

        String referral_employee_msg = commonMessage + user.getChineseName() + "(轉介編號：" + user.getReferralNumber().getId() + ")" + checkinFinishMessage;
        String landlord_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId() + ")" + checkinFinishMessage + checkinInfo;
        String volunteer_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId().substring(0, 5) + "...)" + checkinFinishMessage + checkinInfo;
        String firm_employee_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId().substring(0, 5) + "...)" + checkinFinishMessage + checkinInfo;

//        lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
//        lineUtil.sendLandlordBotNotification(landlord.getLineId(), List.of(new Message(MessageType.TEXT, landlord_msg)));
//        lineUtil.sendVolunteerBotNotification(volunteer.getLineId(), List.of(new Message(MessageType.TEXT, volunteer_msg)));
//        lineUtil.sendFirmEmployeeBotNotification(firmEmployees.getLineId(), List.of(new Message(MessageType.TEXT, firm_employee_msg)));

        try {
            lineUtil.sendVolunteerBotNotification(volunteer.getLineId(), List.of(new Message(MessageType.TEXT, volunteer_msg)));
        } catch (Exception e) {
            System.out.println(new VolunteerNotFoundException().getMessage());
        }

        try {
            lineUtil.sendFirmEmployeeBotNotification(firmEmployees.getLineId(), List.of(new Message(MessageType.TEXT, firm_employee_msg)));
        } catch (Exception e) {
            System.out.println(new FirmEmployeeNotFoundException().getMessage());
        }

        try {
            lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
        } catch (Exception e) {
            System.out.println(new ReferralEmployeeNotFoundException().getMessage());
        }

        try {
            lineUtil.sendLandlordBotNotification(landlord.getLineId(), List.of(new Message(MessageType.TEXT, landlord_msg)));
        } catch (Exception e) {
            System.out.println(new LandlordNotFoundException().getMessage());
        }

        return new NotificationDateResponse(checkinApplication.getCheckinNotificationDate());

    }

    @Transactional
    @Override
    public NotificationDateResponse sendCheckoutFinish(String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException, RoomStateNotFoundException {

        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinAppId).orElseThrow(CheckinApplicationNotFoundException::new);
        User user = checkinApplication.getUser();
        ReferralEmployee referralEmployee = checkinApplication.getReferralEmployee();
        Landlord landlord = checkinApplication.getHouse().getLandlord();
        Volunteer volunteer = checkinApplication.getVolunteer();
        FirmEmployees firmEmployees = checkinApplication.getFirmEmployees();

        //set notification date
        checkinApplication.setCheckoutNotificationDate(new Date());
        checkinApplicationRepository.save(checkinApplication);

        String referral_employee_msg = commonMessage + user.getChineseName() + "(轉介編號：" + user.getReferralNumber().getId() + ")" + checkoutFinishMessage;
        String landlord_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId().substring(0, 5) + "...)" + checkoutFinishMessage;
        String volunteer_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId().substring(0, 5) + "...)" + checkoutFinishMessage;
        String firm_employee_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId().substring(0, 5) + "...)" + checkoutFinishMessage;

//        lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
//        lineUtil.sendLandlordBotNotification(landlord.getLineId(), List.of(new Message(MessageType.TEXT, landlord_msg)));
//        lineUtil.sendVolunteerBotNotification(volunteer.getLineId(), List.of(new Message(MessageType.TEXT, volunteer_msg)));
//        lineUtil.sendFirmEmployeeBotNotification(firmEmployees.getLineId(), List.of(new Message(MessageType.TEXT, firm_employee_msg)));

        try {
            lineUtil.sendVolunteerBotNotification(volunteer.getLineId(), List.of(new Message(MessageType.TEXT, volunteer_msg)));
        } catch (Exception e) {
            System.out.println(new VolunteerNotFoundException().getMessage());
        }

        try {
            lineUtil.sendFirmEmployeeBotNotification(firmEmployees.getLineId(), List.of(new Message(MessageType.TEXT, firm_employee_msg)));
        } catch (Exception e) {
            System.out.println(new FirmEmployeeNotFoundException().getMessage());
        }

        try {
            lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
        } catch (Exception e) {
            System.out.println(new ReferralEmployeeNotFoundException().getMessage());
        }

        try {
            lineUtil.sendLandlordBotNotification(landlord.getLineId(), List.of(new Message(MessageType.TEXT, landlord_msg)));
        } catch (Exception e) {
            System.out.println(new LandlordNotFoundException().getMessage());
        }

        return new NotificationDateResponse(checkinApplication.getCheckoutNotificationDate());

    }

    @Transactional
    @Override
    public void sendRoomStateChangeVerified(String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException, RoomStateNotFoundException, RoomStateChangeNotFoundException {
        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinAppId).orElseThrow(CheckinApplicationNotFoundException::new);
        RoomStateChange roomStateChange = roomStateChangeRepository.findByRoomState(checkinApplication.getRoomState()).orElseThrow(RoomStateChangeNotFoundException::new);
        User user = checkinApplication.getUser();
        ReferralEmployee referralEmployee = checkinApplication.getReferralEmployee();
        Landlord landlord = checkinApplication.getHouse().getLandlord();
        Volunteer volunteer = checkinApplication.getVolunteer();
        FirmEmployees firmEmployees = checkinApplication.getFirmEmployees();

        String roomStateChangeInfo = new String();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newStartDateStr = sdf.format(roomStateChange.getNewStartDate());
        String newEndDateStr = sdf.format(roomStateChange.getNewEndDate());

        if (roomStateChange.getChangedItem().equals("取消入住")){
            roomStateChangeInfo = roomStateChange.getChangedItem();
        }else{
            roomStateChangeInfo = "\n變更項目 : " + roomStateChange.getChangedItem() + "\n變更後入住時間 : " + newStartDateStr + "\n變更後退房時間 : " + newEndDateStr;
        }

        String user_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId() + ")" + roomStateChangeVerified + roomStateChangeInfo;
        String referral_employee_msg = commonMessage + user.getChineseName() + "(轉介編號：" + user.getReferralNumber().getId() + ")" + roomStateChangeVerified + roomStateChangeInfo;
        String landlord_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId() + ")" + roomStateChangeVerified + roomStateChangeInfo;
        String volunteer_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId() + ")" + roomStateChangeVerified + roomStateChangeInfo;
        String firm_employee_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId() + ")" + roomStateChangeVerified + roomStateChangeInfo;

//        lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, user_msg)));
//        lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
//        lineUtil.sendLandlordBotNotification(landlord.getLineId(), List.of(new Message(MessageType.TEXT, landlord_msg)));
//        lineUtil.sendVolunteerBotNotification(volunteer.getLineId(), List.of(new Message(MessageType.TEXT, volunteer_msg)));
//        lineUtil.sendFirmEmployeeBotNotification(firmEmployees.getLineId(), List.of(new Message(MessageType.TEXT, firm_employee_msg)));

        try {
            lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, user_msg)));
        } catch (Exception e) {
            System.out.println(new UserNotFoundException().getMessage());
        }

        try {
            lineUtil.sendVolunteerBotNotification(volunteer.getLineId(), List.of(new Message(MessageType.TEXT, volunteer_msg)));
        } catch (Exception e) {
            System.out.println(new VolunteerNotFoundException().getMessage());
        }

        try {
            lineUtil.sendFirmEmployeeBotNotification(firmEmployees.getLineId(), List.of(new Message(MessageType.TEXT, firm_employee_msg)));
        } catch (Exception e) {
            System.out.println(new FirmEmployeeNotFoundException().getMessage());
        }

        try {
            lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));
        } catch (Exception e) {
            System.out.println(new ReferralEmployeeNotFoundException().getMessage());
        }

        try {
            lineUtil.sendLandlordBotNotification(landlord.getLineId(), List.of(new Message(MessageType.TEXT, landlord_msg)));
        } catch (Exception e) {
            System.out.println(new LandlordNotFoundException().getMessage());
        }
        
    }

    @Transactional
    @Override
    public void sendRoomStateChangeVerifyDenied(String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException, RoomStateNotFoundException, RoomStateChangeNotFoundException {
        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinAppId).orElseThrow(CheckinApplicationNotFoundException::new);
        User user = checkinApplication.getUser();
        ReferralEmployee referralEmployee = checkinApplication.getReferralEmployee();

        String user_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId() + ")" + roomStateChangeVerifyDenied;
        String referral_employee_msg = commonMessage + user.getChineseName() + "(轉介編號：" + user.getReferralNumber().getId() + ")" + roomStateChangeVerifyDenied;

        lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, user_msg)));
        lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, referral_employee_msg)));

    }


    @SneakyThrows
    @Transactional
    @Override
    public void sendNotificationForget(Map<String, String> userInfo) throws LineNotificationException, UserNotFoundException, ReferralEmployeeNotFoundException {
        String userId = userInfo.get("userId");
        String role = userInfo.get("role");

        String message_start = "先生/小姐，您好：\n\n";
        String message_content = "愛心棧系統已經接收到您「變更密碼」的請求。\n" +
            "驗證訊息已經成功寄送到您註冊的信箱，請按照內容完成後續變更的程序。\n\n";
        String message_end = "若您未提出此項申請請忽略此訊息，愛心棧系統祝您平安。";


        if (role.equals("user")) {
            User user = userRepository.findByIdAndVerified(userId, true).orElseThrow(UserNotFoundException::new);

            String text = user.getChineseName() + message_start + message_content + message_end;
            lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, text)));
        } else if (role.equals("referral_employee")) {
            ReferralEmployee referralEmployee = referralEmployeeRepository.findByIdAndVerified(userId, true).orElseThrow(ReferralEmployeeNotFoundException::new);

            String text = referralEmployee.getName() + message_start + message_content + message_end;
            lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, text)));
        } else if (role.equals("landlord")) {
            Landlord landlord = landlordRepository.findByIdAndVerified(userId, true).orElseThrow(LandlordNotFoundException::new);

            String text = landlord.getChineseName() + message_start + message_content + message_end;
            lineUtil.sendLandlordBotNotification(landlord.getLineId(), List.of(new Message(MessageType.TEXT, text)));
        } else if (role.equals("volunteer")) {
            Volunteer volunteer = volunteerRepository.findByIdAndVerified(userId, true).orElseThrow(VolunteerNotFoundException::new);

            String text = volunteer.getChineseName() + message_start + message_content + message_end;
            lineUtil.sendVolunteerBotNotification(volunteer.getLineId(), List.of(new Message(MessageType.TEXT, text)));
        } else if (role.equals("firm_employee")) {
            FirmEmployees firmEmployees = firmEmployeesRepository.findByIdAndVerified(userId, true).orElseThrow(FirmEmployeeNotFoundException::new);


            String text = firmEmployees.getChineseName() + message_start + message_content + message_end;
            lineUtil.sendFirmEmployeeBotNotification(firmEmployees.getLineId(), List.of(new Message(MessageType.TEXT, text)));
        } else if (role.equals("admin")) {
            // admin don't have the line bot
        }

    }

    @Transactional
    @Override
    public void sendNotificationRepassword(Map<String, String> userInfo) throws LineNotificationException, UserNotFoundException, ReferralEmployeeNotFoundException {
        String userId = userInfo.get("userId");
        String role = userInfo.get("role");

        String message_start = "先生/小姐，您好：\n\n";
        String message_content = "愛心棧系統已經完成您「變更密碼」的程序。\n" +
            "後續使用請以變更後的新密碼登入。\n\n";
        String message_end = "若您未提出此項申請請忽略此訊息，愛心棧系統祝您平安。";


        if (role.equals("user")) {
            User user = userRepository.findByIdAndVerified(userId, true).orElseThrow(UserNotFoundException::new);

            String text = user.getChineseName() + message_start + message_content + message_end;
            lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, text)));
        } else if (role.equals("referral_employee")) {
            ReferralEmployee referralEmployee = referralEmployeeRepository.findByIdAndVerified(userId, true).orElseThrow(ReferralEmployeeNotFoundException::new);

            String text = referralEmployee.getName() + message_start + message_content + message_end;
            lineUtil.sendReferralEmployeeBotNotification(referralEmployee.getLineId(), List.of(new Message(MessageType.TEXT, text)));
        } else if (role.equals("landlord")) {
            // None done
        } else if (role.equals("volunteer")) {
            // None done
        } else if (role.equals("firm")) {
            // None done
        } else if (role.equals("admin")) {
            // admin don't have the line bot
        }

    }

    @Transactional
    @Override
    public void sendCheckoutRemind(String checkinAppId) throws LineNotificationException, CheckinApplicationNotFoundException{
        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinAppId).orElseThrow(CheckinApplicationNotFoundException::new);
        User user = checkinApplication.getUser();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        String endDateStr = sdf.format(checkinApplication.getRoomState().getEndDate());

        String user_msg = commonMessage + user.getChineseName() + "(申請編號：" + checkinApplication.getId() + ")\n\n本次入住需於三天後(" + endDateStr + ")辦理退房，謝謝。";

        lineUtil.sendUserBotNotification(user.getLineId(), List.of(new Message(MessageType.TEXT, user_msg)));

    }
}
