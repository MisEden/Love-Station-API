package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateReferralEmployeeRegisterVerificationRequest {
    @NotNull
    private Boolean verified;
}
