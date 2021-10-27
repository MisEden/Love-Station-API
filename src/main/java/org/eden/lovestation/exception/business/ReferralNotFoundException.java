package org.eden.lovestation.exception.business;

public class ReferralNotFoundException extends Exception {
    public ReferralNotFoundException() {
        super("無法根據條件取得轉介單位資料(Referral Not Found)");
    }
}
