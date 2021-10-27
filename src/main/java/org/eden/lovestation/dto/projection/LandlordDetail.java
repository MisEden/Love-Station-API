package org.eden.lovestation.dto.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface LandlordDetail {
    String getId();

    @Value("#{target.line_id}")
    String getLineId();

    String getRole();

    String getAccount();

    @Value("#{target.chinese_name}")
    String getChineseName();

    @Value("#{target.english_name}")
    String getEnglishName();

    @Value("#{target.identity_card}")
    String getIdentityCard();

    String getEmail();

    String getGender();

    String getBirthday();

    String getAddress();

    String getPhone();

    String getCellphone();
}
