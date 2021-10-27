package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface OccupiedRoomDate {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    @Value("#{target.startDate}")
    Date getStartDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    @Value("#{target.endDate}")
    Date getEndDate();

}
