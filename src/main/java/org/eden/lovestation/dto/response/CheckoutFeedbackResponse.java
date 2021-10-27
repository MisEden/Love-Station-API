package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.CheckoutFeedbackDetail;

import java.util.List;

@Data
@AllArgsConstructor
public class CheckoutFeedbackResponse {

    private String roomStateId;

    private List<CheckoutFeedbackDetail> checkoutFeedbackDetails;
}
