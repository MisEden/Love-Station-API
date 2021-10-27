package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.CheckoutInfoAdminNeed;

import java.util.List;

@Data
@AllArgsConstructor
public class AdminGetScheduledCheckoutResponse {

    private String date;

    private List<CheckoutInfoAdminNeed> infoAdminNeeds;

    private int currentPage;
    private int totalPage;
}
