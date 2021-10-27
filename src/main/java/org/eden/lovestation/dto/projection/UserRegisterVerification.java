package org.eden.lovestation.dto.projection;

import org.eden.lovestation.dao.model.ReferralNumber;

public interface UserRegisterVerification {
    String getId();

    ReferralNumber getReferralNumber();

    String getChineseName();

    String getEnglishName();

    String getEmail();

    String getBirthday();

    String getBloodType();

    String getIdentityCard();

    String getGender();

    String getAddress();

    String getPhone();

    String getCellphone();

    Boolean getVerified();

    Boolean getAgreePersonalInformation();
}
