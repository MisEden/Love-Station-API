package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.UserRegisterVerification;

import java.util.List;

@Data
@AllArgsConstructor
public class UserRegisterVerificationPagedResponse {
    private List<UserRegisterVerification> users;
    private int currentPage;
    private int totalPage;
}
