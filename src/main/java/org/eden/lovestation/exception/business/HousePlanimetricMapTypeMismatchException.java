package org.eden.lovestation.exception.business;

public class HousePlanimetricMapTypeMismatchException extends Exception {
    public HousePlanimetricMapTypeMismatchException() {
        super("棧點平面圖格式錯誤(House PlanimetricMap Content-Type Must be image/jpeg Or image/png)");
    }
}
