package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.request.Auth;
import org.eden.lovestation.exception.business.LoginFailException;
import org.eden.lovestation.util.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReferralEmployeeRepository referralEmployeeRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private LandlordRepository landlordRepository;
    @Autowired
    private VolunteerRepository volunteerRepository;
    @Autowired
    private FirmEmployeesRepository firmEmployeesRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Optional<User> userInstance = userRepository.findByAccountAndVerified(account, true);
        Optional<ReferralEmployee> referralEmployeeInstance = referralEmployeeRepository.findByAccountAndVerified(account, true);
        Optional<Admin> adminInstance = adminRepository.findByAccount(account);
        Optional<Landlord> landlordInstance = landlordRepository.findByAccountAndVerified(account, true);
        Optional<Volunteer> volunteerInstance = volunteerRepository.findByAccountAndVerified(account, true);
        Optional<FirmEmployees> firmInstance = firmEmployeesRepository.findByAccountAndVerified(account, true);
        UserDetails userDetails;
        if (userInstance.isPresent()) {
            User user = userInstance.get();
            userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getAccount())
                    .password(user.getPassword())
                    .roles(user.getRole().getName())
                    .build();
        } else if (referralEmployeeInstance.isPresent()) {
            ReferralEmployee referralEmployee = referralEmployeeInstance.get();
            userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(referralEmployee.getAccount())
                    .password(referralEmployee.getPassword())
                    .roles(referralEmployee.getRole().getName())
                    .build();
        } else if (adminInstance.isPresent()) {
            Admin admin = adminInstance.get();
            userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(admin.getAccount())
                    .password(admin.getPassword())
                    .roles(admin.getRole().getName())
                    .build();
        } else if (landlordInstance.isPresent()) {
            Landlord landlord = landlordInstance.get();
            userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(landlord.getAccount())
                    .password(landlord.getPassword())
                    .roles(landlord.getRole().getName())
                    .build();
        } else if (volunteerInstance.isPresent()) {
            Volunteer volunteer = volunteerInstance.get();
            userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(volunteer.getAccount())
                    .password(volunteer.getPassword())
                    .roles(volunteer.getRole().getName())
                    .build();
        } else if (firmInstance.isPresent()) {
            FirmEmployees firmEmployees = firmInstance.get();
            userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(firmEmployees.getAccount())
                    .password(firmEmployees.getPassword())
                    .roles(firmEmployees.getRole().getName())
                    .build();
        } else {
            throw new UsernameNotFoundException("account not found");
        }
        return userDetails;
    }

    public String getToken(Auth auth) throws LoginFailException {
        try {
            String token;
            String account = auth.getAccount();
            String password = auth.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account, password));
            Optional<User> userInstance = userRepository.findByAccountAndVerified(account, true);
            Optional<ReferralEmployee> referralEmployeeInstance = referralEmployeeRepository.findByAccountAndVerified(account, true);
            Optional<Admin> adminInstance = adminRepository.findByAccountAndVerified(account, true);
            Optional<Landlord> landlordInstance = landlordRepository.findByAccountAndVerified(account, true);
            Optional<Volunteer> volunteerInstance = volunteerRepository.findByAccountAndVerified(account, true);
            Optional<FirmEmployees> firmInstance = firmEmployeesRepository.findByAccountAndVerified(account, true);
            if (userInstance.isPresent()) {
                token = jwtUtil.sign(userInstance.get().getId(), userInstance.get().getRole().getName());
            } else if (referralEmployeeInstance.isPresent()) {
                token = jwtUtil.sign(referralEmployeeInstance.get().getId(), referralEmployeeInstance.get().getRole().getName());
            } else if (adminInstance.isPresent()) {
                token = jwtUtil.sign(adminInstance.get().getId(), adminInstance.get().getRole().getName());
            } else if (landlordInstance.isPresent()) {
                token = jwtUtil.sign(landlordInstance.get().getId(), landlordInstance.get().getRole().getName());
            } else if (volunteerInstance.isPresent()) {
                token = jwtUtil.sign(volunteerInstance.get().getId(), volunteerInstance.get().getRole().getName());
            } else if (firmInstance.isPresent()) {
                token = jwtUtil.sign(firmInstance.get().getId(), firmInstance.get().getRole().getName());
            } else {
                throw new LoginFailException();
            }
            return token;
        } catch (Exception e) {
            throw new LoginFailException();
        }
    }
}
