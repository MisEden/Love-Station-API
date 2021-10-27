package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface FirmEmployeeServiceRecordDetailByAdmin {

    String getId();

    @Value("#{target.checkin_application_id}")
    String getCheckinAppId();

    @Value("#{target.firmName}")
    String getFirmName();

    @Value("#{target.firmEmployeeName}")
    String getFirmEmployeeName();

    @Value("#{target.houseName}")
    String getHouse();

    @Value("#{target.roomNumber}")
    String getRoomNumber();

    String getService();

    String getRemark();

    @Value("#{target.before_image}")
    String getBeforeImage();

    @Value("#{target.after_image}")
    String getAfterImage();

    String getViewed();

    @Value("#{target.created_at}")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getCreatedAt();

}
