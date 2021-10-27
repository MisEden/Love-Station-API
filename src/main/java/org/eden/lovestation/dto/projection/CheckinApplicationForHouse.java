package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface CheckinApplicationForHouse {

    @Value("#{target.id}")
    String getCheckinAppId();

    @Value("#{target.user.chineseName}")
    String getUserName();

    @Value("#{target.house.name}")
    String getHouseName();

    @Value("#{target.room.id}")
    String getRoomId();

    @Value("#{target.room.number}")
    String getRoomNumber();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    @Value("#{target.roomState.startDate}")
    Date getStartDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    @Value("#{target.roomState.endDate}")
    Date getEndDate();

}
