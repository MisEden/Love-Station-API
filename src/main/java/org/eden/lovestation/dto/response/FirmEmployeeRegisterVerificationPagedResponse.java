package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.FirmEmployeeRegisterVerification;

import java.util.List;

@Data
@AllArgsConstructor
public class FirmEmployeeRegisterVerificationPagedResponse {
    private List<FirmEmployeeRegisterVerification> firmEmployees;
    private int currentPage;
    private int totalPage;
}
