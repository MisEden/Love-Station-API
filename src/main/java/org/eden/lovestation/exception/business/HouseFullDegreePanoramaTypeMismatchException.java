package org.eden.lovestation.exception.business;

public class HouseFullDegreePanoramaTypeMismatchException extends Exception {
    public HouseFullDegreePanoramaTypeMismatchException() {
        super("棧點環視圖格式錯誤(House FullDegreePanorama Content-Type Must be image/jpeg Or image/png)");
    }
}
