package org.eden.lovestation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class CheckinActualTimeResponse {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Taipei")
    Date checkinActualTime;

    Boolean checkinAlready;

    public CheckinActualTimeResponse(Date checkinActualTime, Boolean checkinAlready){
        this.checkinActualTime = checkinActualTime;
        this.checkinAlready = checkinAlready;
    }
}
