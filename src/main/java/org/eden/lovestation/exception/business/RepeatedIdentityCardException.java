package org.eden.lovestation.exception.business;

public class RepeatedIdentityCardException extends Exception {
    public RepeatedIdentityCardException() {
        super("使用者資訊已存在於資料庫-身分證字號(Repeated IdentityCard)");
    }
}
