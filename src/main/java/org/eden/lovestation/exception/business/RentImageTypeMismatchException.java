package org.eden.lovestation.exception.business;

public class RentImageTypeMismatchException extends Exception {
    public RentImageTypeMismatchException() {
        super("契約書圖片格式錯誤(Rent Image Content-Type Must be image/jpeg or image/png)");
    }
}
