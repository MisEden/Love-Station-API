package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.CheckinApplicationWithRoomStateChangeDetail;
import org.eden.lovestation.dto.projection.CheckinApplicationWithRoomStateChangeInfoAdminNeed;

import java.util.List;

@Data
@AllArgsConstructor
public class CheckinApplicationWithRoomStateChangeInfoAdminNeedResponse {

    private List<CheckinApplicationWithRoomStateChangeInfoAdminNeed> checkinApplicationWithRoomStateChangeDetails;
    private int currentPage;
    private int totalPage;

}
