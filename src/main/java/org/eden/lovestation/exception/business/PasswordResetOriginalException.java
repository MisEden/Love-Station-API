package org.eden.lovestation.exception.business;

public class PasswordResetOriginalException extends Exception {
    public PasswordResetOriginalException() {
        super("輸入的原密碼並不符合(Original Password Verification Failed)");
    }
}
