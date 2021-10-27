package org.eden.lovestation.exception.business;

public class RepeatedEmailException extends Exception {
    public RepeatedEmailException() {
        super("使用者資訊已存在於資料庫-信箱(Repeated Email)");
    }
}
