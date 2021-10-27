package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface CheckinApplicationWithRoomStateChangeDetail {

    @Value("#{target.id}")
    String getCheckinAppId();

    String getHouseName();

    String getRoomNumber();

    @Value("#{target.referral_date}")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getReferralDate();

    String getReferralName();

    String getReferralEmployeeName();

    String getReferralEmployeeCellphone();

    String getReferralEmployeeTitle();

    String getUserName();

    String getUserGender();

    String getUserBirthday();

    String getUserIdentityCard();

    String getUserCellphone();

    String getUserAddress();

    @Value("#{target.applicant_in}")
    String getApplicantIn();

    @Value("#{target.blood_type}")
    String getBloodType();

    String getLanguage();

    @Value("#{target.user_identity}")
    String getUserIdentity();

    @Value("#{target.special_medical_history}")
    String getSpecialMedicalHistory();

    @Value("#{target.drug_allergy}")
    String getDrugAllergy();

    @Value("#{target.diagnosed_with}")
    String getDiagnosedWith();

    @Value("#{target.overview_patient_condition}")
    String getOverviewPatientCondition();

    String getMedicine();

    @Value("#{target.self_care_ability}")
    String getSelfCareAbility();

    String getAttachment();

    @Value("#{target.caregiver_name}")
    String getCaregiverName();

    @Value("#{target.caregiver_relationship}")
    String getCaregiverRelationship();

    @Value("#{target.caregiver_phone}")
    String getCaregiverPhone();

    @Value("#{target.applicant_infectious_disease}")
    String getApplicantInfectiousDisease();

    @Value("#{target.caregiver_infectious_disease}")
    String getCaregiverInfectiousDisease();

    @Value("#{target.one_emergency_contact_name}")
    String getOneEmergencyContactName();

    @Value("#{target.one_emergency_contact_phone}")
    String getOneEmergencyContactPhone();

    @Value("#{target.one_emergency_contact_relationship}")
    String getOneEmergencyContactRelationship();

    @Value("#{target.two_emergency_contact_name}")
    String getTwoEmergencyContactName();

    @Value("#{target.two_emergency_contact_phone}")
    String getTwoEmergencyContactPhone();

    @Value("#{target.two_emergency_contact_relationship}")
    String getTwoEmergencyContactRelationship();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getStartDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getEndDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getNewStartDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getNewEndDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getRoomStateChangeDate();

    String getChangedItem();

    String getReason();

}
