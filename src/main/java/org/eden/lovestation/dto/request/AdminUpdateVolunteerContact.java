package org.eden.lovestation.dto.request;

import lombok.Data;
import org.eden.lovestation.util.validator.CustomDateConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AdminUpdateVolunteerContact {
    @NotEmpty
    @Email
    private String email;

    @Size(min = 1, max = 200)
    private String address;

    @Pattern(regexp = "^(0\\d+)-(\\d{7,8})(?:(?:#)(\\d+))?$", message = "聯絡電話應該符合台灣的規定")
    private String phone;

    @Pattern(regexp = "^(09)\\d{2}-\\d{3}-\\d{3}$", message = "手機號碼應該符合台灣的規定")
    private String cellphone;
}
