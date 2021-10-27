package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.AdminCheckinApplicationSearchResult;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminCheckinApplicationSearchResultPagedResponse {
    private List<AdminCheckinApplicationSearchResult> checkinApplications;
    private int currentPage;
    private int totalPage;
}
