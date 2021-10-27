package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.AdminCheckinApplicationSearchResult;
import org.eden.lovestation.dto.projection.ReferralSearchResult;

import java.util.List;

@Data
@AllArgsConstructor
public class ReferralSearchResultPagedResponse {
    private List<ReferralSearchResult> referrals;
    private int currentPage;
    private int totalPage;
}
