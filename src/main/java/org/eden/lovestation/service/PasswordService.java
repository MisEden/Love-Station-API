package org.eden.lovestation.service;

import org.eden.lovestation.dto.request.PasswordReset;
import org.eden.lovestation.dto.request.UpdatePasswordReset;
import org.eden.lovestation.exception.business.PasswordResetConfirmException;
import org.eden.lovestation.exception.business.PasswordResetRateLimitException;
import org.eden.lovestation.exception.business.PasswordResetUpdateNotFoundException;

import java.util.Map;

public interface PasswordService {
    Map<String, String> savePasswordReset(PasswordReset passwordReset) throws PasswordResetRateLimitException;

    Map<String, String> updatePassword(UpdatePasswordReset updatePasswordReset, String passwordResetId) throws PasswordResetUpdateNotFoundException, PasswordResetConfirmException;
}
