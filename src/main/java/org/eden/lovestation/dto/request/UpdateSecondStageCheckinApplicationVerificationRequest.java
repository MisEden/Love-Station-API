package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateSecondStageCheckinApplicationVerificationRequest {
    @NotNull
    private Boolean secondVerified;
}
