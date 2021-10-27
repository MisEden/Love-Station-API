package org.eden.lovestation.exception.business;

public class PasswordResetUpdateFailExcetion extends Exception {
    public PasswordResetUpdateFailExcetion() {
        super("不存在變更密碼的申請(Password Reset Update Fail, Not Found Any Record)");
    }
}
