package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.ReferralEmployeeCheckinApplicationSearchResult;
import org.eden.lovestation.dto.projection.VolunteerCheckinApplicationSearchResult;

import java.util.List;

@Data
@AllArgsConstructor
public class VolunteerCheckinApplicationSearchResultPagedResponse {

    private List<VolunteerCheckinApplicationSearchResult> checkinApplications;
    private int currentPage;
    private int totalPage;

}
