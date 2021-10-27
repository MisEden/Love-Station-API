package org.eden.lovestation.dto.projection;

public interface VolunteerRegisterVerification {

    String getId();

    String getChineseName();

    String getEnglishName();

    String getIdentityCard();

    String getBirthday();

    String getEmail();

    String getGender();

    String getAddress();

    String getPhone();

    String getCellphone();

    Boolean getVerified();

    Boolean getAgreePersonalInformation();

}
