package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailResponse {
    private String userId;
    private String birthday;
    private String userName;
    private String gender;
    private String address;
    private String cellphone;
    private String bloodType;
}
