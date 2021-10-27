package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class GenerateReferralNumberRequest {
    @Pattern(regexp = "^[A-Z]\\d{9}$", message = "身分證要符合台灣的規定")
    private String identityCard;
}
