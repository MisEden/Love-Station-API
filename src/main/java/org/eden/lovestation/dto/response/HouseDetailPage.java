package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.HouseDetail;

import java.util.List;

@Data
@AllArgsConstructor
public class HouseDetailPage {
    private List<HouseDetail> houses;
    private int currentPage;
    private int totalPage;
}
