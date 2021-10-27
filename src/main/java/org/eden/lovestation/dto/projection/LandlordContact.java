package org.eden.lovestation.dto.projection;

import org.springframework.beans.factory.annotation.Value;

public interface LandlordContact {
    String getId();

    @Value("#{target.chinese_name}")
    String getChineseName();

    @Value("#{target.english_name}")
    String getEnglishName();

    String getEmail();

    String getGender();

    String getAddress();

    String getPhone();

    String getCellphone();

    String getHousesId();

    String getHousesName();
}
