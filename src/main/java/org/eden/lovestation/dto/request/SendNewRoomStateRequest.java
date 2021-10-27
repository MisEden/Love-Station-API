package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SendNewRoomStateRequest {

    @NotEmpty
    private String newStartDate;

    @NotEmpty
    private String newEndDate;

    @NotEmpty
    private String changedItem;

    private String reason;

}
