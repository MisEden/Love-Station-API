package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface AdminCheckinApplicationSearchResult {

    String getId();

    String getUserId();

    String getUserIdentityCard();

    String getUserGender();

    String getUserCellphone();

    String getUserBirthday();

    String getHouseName();

    String getRoomNumber();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getStartDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getEndDate();

    String getUserName();

    String getCareGiverName();

    String getReferralHospitalName();

    String getReferralEmployeeName();

    String getRentImage();

    String getAffidavitImage();

    Boolean getFirstVerified();

    String getFirstVerifiedReason();

    Boolean getSecondVerified();

//    String getChangeItem();

    Boolean getDeleted();

//    boolean getAdminVerified();
}
