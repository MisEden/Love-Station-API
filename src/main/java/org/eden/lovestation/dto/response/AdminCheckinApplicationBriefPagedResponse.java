package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.AdminCheckinApplicationSearchResult;
import org.eden.lovestation.dto.projection.CheckinApplicationBrief;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminCheckinApplicationBriefPagedResponse {

    private List<CheckinApplicationBrief> checkinApplicationBriefs;
    private int currentPage;
    private int totalPage;

}
