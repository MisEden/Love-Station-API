package org.eden.lovestation.exception.business;

public class LandlordNotFoundException extends Exception {
    public LandlordNotFoundException() {
        super("無法根據條件取得房東帳號(Landlord Not Found)");
    }
}
