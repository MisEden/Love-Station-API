package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateVolunteerRegisterVerificationRequest {
    @NotNull
    private Boolean verified;
}
