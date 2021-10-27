package org.eden.lovestation.exception.business;

public class LoginFailException extends Exception {
    public LoginFailException() {
        super("帳號或密碼資訊錯誤(Account or Password is Invalid)");
    }
}
