package org.eden.lovestation.exception.business;

public class HouseNotFoundException extends Exception {
    public HouseNotFoundException() {
        super("無法根據條件取得棧點(House Not Found)");
    }
}
