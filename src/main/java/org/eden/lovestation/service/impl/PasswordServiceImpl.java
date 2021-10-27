package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.request.PasswordReset;
import org.eden.lovestation.dto.request.UpdatePasswordReset;
import org.eden.lovestation.exception.business.PasswordResetConfirmException;
import org.eden.lovestation.exception.business.PasswordResetRateLimitException;
import org.eden.lovestation.exception.business.PasswordResetUpdateNotFoundException;
import org.eden.lovestation.service.PasswordService;
import org.eden.lovestation.util.checker.CheckerUtil;
import org.eden.lovestation.util.email.EmailUtil;
import org.eden.lovestation.util.password.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final String PASSWORD_RESET_COUNT_PREFIX = "password-reset-count:";
    private final String USER_PASSWORD_RESET_PREFIX = "user-password-reset:";

    private final EmailUtil emailUtil;
    private final PasswordUtil passwordUtil;
    private final CheckerUtil checkerUtil;

    private final RedisTemplate<String, String> redisTemplate;

    private final UserRepository userRepository;
    private final ReferralEmployeeRepository referralEmployeeRepository;
    private final LandlordRepository landlordRepository;
    private final VolunteerRepository volunteerRepository;
    private final FirmEmployeesRepository firmEmployeesRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public PasswordServiceImpl(RedisTemplate<String, String> redisTemplate,
                               UserRepository userRepository,
                               ReferralEmployeeRepository referralEmployeeRepository,
                               VolunteerRepository volunteerRepository,
                               LandlordRepository landlordRepository,
                               FirmEmployeesRepository firmEmployeesRepository,
                               AdminRepository adminRepository,
                               EmailUtil emailUtil,
                               PasswordUtil passwordUtil,
                               CheckerUtil checkerUtil) {
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
        this.referralEmployeeRepository = referralEmployeeRepository;
        this.volunteerRepository = volunteerRepository;
        this.landlordRepository = landlordRepository;
        this.firmEmployeesRepository = firmEmployeesRepository;
        this.adminRepository = adminRepository;
        this.emailUtil = emailUtil;
        this.passwordUtil = passwordUtil;
        this.checkerUtil = checkerUtil;
    }

    private boolean checkPasswordResetCount(String userId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        Boolean isExists = ops.setIfAbsent(PASSWORD_RESET_COUNT_PREFIX + userId, "1", 1, TimeUnit.MINUTES);
        if (isExists != null && !isExists) {
            Long count = ops.increment(PASSWORD_RESET_COUNT_PREFIX + userId);
            return count != null && count > 3;
        }
        return false;
    }

    @Override
    public Map<String, String> savePasswordReset(PasswordReset passwordReset) throws PasswordResetRateLimitException {
        String id;
        String role;

        String email = passwordReset.getEmail();
        Optional<User> userOptional = userRepository.findByEmailAndVerified(email, true);
        Optional<ReferralEmployee> referralEmployeeOptional = referralEmployeeRepository.findByEmailAndVerified(email, true);
        Optional<Landlord> landlordOptional = landlordRepository.findByEmailAndVerified(email, true);
        Optional<Volunteer> volunteerOptional = volunteerRepository.findByEmailAndVerified(email, true);
        Optional<FirmEmployees> firmOptional = firmEmployeesRepository.findByEmailAndVerified(email, true);
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);



        if (userOptional.isPresent()) {
            id = userOptional.get().getId();
            role = userOptional.get().getRole().getName();
        } else if (referralEmployeeOptional.isPresent()) {
            id = referralEmployeeOptional.get().getId();
            role = referralEmployeeOptional.get().getRole().getName();
        } else if (landlordOptional.isPresent()) {
            id = landlordOptional.get().getId();
            role = landlordOptional.get().getRole().getName();
        } else if (volunteerOptional.isPresent()) {
            id = volunteerOptional.get().getId();
            role = volunteerOptional.get().getRole().getName();
        } else if (firmOptional.isPresent()) {
            id = firmOptional.get().getId();
            role = firmOptional.get().getRole().getName();
        } else if (adminOptional.isPresent()) {
            id = adminOptional.get().getId();
            role = adminOptional.get().getRole().getName();
        } else {
            // prevent hacker try email, still return
            return null;
        }


        if (checkPasswordResetCount(id)) {
            throw new PasswordResetRateLimitException();
        }

        // store password reset
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String uid = UUID.randomUUID().toString();
        ops.set(USER_PASSWORD_RESET_PREFIX + uid, id, 10, TimeUnit.MINUTES);

        // send email
        emailUtil.sendPasswordResetEmail(email, uid);


        Map<String, String> userInfo = new HashMap<String, String>();
        userInfo.put("userId", id);
        userInfo.put("role", role);

        return userInfo;
    }

    private String getPasswordResetId(String passwordResetId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(USER_PASSWORD_RESET_PREFIX + passwordResetId);
    }

    private void deletePasswordReset(String passwordResetId) {
        redisTemplate.delete(USER_PASSWORD_RESET_PREFIX + passwordResetId);
    }

    @Transactional
    @Override
    public Map<String, String> updatePassword(UpdatePasswordReset updatePasswordReset, String passwordResetId) throws PasswordResetUpdateNotFoundException, PasswordResetConfirmException {
        if (!updatePasswordReset.getConfirmPassword().equals(updatePasswordReset.getPassword())) {
            throw new PasswordResetConfirmException();
        }
        String userId = getPasswordResetId(passwordResetId);
        System.out.println("[rePassword/RestPW] userId=" + userId);
        if (userId == null) {
            throw new PasswordResetUpdateNotFoundException();
        }
        String newPassword = updatePasswordReset.getPassword();
        String newPasswordHash = passwordUtil.generateHashPassword(newPassword);

        System.out.println("[rePassword/UserRole] role=" + checkerUtil.getRole(userId));

        if (checkerUtil.getRole(userId).equals("user")) {
            userRepository.updatePasswordById(newPasswordHash, userId);
            System.out.println("[rePassword/UserRole] User's password changing");
        } else if (checkerUtil.getRole(userId).equals("referral_employee")) {
            referralEmployeeRepository.updatePasswordById(newPasswordHash, userId);
            System.out.println("[rePassword/UserRole] ReferralEmployee's password changing");
        } else if (checkerUtil.getRole(userId).equals("landlord")) {
            landlordRepository.updatePasswordById(newPasswordHash, userId);
            System.out.println("[rePassword/UserRole] Landlord's password changing");
        } else if (checkerUtil.getRole(userId).equals("volunteer")) {
            volunteerRepository.updatePasswordById(newPasswordHash, userId);
            System.out.println("[rePassword/UserRole] Volunteer's password changing");
        } else if (checkerUtil.getRole(userId).equals("firm")) {
            firmEmployeesRepository.updatePasswordById(newPasswordHash, userId);
            System.out.println("[rePassword/UserRole] Firm's password changing");
        } else if (checkerUtil.getRole(userId).equals("admin")) {
            adminRepository.updatePasswordById(newPasswordHash, userId);
            System.out.println("[rePassword/UserRole] Admin's password changing");
        }
        deletePasswordReset(passwordResetId);

        Map<String, String> userInfo = new HashMap<String, String>();
        userInfo.put("userId", userId);
        userInfo.put("role", checkerUtil.getRole(userId));

        return userInfo;
    }
}
