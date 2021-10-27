package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.CheckinInfoAdminNeed;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminGetScheduledCheckinResponse {

    private String date;

    private List<CheckinInfoAdminNeed> infoAdminNeeds;

    private int currentPage;
    private int totalPage;
}
