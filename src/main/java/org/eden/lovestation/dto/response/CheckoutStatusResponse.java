package org.eden.lovestation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CheckoutStatusResponse {

    private String checkinAppId;

    private String roomStateId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    private Date endDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Taipei")
    private Date checkinDateTime;

    private int daysOfStay;

    private Boolean overdue;
}
