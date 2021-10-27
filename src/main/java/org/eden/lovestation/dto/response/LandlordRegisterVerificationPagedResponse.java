package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.LandlordRegisterVerification;

import java.util.List;

@Data
@AllArgsConstructor
public class LandlordRegisterVerificationPagedResponse {
    private List<LandlordRegisterVerification> landlords;
    private int currentPage;
    private int totalPage;
}
