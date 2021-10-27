package org.eden.lovestation.exception.business;

public class PasswordResetUpdateNotFoundException extends Exception {
    public PasswordResetUpdateNotFoundException() {
        super("不存在變更密碼的申請(Password Reset Update Not Found)");
    }
}
