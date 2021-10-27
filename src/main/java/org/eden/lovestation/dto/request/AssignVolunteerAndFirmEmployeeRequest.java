package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class AssignVolunteerAndFirmEmployeeRequest {

    @Pattern(regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", message = "must fit uuid format")
    private String checkinAppId;

    @Pattern(regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", message = "must fit uuid format")
    private String volunteerId;

    @Pattern(regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", message = "must fit uuid format")
    private String firmEmployeeId;

}
