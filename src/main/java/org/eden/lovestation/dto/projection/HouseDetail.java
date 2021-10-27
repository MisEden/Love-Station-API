package org.eden.lovestation.dto.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface HouseDetail {

    String getId();

    int getSerial();

    String getCity();

    String getName();

    String getIntroduction();

    @Value("#{target.houseImages.![id]}")
    List<String> getImagesId();

    @Value("#{target.houseImages.![image]}")
    List<String> getImages();

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

    boolean getDisable();
}
