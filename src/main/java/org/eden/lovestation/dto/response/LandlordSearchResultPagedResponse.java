package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.LandlordDetail;
import org.eden.lovestation.dto.projection.ReferralSearchResult;

import java.util.List;

@Data
@AllArgsConstructor
public class LandlordSearchResultPagedResponse {
    private List<LandlordDetail> landlord;
    private int currentPage;
    private int totalPage;
}
