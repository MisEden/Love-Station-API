package org.eden.lovestation.dto.request;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
public class SendCheckoutFeedbackRequest {

    private String bedding;
    private String beddingFeedback;

    private String bathroom;
    private String bathroomFeedback;

    private String refrigerator;
    private String refrigeratorFeedback;

    private String privateItem;
    private String privateItemFeedback;

    private String garbage;
    private String garbageFeedback;

    private String doorsWindowsPower;
    private String doorsWindowsPowerFeedback;

    private String securityNotification;
    private String securityNotificationFeedback;

    private String returnKey;
    private String returnKeyFeedback;

    private String returnCheckinFile;
    private String returnCheckinFileFeedback;

//    private  String checkoutFeedback;

//    public String getCheckoutFeedback(){ return this.checkoutFeedback; }

}
