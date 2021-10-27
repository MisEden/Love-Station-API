package org.eden.lovestation.exception.business;

public class CleaningImageTypeMismatchException extends Exception{

    public CleaningImageTypeMismatchException() {
        super("圖片格式錯誤(Image Content-Type Must be image/jpg Or image/jpeg Or image/png)");
    }

}
