package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.AdminAccountDetail;
import org.eden.lovestation.dto.projection.FirmDetail;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminAccountSearchResultPagedResponse {
    private List<AdminAccountDetail> admins;
    private int currentPage;
    private int totalPage;
}
