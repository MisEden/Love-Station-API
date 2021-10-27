package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface LandlordServiceRecordDetail {

    String getId();

    @Value("#{target.checkin_application_id}")
    String getCheckinAppId();

    @Value("#{target.landlordName}")
    String getLandlordName();

    @Value("#{target.houseName}")
    String getHouse();

    @Value("#{target.roomNumber}")
    String getRoomNumber();

    String getService();

    String getRemark();

    String getViewed();

    @Value("#{target.created_at}")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getCreatedAt();
}
