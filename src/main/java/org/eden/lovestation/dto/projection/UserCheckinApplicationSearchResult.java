package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface UserCheckinApplicationSearchResult {

    String getId();

    String getHouseName();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getStartDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getEndDate();

    String getRentImage();

    Boolean getFirstVerified();

    Boolean getSecondVerified();
}
