package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.ReferralEmployeeRegisterVerification;

import java.util.List;

@Data
@AllArgsConstructor
public class ReferralEmployeeRegisterVerificationPagedResponse {
    private List<ReferralEmployeeRegisterVerification> referralEmployees;
    private int currentPage;
    private int totalPage;
}
