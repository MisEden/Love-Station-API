package org.eden.lovestation.exception.business;

public class PasswordResetRateLimitException extends Exception {
    public PasswordResetRateLimitException() {
        super("密碼重置超過限制(Password Reset Limit Exceeds)");
    }
}
