package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.LandlordContact;
import org.eden.lovestation.dto.projection.LandlordDetail;

import java.util.List;

@Data
@AllArgsConstructor
public class LandlordContactSearchResultPagedResponse {
    private List<LandlordContact> landlord;
    private int currentPage;
    private int totalPage;
}
