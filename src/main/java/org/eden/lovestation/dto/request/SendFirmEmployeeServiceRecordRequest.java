package org.eden.lovestation.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class SendFirmEmployeeServiceRecordRequest {

    @NotEmpty
    @Pattern(regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", message = "checkinAppId must fit uuid format")
    private String checkinAppId;

    @NotEmpty
    @Pattern(regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", message = "firmEmployeeId must fit uuid format")
    private String firmEmployeeId;

    private String houseName;

    private Integer roomNumber;

    private String service;

    private String remark;

    private MultipartFile beforeImage;

    private MultipartFile afterImage;
}
