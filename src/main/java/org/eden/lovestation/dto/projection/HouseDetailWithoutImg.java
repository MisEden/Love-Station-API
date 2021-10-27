package org.eden.lovestation.dto.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface HouseDetailWithoutImg {

    String getId();

    int getSerial();

    String getName();

    String getIntroduction();

    String getSquareFootage();

    String getRoomLayout();

    String getTotalFloor();

    String getStyle();

    String getFeature();

    String getRoomDescription();

    @Value("#{target.landlord.id}")
    String getLandlordId();

    @Value("#{target.landlord.chineseName}")
    String getLandlordName();

    String getTraffic();

    String getAddress();

    String getNearHospital();

    String getLifeFunction();

    String getPlanimetricMap();

    String getFullDegreePanorama();
}
