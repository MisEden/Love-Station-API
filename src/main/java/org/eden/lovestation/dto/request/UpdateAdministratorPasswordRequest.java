package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateAdministratorPasswordRequest {
    @Size(min = 1, max = 15)
    private String oldPassword;

    @Size(min = 6, max = 15)
    @Pattern(regexp = "^([a-zA-Z]+\\d+|\\d+[a-zA-Z]+)[a-zA-Z0-9]*$", message = "密碼應該要包含英文與數字")
    private String password;

    @Size(min = 6, max = 15)
    private String confirmPassword;
}
