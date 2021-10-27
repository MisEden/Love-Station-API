package org.eden.lovestation.controller;


import org.eden.lovestation.dto.request.PasswordReset;
import org.eden.lovestation.dto.request.UpdatePasswordReset;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.NotificationService;
import org.eden.lovestation.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/passwords")
public class PasswordController {

    private final PasswordService passwordService;
    private final NotificationService notificationService;

    @Autowired
    public PasswordController(PasswordService passwordService,
                              NotificationService notificationService) {
        this.passwordService = passwordService;
        this.notificationService = notificationService;
    }


    @PostMapping(value = "/reset")
    public ResponseEntity<?> save(@RequestBody @Valid PasswordReset passwordReset) throws PasswordResetRateLimitException, UserNotFoundException, LineNotificationException, ReferralEmployeeNotFoundException, EmailNotFoundException {
        Map<String, String> userInfo = passwordService.savePasswordReset(passwordReset);

        if(userInfo != null){
            try{
                notificationService.sendNotificationForget(userInfo);
            }catch (Exception e){
                throw new LineNotificationException();
            }

        }else{
            throw new EmailNotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/reset/{id}")
    public ResponseEntity<?> save(@PathVariable String id, @RequestBody @Valid UpdatePasswordReset updatePasswordReset) throws PasswordResetUpdateNotFoundException, PasswordResetConfirmException, UserNotFoundException, LineNotificationException, ReferralEmployeeNotFoundException {
        Map<String, String> userInfo = passwordService.updatePassword(updatePasswordReset, id);
        notificationService.sendNotificationRepassword(userInfo);
        return new ResponseEntity<>(userInfo.get("role"), HttpStatus.OK);
    }
}
