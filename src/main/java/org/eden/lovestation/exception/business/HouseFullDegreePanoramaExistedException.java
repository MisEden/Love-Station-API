package org.eden.lovestation.exception.business;

public class HouseFullDegreePanoramaExistedException extends Exception {
    public HouseFullDegreePanoramaExistedException() {
        super("請先將既有的環視圖刪除後方可上傳新照片(Upload House FullDegreePanorama Fail)");
    }
}
