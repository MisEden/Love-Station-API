package org.eden.lovestation.dto.projection;

import org.springframework.beans.factory.annotation.Value;

public interface FirmEmployeeRegisterVerification {

    String getId();

    String getFirmName();

    @Value("#{target.chinese_name}")
    String getChineseName();

    @Value("#{target.english_name}")
    String getEnglishName();

    String getBirthday();

    @Value("#{target.identity_card}")
    String getIdentityCard();

    String getEmail();

    String getGender();

    String getAddress();

    String getPhone();

    String getCellphone();

    Boolean getVerified();

    @Value("#{target.agree_personal_information}")
    Boolean getAgreePersonalInformation();
}
