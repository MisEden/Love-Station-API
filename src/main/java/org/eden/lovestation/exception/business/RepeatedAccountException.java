package org.eden.lovestation.exception.business;

public class RepeatedAccountException extends Exception {
    public RepeatedAccountException() {
        super("使用者資訊已存在於資料庫-帳號(Repeated Account)");
    }
}
