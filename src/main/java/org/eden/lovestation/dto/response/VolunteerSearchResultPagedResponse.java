package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.LandlordDetail;
import org.eden.lovestation.dto.projection.VolunteerDetail;

import java.util.List;

@Data
@AllArgsConstructor
public class VolunteerSearchResultPagedResponse {
    private List<VolunteerDetail> volunteer;
    private int currentPage;
    private int totalPage;
}
