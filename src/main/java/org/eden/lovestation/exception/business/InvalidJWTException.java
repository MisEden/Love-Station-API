package org.eden.lovestation.exception.business;

public class InvalidJWTException extends Exception {
    public InvalidJWTException() {
        super("無法解析身份或不合法使用者(Invalid JWT Token)");
    }
}
