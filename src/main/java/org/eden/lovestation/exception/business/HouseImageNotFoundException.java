package org.eden.lovestation.exception.business;

public class HouseImageNotFoundException extends Exception {
    public HouseImageNotFoundException() {
        super("無法根據條件取得棧點照片(House Image Not Found)");
    }
}
