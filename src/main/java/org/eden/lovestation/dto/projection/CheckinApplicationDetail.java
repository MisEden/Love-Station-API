package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface CheckinApplicationDetail {

    String getId();

    @Value("#{target.user.id}")
    String getUserId();

    @Value("#{target.user.birthday}")
    String getBirthday();

    @Value("#{target.user.identityCard}")
    String getIdentityCard();

    @Value("#{target.user.chineseName}")
    String getUserName();

    @Value("#{target.user.gender}")
    String getGender();

    @Value("#{target.user.address}")
    String getAddress();

    @Value("#{target.user.cellphone}")
    String getCellphone();

    @Value("#{target.referralEmployee.id}")
    String getReferralEmployeeId();

    @Value("#{target.referralEmployee.referral.hospitalChineseName}")
    String getReferralHospitalChineseName();

    @Value("#{target.referralEmployee.name}")
    String getReferralEmployeeName();

    @Value("#{target.referralEmployee.referralTitle.name}")
    String getReferralTitleName();

    @Value("#{target.referralEmployee.cellphone}")
    String getReferralEmployeeCellphone();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Taipei")
    Date getReferralDate();

    @Value("#{target.roomState.id}")
    String getRoomId();

    @Value("#{target.roomState.room.number}")
    String getRoomNumber();

    Boolean getApplicantIn();

    @Value("#{target.user.bloodType}")
    String getBloodType();

    String getLanguage();

    String getSpecialMedicalHistory();

    String getDrugAllergy();

    String getDiagnosedWith();

    String getOverviewPatientCondition();

    String getMedicine();

    String getUserIdentity();

    String getSelfCareAbility();

    String getAttachment();

    String getCaregiverName();

    String getCaregiverRelationship();

    String getCaregiverPhone();

    String getApplicantInfectiousDisease();

    String getCaregiverInfectiousDisease();

    String getOneEmergencyContactName();

    String getOneEmergencyContactPhone();

    String getOneEmergencyContactRelationship();

    String getTwoEmergencyContactName();

    String getTwoEmergencyContactPhone();

    String getTwoEmergencyContactRelationship();

    String getApplicationReason();

    Boolean getFirstVerified();

    String getFirstVerifiedReason();

    Boolean getSecondVerified();

    String getRentImage();

    String getAffidavitImage();

    @Value("#{target.roomState.room.house.name}")
    String getHouseName();

    @Value("#{target.volunteer != null ? target.volunteer.id : null}")
    String getVolunteerId();

    @Value("#{target.volunteer != null ? target.volunteer.chineseName : null}")
    String getVolunteerName();

    @Value("#{target.firmEmployees != null ? target.firmEmployees.id : null}")
    String getFirmEmployeeId();

    @Value("#{target.firmEmployees != null ? target.firmEmployees.chineseName : null}")
    String getFirmEmployeeName();

    @Value("#{target.roomState.startDate}")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getStartDate();

    @Value("#{target.roomState.endDate}")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getEndDate();
}
