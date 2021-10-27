package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.CheckinApplicationDetail;

import java.util.List;

@Data
@AllArgsConstructor
public class CheckinApplicationDetailPagedResponse {
    private List<CheckinApplicationDetail> checkinApplications;
    private int currentPage;
    private int totalPage;
}
