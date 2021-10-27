package org.eden.lovestation.exception.business;

public class ReferralEmployeeNotFoundException extends Exception {
    public ReferralEmployeeNotFoundException() {
        super("無法根據條件取得轉介人員帳號(ReferralEmployee Not Found)");
    }
}
