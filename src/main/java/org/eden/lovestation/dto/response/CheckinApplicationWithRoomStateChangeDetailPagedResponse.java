package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.CheckinApplicationWithRoomStateChangeDetail;

import java.util.List;

@Data
@AllArgsConstructor
public class CheckinApplicationWithRoomStateChangeDetailPagedResponse {

    private List<CheckinApplicationWithRoomStateChangeDetail> checkinApplicationWithRoomStateChangeDetails;
    private int currentPage;
    private int totalPage;
}
