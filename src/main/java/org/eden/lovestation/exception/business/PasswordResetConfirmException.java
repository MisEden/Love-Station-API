package org.eden.lovestation.exception.business;

public class PasswordResetConfirmException extends Exception {
    public PasswordResetConfirmException() {
        super("兩次密碼輸入不相符(Password Must Equal to ConfirmPassword)");
    }
}
