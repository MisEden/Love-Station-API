package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface CheckinApplicationBeforeCheckoutBrief {

    String getCheckinAppId();

    String getHouseId();

    String getHouse();

    String getRoomNumber();

    String getRoomStateId();

    String getChineseName();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getStartDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getEndDate();

}
