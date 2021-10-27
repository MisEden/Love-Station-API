package org.eden.lovestation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CheckoutActualTimeResponse {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Taipei")
    Date checkoutActualTime;

    Boolean checkoutAlready;

    public CheckoutActualTimeResponse(Date checkoutActualTime, Boolean checkoutAlready){
        this.checkoutActualTime = checkoutActualTime;
        this.checkoutAlready = checkoutAlready;
    }

}
