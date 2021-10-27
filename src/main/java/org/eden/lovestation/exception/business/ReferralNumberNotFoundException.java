package org.eden.lovestation.exception.business;

public class ReferralNumberNotFoundException extends Exception {
    public ReferralNumberNotFoundException() {
        super("無法根據條件取得轉介編號(Referral Number Not Found)");
    }
}
