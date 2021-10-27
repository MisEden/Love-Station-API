package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface UserDetail {
    String getId();

    @Value("#{target.referralNumber.id}")
    String getReferralNumber();

    @Value("#{target.role.name}")
    String getRole();

    String getLineId();

    String getAccount();

    String getChineseName();

    String getEnglishName();

    String getEmail();

    String getBirthday();

    String getIdentityCard();

    String getGender();

    String getAddress();

    String getPhone();

    String getCellphone();

    Boolean getVerified();

    Boolean getAgreePersonalInformation();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getCreatedAt();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getUpdatedAt();
}
