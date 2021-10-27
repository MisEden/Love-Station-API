package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterAdministratorByAdministratorRequest {
    private String id;

    @Size(min = 5, max = 15)
    @Pattern(regexp = "^\\d{5,15}$", message = "帳號需為5~15位的數字組合")
    private String account;

    @Size(min = 1, max = 200)
    private String name;

    @NotEmpty
    @Email
    private String email;

    @Size(min = 1, max = 200)
    private String roleName;

}
