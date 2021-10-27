package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface LandlordRecordDetail {

    @Value("#{target.id}")
    String getServiceRecordId();

    @Value("#{target.checkinApplication.id}")
    String getCheckinAppId();

    @Value("#{target.landlord.chineseName}")
    String getLandlordName();

    @Value("#{target.house.name}")
    String getHouse();

    @Value("#{target.room.number}")
    String getRoomNumber();

    String getService();

    String getRemark();

    String getViewed();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getCreatedAt();

}
