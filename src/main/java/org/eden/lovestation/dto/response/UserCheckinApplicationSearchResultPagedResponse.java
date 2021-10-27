package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.UserCheckinApplicationSearchResult;

import java.util.List;

@Data
@AllArgsConstructor
public class UserCheckinApplicationSearchResultPagedResponse {
    private List<UserCheckinApplicationSearchResult> checkinApplications;
    private int currentPage;
    private int totalPage;
}
