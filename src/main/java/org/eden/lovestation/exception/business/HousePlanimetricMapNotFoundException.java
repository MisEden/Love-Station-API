package org.eden.lovestation.exception.business;

public class HousePlanimetricMapNotFoundException extends Exception {
    public HousePlanimetricMapNotFoundException() {
        super("無法根據條件取得棧點平面圖(House PlanimetricMap Not Found)");
    }
}
