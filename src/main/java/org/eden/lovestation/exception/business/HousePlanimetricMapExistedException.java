package org.eden.lovestation.exception.business;

public class HousePlanimetricMapExistedException extends Exception {
    public HousePlanimetricMapExistedException() {
        super("請先將既有的平面圖刪除後方可上傳新照片(Upload House PlanimetricMap Fail)");
    }
}
