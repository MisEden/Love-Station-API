package org.eden.lovestation.util.checker;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.exception.business.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CheckerUtil {

    private final UserRepository userRepository;
    private final ReferralEmployeeRepository referralEmployeeRepository;
    private final ReferralRepository referralRepository;
    private final ReferralTitleRepository referralTitleRepository;
    private final ReferralNumberRepository referralNumberRepository;
    private final AdminRepository adminRepository;
    private final LandlordRepository landlordRepository;
    private final VolunteerRepository volunteerRepository;
    private final FirmRepository firmRepository;
    private final FirmEmployeesRepository firmEmployeesRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CheckerUtil(UserRepository userRepository,
                       ReferralEmployeeRepository referralEmployeeRepository,
                       ReferralRepository referralRepository,
                       ReferralTitleRepository referralTitleRepository,
                       ReferralNumberRepository referralNumberRepository,
                       LandlordRepository landlordRepository,
                       VolunteerRepository volunteerRepository,
                       FirmRepository firmRepository,
                       FirmEmployeesRepository firmEmployeesRepository,
                       AdminRepository adminRepository,
                       RoleRepository roleRepository,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.referralEmployeeRepository = referralEmployeeRepository;
        this.referralRepository = referralRepository;
        this.referralTitleRepository = referralTitleRepository;
        this.referralNumberRepository = referralNumberRepository;
        this.adminRepository = adminRepository;
        this.landlordRepository = landlordRepository;
        this.volunteerRepository = volunteerRepository;
        this.firmRepository = firmRepository;
        this.firmEmployeesRepository = firmEmployeesRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public void checkRepeatedAccount(String account) throws RepeatedAccountException {
        if (userRepository.existsByAccount(account) ||
                referralEmployeeRepository.existsByAccount(account) ||
                adminRepository.existsByAccount(account) ||
                landlordRepository.existsByAccount(account) ||
                volunteerRepository.existsByAccount(account) ||
                firmEmployeesRepository.existsByAccount(account)) {
            throw new RepeatedAccountException();
        }
    }

    public void checkRepeatedEmail(String email) throws RepeatedEmailException {
        if (userRepository.existsByEmail(email) ||
                referralEmployeeRepository.existsByEmail(email) ||
                adminRepository.existsByEmail(email) ||
                landlordRepository.existsByEmail(email) ||
                volunteerRepository.existsByEmail(email) ||
                firmEmployeesRepository.existsByEmail(email)) {
            throw new RepeatedEmailException();
        }
    }

    public void checkRepeatedLineId(String lineId) throws RepeatedLineIdException {
        if (userRepository.existsByLineId(lineId) ||
                referralEmployeeRepository.existsByLineId(lineId) ||
                adminRepository.existsByLineId(lineId) ||
                landlordRepository.existsByLineId(lineId) ||
                volunteerRepository.existsByLineId(lineId) ||
                firmEmployeesRepository.existsByLineId(lineId)) {
            throw new RepeatedLineIdException();
        }
    }

    public void checkRepeatedIdentityCard(String identityCard) throws RepeatedIdentityCardException {
        if (userRepository.existsByIdentityCard(identityCard) ||
                landlordRepository.existsByIdentityCard(identityCard) ||
                volunteerRepository.existsByIdentityCard(identityCard) ||
                firmEmployeesRepository.existsByIdentityCard(identityCard)) {
            throw new RepeatedIdentityCardException();
        }
    }

    public Admin checkRegisterAdministratorMappingAndReturn(RegisterAdministratorRequest registerAdministratorRequest)  throws RoleNotFoundException{
        Admin admin = modelMapper.map(registerAdministratorRequest, Admin.class);
        Role role = roleRepository.findByName("admin_readonly").orElseThrow(RoleNotFoundException::new);
        admin.setRole(role);
        return admin;
    }

    public ReferralEmployee checkRegisterReferralEmployeeMappingAndReturn(RegisterReferralEmployeeRequest registerReferralEmployeeRequest) throws ReferralNotFoundException, ReferralTitleNotFoundException, RoleNotFoundException {
        ReferralEmployee referralEmployee = modelMapper.map(registerReferralEmployeeRequest, ReferralEmployee.class);
        Referral referral = referralRepository.findById(registerReferralEmployeeRequest.getReferralId()).orElseThrow(ReferralNotFoundException::new);
        ReferralTitle referralTitle = referralTitleRepository.findById(registerReferralEmployeeRequest.getReferralTitleId()).orElseThrow(ReferralTitleNotFoundException::new);
        Role role = roleRepository.findByName("referral_employee").orElseThrow(RoleNotFoundException::new);
        referralEmployee.setReferral(referral);
        referralEmployee.setReferralTitle(referralTitle);
        referralEmployee.setRole(role);
        return referralEmployee;
    }

    public User checkRegisterUserMappingAndReturn(RegisterUserRequest request) throws RoleNotFoundException {
        User user = modelMapper.map(request, User.class);
        Role role = roleRepository.findByName("user").orElseThrow(RoleNotFoundException::new);
        user.setRole(role);
        return user;
    }

    public FirmEmployees checkRegisterFirmMappingAndReturn(RegisterFirmEmployeeRequest registerFirmEmployeeRequest) throws RoleNotFoundException, FirmNotFoundException {
        //resolve naming conflict while model mapping
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        FirmEmployees firmEmployees = modelMapper.map(registerFirmEmployeeRequest, FirmEmployees.class);
        Firm firm = firmRepository.findById(registerFirmEmployeeRequest.getFirmId()).orElseThrow(FirmNotFoundException::new);
        Role role = roleRepository.findByName("firm_employee").orElseThrow(RoleNotFoundException::new);
        firmEmployees.setFirm(firm);
        firmEmployees.setRole(role);
        return firmEmployees;
    }

    public Landlord checkRegisterLandlordMappingAndReturn(RegisterLandlordRequest request) throws RoleNotFoundException {
        Landlord landlord = modelMapper.map(request, Landlord.class);
        Role role = roleRepository.findByName("landlord").orElseThrow(RoleNotFoundException::new);
        landlord.setRole(role);
        return landlord;
    }

    public Volunteer checkRegisterVolunteerMappingAndReturn(RegisterVolunteerRequest request) throws RoleNotFoundException {
        Volunteer volunteer = modelMapper.map(request, Volunteer.class);
        Role role = roleRepository.findByName("volunteer").orElseThrow(RoleNotFoundException::new);
        volunteer.setRole(role);
        return volunteer;
    }

    public void checkUserAlreadyGenerateReferralNumber(String identityCard) throws RepeatedGenerateReferralNumberException {
        if (referralNumberRepository.existsByIdentityCard(identityCard)) {
            throw new RepeatedGenerateReferralNumberException();
        }
    }

    public ReferralNumber checkUserAlreadyHasReferralNumberAndReturn(String identityCard) throws ReferralNumberNotFoundException {
        return referralNumberRepository.findByIdentityCard(identityCard).orElseThrow(ReferralNumberNotFoundException::new);
    }

    public boolean hasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

    public String getRole(String userId){
        String role;

        Optional<User> userOptional = userRepository.findByIdAndVerified(userId, true);
        Optional<ReferralEmployee> referralEmployeeOptional = referralEmployeeRepository.findByIdAndVerified(userId, true);
        Optional<Landlord> landlordOptional = landlordRepository.findByIdAndVerified(userId, true);
        Optional<Volunteer> volunteerOptional = volunteerRepository.findByIdAndVerified(userId, true);
        Optional<FirmEmployees> firmOptional = firmEmployeesRepository.findByIdAndVerified(userId, true);
        Optional<Admin> adminOptional = adminRepository.findById(userId);

        if (userOptional.isPresent()) {
            role = userOptional.get().getRole().getName();
        } else if (referralEmployeeOptional.isPresent()) {
            role = referralEmployeeOptional.get().getRole().getName();
        } else if (landlordOptional.isPresent()) {
            role = landlordOptional.get().getRole().getName();
        } else if (volunteerOptional.isPresent()) {
            role = volunteerOptional.get().getRole().getName();
        } else if (firmOptional.isPresent()) {
            role = firmOptional.get().getRole().getName();
        } else if (adminOptional.isPresent()) {
            role = adminOptional.get().getRole().getName();
        } else {
            // prevent hacker try email, still return
            return "";
        }

        return role;
    }
}
