package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class SendServiceRecordRequest {

    @NotEmpty
    @Pattern(regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", message = "checkinAppId must fit uuid format")
    private String checkinAppId;

    @NotEmpty
    @Pattern(regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", message = "houseId must fit uuid format")
    private String houseId;

    @NotEmpty
    private int roomNumber;

    private String service;

    private String remark;

}
