package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.FirmDetail;

import java.util.List;

@Data
@AllArgsConstructor
public class FirmSearchResultPagedResponse {
    private List<FirmDetail> firms;
    private int currentPage;
    private int totalPage;
}
