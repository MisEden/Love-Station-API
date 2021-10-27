package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.FirmEmployeeCheckinApplicationSearchResult;

import java.util.List;

@Data
@AllArgsConstructor
public class FirmEmployeeCheckinApplicationResultPagedResponse {

    private List<FirmEmployeeCheckinApplicationSearchResult> checkinApplications;
    private int currentPage;
    private int totalPage;

}
