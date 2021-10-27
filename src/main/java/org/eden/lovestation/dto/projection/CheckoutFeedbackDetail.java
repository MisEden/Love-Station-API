package org.eden.lovestation.dto.projection;

import org.springframework.beans.factory.annotation.Value;

public interface CheckoutFeedbackDetail {

    String getBeddingFeedback();

    String getBathroomFeedback();

    String getRefrigeratorFeedback();

    String getPrivateItemFeedback();

    String getGarbageFeedback();

    String getDoorsWindowsPowerFeedback();

    String getSecurityNotificationFeedback();

    String getReturnKeyFeedback();

    String getReturnCheckinFileFeedback();

}
