package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.ReferralEmployeeCheckinApplicationSearchResult;

import java.util.List;

@Data
@AllArgsConstructor
public class ReferralEmployeeCheckinApplicationSearchResultPagedResponse {

    private List<ReferralEmployeeCheckinApplicationSearchResult> checkinApplications;
    private int currentPage;
    private int totalPage;
}
