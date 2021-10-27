package org.eden.lovestation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class LandlordServiceRecordResponse {

    private String landlord;

    private String house;

    private int roomNumber;

    private String service;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    private Date createdAt;

}
