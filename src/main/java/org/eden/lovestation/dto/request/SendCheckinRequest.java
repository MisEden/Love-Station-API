package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class SendCheckinRequest {
    @NotEmpty
    @Pattern(regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", message = "must fit uuid format")
    private String referralEmployeeId;

    @NotEmpty
    @Pattern(regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", message = "must fit uuid format")
    private String roomStateId;
}
