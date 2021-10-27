package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.LandlordCheckinApplicationSearchResult;

import java.util.List;

@Data
@AllArgsConstructor
public class LandlordCheckinApplicationSearchResultPagedResponse {

    private List<LandlordCheckinApplicationSearchResult> checkinApplications;
    private int currentPage;
    private int totalPage;

}
