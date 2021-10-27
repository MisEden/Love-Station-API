package org.eden.lovestation.exception.business;

public class HouseImageTypeMismatchException extends Exception {
    public HouseImageTypeMismatchException() {
        super("棧點圖片格式錯誤(House Image Content-Type Must be image/jpeg Or image/png)");
    }
}
