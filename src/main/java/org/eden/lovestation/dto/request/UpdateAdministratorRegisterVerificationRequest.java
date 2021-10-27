package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateAdministratorRegisterVerificationRequest {
    @NotNull
    private Boolean verified;

    private String changeTo;

    private String reason;
}
