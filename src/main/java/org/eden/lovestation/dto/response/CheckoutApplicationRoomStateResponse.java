package org.eden.lovestation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
//@AllArgsConstructor
public class CheckoutApplicationRoomStateResponse {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    private Date endDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    private Date nowDate;

    private int daysOfStay;

    public CheckoutApplicationRoomStateResponse(Date startDate, Date endDate, Date nowDate, int diff){
        this.startDate = startDate;
        this.endDate = endDate;
        this.nowDate = nowDate;
        this.daysOfStay = diff;
    }

}
