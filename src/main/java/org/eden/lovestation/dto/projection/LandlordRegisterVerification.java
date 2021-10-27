package org.eden.lovestation.dto.projection;

public interface LandlordRegisterVerification {
    String getId();

    String getChineseName();

    String getEnglishName();

    String getBirthday();

    String getIdentityCard();

    String getEmail();

    String getGender();

    String getAddress();

    String getPhone();

    String getCellphone();

    Boolean getVerified();

    Boolean getAgreePersonalInformation();
}
