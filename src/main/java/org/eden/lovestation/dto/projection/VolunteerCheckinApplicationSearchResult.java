package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface VolunteerCheckinApplicationSearchResult {

    String getCheckinAppId();

    String getHouseName();

    String getRoomNumber();

    String getUserName();

    String getCaregiverName();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getStartDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getEndDate();

}
