package org.eden.lovestation.exception.business;

public class HouseFullDegreePanoramaNotFoundException extends Exception {
    public HouseFullDegreePanoramaNotFoundException() {
        super("無法根據條件取得棧點環視圖(House FullDegreePanorama Not Found)");
    }
}
