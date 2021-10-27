package org.eden.lovestation.dto.request;

import lombok.Data;
import org.eden.lovestation.exception.business.DateFormatParseException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Data
public class ApplyFirstStageCheckinApplicationRequest {
    @NotEmpty
    private String id;
    @NotEmpty
    @Pattern(regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", message = "must fit uuid format")
    private String userId;
    @NotEmpty
    private String referralDate;
    @NotEmpty
    private String startDate;
    @NotEmpty
    private String endDate;
    @NotEmpty
    private String roomId;
    private boolean applicantIn;
    @Size(min = 1, max = 200)
    private String bloodType;
    @NotEmpty
    private List<String> language;
    @Size(min = 1, max = 200)
    private String specialMedicalHistory;
    @Size(min = 1, max = 200)
    private String drugAllergy;
    @Size(min = 1, max = 200)
    private String diagnosedWith;
    @Size(min = 1, max = 200)
    private String overviewPatientCondition;
    @Size(min = 1, max = 200)
    private String medicine;
    @NotEmpty
    private List<String> userIdentity;
    @NotEmpty
    private String selfCareAbility;
    @NotEmpty
    private List<String> attachment;
    @Size(min = 1, max = 200)
    private String caregiverName;
    @Size(min = 1, max = 200)
    private String caregiverRelationship;
    @Size(min = 1, max = 200)
    private String caregiverPhone;
    @Size(min = 1, max = 200)
    private String applicantInfectiousDisease;
    @Size(min = 1, max = 200)
    private String caregiverInfectiousDisease;
    @Size(min = 1, max = 200)
    private String oneEmergencyContactName;
    @Size(min = 1, max = 200)
    private String oneEmergencyContactPhone;
    @Size(min = 1, max = 200)
    private String oneEmergencyContactRelationship;
    @Size(min = 1, max = 200)
    private String twoEmergencyContactName;
    @Size(min = 1, max = 200)
    private String twoEmergencyContactPhone;
    @Size(min = 1, max = 200)
    private String twoEmergencyContactRelationship;
    @NotEmpty
    private List<String> applicationReason;

    public Date getReferralDateConverted() throws DateFormatParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return simpleDateFormat.parse(this.referralDate);
        } catch (ParseException e) {
            throw new DateFormatParseException();
        }
    }

    public Date getStartDateConverted() throws DateFormatParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return simpleDateFormat.parse(this.startDate);
        } catch (ParseException e) {
            throw new DateFormatParseException();
        }
    }

    public Date getEndDateConverted() throws DateFormatParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return simpleDateFormat.parse(this.endDate);
        } catch (ParseException e) {
            throw new DateFormatParseException();
        }
    }

    public String getLanguageConverted() {
        return String.join(",", this.language);
    }

    public String getAttachmentConverted() {
        return String.join(",", this.attachment);
    }

    public String getUserIdentityConverted() {
        return String.join(",", this.userIdentity);
    }

    public String getApplicationReasonConverted() {
        return String.join(",", this.applicationReason);
    }
}
