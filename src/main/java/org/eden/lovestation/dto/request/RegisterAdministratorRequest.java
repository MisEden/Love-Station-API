package org.eden.lovestation.dto.request;

import lombok.Data;
import org.eden.lovestation.util.validator.CustomDateConstraint;

import javax.validation.constraints.*;

@Data
public class RegisterAdministratorRequest {
    private String id;

    private String lineId;

    @Size(min = 5, max = 15)
    @Pattern(regexp = "^\\d{5,15}$", message = "帳號需為5~15位的數字組合")
    private String account;

    @Size(min = 6, max = 15)
    @Pattern(regexp = "^([a-zA-Z]+\\d+|\\d+[a-zA-Z]+)[a-zA-Z0-9]*$", message = "密碼應該要包含英文與數字")
    private String password;

    @Size(min = 1, max = 200)
    private String name;

    @NotEmpty
    @Email
    private String email;

}
