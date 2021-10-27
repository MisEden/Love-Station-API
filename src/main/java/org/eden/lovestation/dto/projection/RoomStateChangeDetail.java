package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface RoomStateChangeDetail {

    String getId();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getNewStartDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getNewEndDate();

    String getReason();
}
