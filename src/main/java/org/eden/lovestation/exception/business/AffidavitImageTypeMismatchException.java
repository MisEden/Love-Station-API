package org.eden.lovestation.exception.business;

public class AffidavitImageTypeMismatchException extends Exception {
    public AffidavitImageTypeMismatchException() {
        super("切結書圖片格式錯誤(Affidavit Image Content-Type Must be image/jpeg Or image/png)");
    }
}
