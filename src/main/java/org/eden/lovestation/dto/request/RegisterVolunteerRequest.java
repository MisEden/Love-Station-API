package org.eden.lovestation.dto.request;

import lombok.Data;
import org.eden.lovestation.util.validator.CustomDateConstraint;

import javax.validation.constraints.*;

@Data
public class RegisterVolunteerRequest {
    private String id;
    @Size(min = 1, max = 200)
    private String lineId;
    @Size(min = 5, max = 15)
    @Pattern(regexp = "^([a-zA-Z]+\\d+)[a-zA-Z0-9]*$", message = "帳號第一位需為英文，且包含英文與數字")
    private String account;
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^([a-zA-Z]+\\d+|\\d+[a-zA-Z]+)[a-zA-Z0-9]*$", message = "密碼應該要包含英文")
    private String password;
    @Size(min = 1, max = 200)
    private String chineseName;
    @Size(min = 0, max = 200)
    private String englishName = "";
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @CustomDateConstraint
    private String birthday;
    @Pattern(regexp = "^[A-Z]\\d{9}$", message = "身分證須符合台灣的規定")
    private String identityCard;
    @Pattern(regexp = "^(男|女)$", message = "必須回傳男或女")
    private String gender;
    @Size(min = 1, max = 200)
    private String address;
    @Pattern(regexp = "^(0\\d+)-(\\d{7,8})(?:(?:#)(\\d+))?$", message = "聯絡電話應該符合台灣的規定")
    private String phone;
    @Pattern(regexp = "^(09)\\d{2}-\\d{3}-\\d{3}$", message = "手機號碼應該符合台灣的規定")
    private String cellphone;
    @AssertTrue
    private boolean agreePersonalInformation;
}
