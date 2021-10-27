package org.eden.lovestation.controller;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.request.Auth;
import org.eden.lovestation.exception.business.LoginFailException;
import org.eden.lovestation.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    private final AdminRepository adminRepository;
    private final ReferralEmployeeRepository referralEmployeeRepository;
    private final UserRepository userRepository;
    private final LandlordRepository landlordRepository;
    private final VolunteerRepository volunteerRepository;
    private final FirmEmployeesRepository firmEmployeesRepository;

    @Autowired
    public AuthController(AuthServiceImpl authService,
                          AdminRepository adminRepository,
                          ReferralEmployeeRepository referralEmployeeRepository,
                          UserRepository userRepository,
                          LandlordRepository landlordRepository,
                          VolunteerRepository volunteerRepository,
                          FirmEmployeesRepository firmEmployeesRepository) {
        this.authService = authService;
        this.adminRepository = adminRepository;
        this.referralEmployeeRepository = referralEmployeeRepository;
        this.userRepository = userRepository;
        this.landlordRepository = landlordRepository;
        this.volunteerRepository = volunteerRepository;
        this.firmEmployeesRepository = firmEmployeesRepository;
    }

    @PostMapping(value = "")
    // todo add @Valid for production
    public ResponseEntity<?> getToken(@RequestBody Auth auth) throws LoginFailException {
        String token = authService.getToken(auth);
        return new ResponseEntity<>(Map.of("x-eden-token", token), HttpStatus.CREATED);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getUserInfo(Principal principal){
        String userName = "";
        String userRole = "";
        String userRoleDetail = "";

        // try if user is Admin
        String[] adminInfo = getAdminInfo(principal.getName());
        userName = adminInfo[0];
        userRole = "admin";
        userRoleDetail = adminInfo[1];

        // try if user is Referral_Employee
        if(userName.length() == 0){
            userName = getReferralEmployeeInfo(principal.getName());
            userRole = "referral_employee";
            userRoleDetail = "referral_employee";
        }

        // try if user is User
        if(userName.length() == 0){
            userName = getUserInfo(principal.getName());
            userRole = "user";
            userRoleDetail = "user";
        }

        // try if user is Landlord
        if(userName.length() == 0){
            userName = getLandlordInfo(principal.getName());
            userRole = "landlord";
            userRoleDetail = "landlord";
        }

        // try if user is Volunteer
        if(userName.length() == 0){
            userName = getVolunteerInfo(principal.getName());
            userRole = "volunteer";
            userRoleDetail = "volunteer";
        }

        // try if user is FirmEmployees
        if(userName.length() == 0){
            userName = getFirmEmployeesInfo(principal.getName());
            userRole = "firm";
            userRoleDetail = "firm";
        }

        Map<String, Object> userLoginInfo = new HashMap<>();
        userLoginInfo.put("userName", userName);
        userLoginInfo.put("userRole", userRole);
        userLoginInfo.put("userRoleDetail", userRoleDetail);

        return new ResponseEntity<>(userLoginInfo, HttpStatus.CREATED);
    }

    public String[] getAdminInfo(String userId){
        // try if user is Admin
        try {
            Admin admin = adminRepository.getOne(userId);

            String[] adminInfo = new String[2];
            adminInfo[0] = admin.getName();
            adminInfo[1] = admin.getRole().getName();

            return adminInfo;
        }catch (Exception e){
            return new String[2];
        }
    }

    public String getReferralEmployeeInfo(String userId){
        try{
            Optional<ReferralEmployee> referralEmployee = referralEmployeeRepository.findById(userId);
            if(referralEmployee.isPresent()){
                return referralEmployee.get().getName();
            }else{
                return "";
            }
        }catch (Exception e){
            return "";
        }
    }

    public String getUserInfo(String userId){
        try{
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){
                return user.get().getChineseName();
            }else{
                return "";
            }
        }catch (Exception e){
            return "";
        }
    }

    public String getLandlordInfo(String userId){
        try{
            Optional<Landlord> landlord = landlordRepository.findById(userId);
            if(landlord.isPresent()){
                return landlord.get().getChineseName();
            }else{
                return "";
            }
        }catch (Exception e){
            return "";
        }
    }

    public String getVolunteerInfo(String userId){
        try{
            Optional<Volunteer> volunteer = volunteerRepository.findById(userId);
            if(volunteer.isPresent()){
                return volunteer.get().getChineseName();
            }else{
                return "";
            }
        }catch (Exception e){
            return "";
        }
    }

    public String getFirmEmployeesInfo(String userId){
        try{
            Optional<FirmEmployees> firmEmployees = firmEmployeesRepository.findById(userId);
            if(firmEmployees.isPresent()){
                return firmEmployees.get().getChineseName();
            }else{
                return "";
            }
        }catch (Exception e){
            return "";
        }
    }
}
