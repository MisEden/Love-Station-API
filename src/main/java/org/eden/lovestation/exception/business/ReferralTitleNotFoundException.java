package org.eden.lovestation.exception.business;

public class ReferralTitleNotFoundException extends Exception {
    public ReferralTitleNotFoundException() {
        super("無法根據條件取得轉介人員職銜(Referral Title Not Found)");
    }
}
