package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdatePasswordReset {
    @Size(min = 8, max = 15)
    private String password;
    @Size(min = 8, max = 15)
    private String confirmPassword;
}
