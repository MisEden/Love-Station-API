package org.eden.lovestation.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Data
public class RegisterReferralEmployeeRequest {
    private String id;
    @NotEmpty
    @Pattern(regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", message = "must fit uuid format")
    private String referralId;
    @Size(min = 1, max = 200)
    private String name;
    @NotEmpty
    @Pattern(regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", message = "must fit uuid format")
    private String referralTitleId;
    @Size(min = 1, max = 200)
    private String workIdentity;
    @NotNull
    private MultipartFile image;
    @Size(min = 1, max = 200)
    private String lineId;
    @Size(min = 5, max = 15)
    @Pattern(regexp = "^[a-zA-Z]\\w*$", message = "帳號第一個字母必須為寫英文")
    private String account;
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9]{6,}", message = "密碼須為至少6字元的英文數字組合")
    private String password;
    @NotEmpty
    @Email
    private String email;
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
