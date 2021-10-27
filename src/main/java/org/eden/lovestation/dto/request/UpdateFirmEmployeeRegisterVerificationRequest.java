package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateFirmEmployeeRegisterVerificationRequest {
    @NotNull
    private Boolean verified;
}
