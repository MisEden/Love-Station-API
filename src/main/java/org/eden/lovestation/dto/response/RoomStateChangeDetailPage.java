package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.HouseDetail;
import org.eden.lovestation.dto.projection.RoomStateChangeDetail;

import java.util.List;

@Data
@AllArgsConstructor
public class RoomStateChangeDetailPage {

    private List<RoomStateChangeDetail> roomStateChangeDetails ;
    private int currentPage;
    private int totalPage;

}
