package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.ReferralNumber;
import org.eden.lovestation.dao.model.User;
import org.eden.lovestation.dao.repository.CheckinApplicationRepository;
import org.eden.lovestation.dao.repository.UserRepository;
import org.eden.lovestation.dto.request.RegisterUserRequest;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.UserService;
import org.eden.lovestation.util.checker.CheckerUtil;
import org.eden.lovestation.util.password.PasswordUtil;
import org.eden.lovestation.util.storage.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CheckinApplicationRepository checkinApplicationRepository;
    private final CheckerUtil checkerUtil;
    private final PasswordUtil passwordUtil;
    private final StorageUtil storageUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           CheckinApplicationRepository checkinApplicationRepository,
                           CheckerUtil checkerUtil,
                           PasswordUtil passwordUtil,
                           StorageUtil storageUtil) {
        this.userRepository = userRepository;
        this.checkinApplicationRepository = checkinApplicationRepository;
        this.checkerUtil = checkerUtil;
        this.passwordUtil = passwordUtil;
        this.storageUtil = storageUtil;
    }

    @Override
    public User register(RegisterUserRequest request) throws RepeatedAccountException, RoleNotFoundException, ReferralNumberNotFoundException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException {
        User user = checkerUtil.checkRegisterUserMappingAndReturn(request);
        checkerUtil.checkRepeatedAccount(user.getAccount());
//        checkerUtil.checkRepeatedEmail(user.getEmail());
//        checkerUtil.checkRepeatedLineId(user.getLineId());
        checkerUtil.checkRepeatedIdentityCard(user.getIdentityCard());
        ReferralNumber referralNumber = checkerUtil.checkUserAlreadyHasReferralNumberAndReturn(user.getIdentityCard());
        String plainPassword = user.getPassword();
        String hashPassword = passwordUtil.generateHashPassword(plainPassword);
        user.setReferralNumber(referralNumber);
        user.setPassword(hashPassword);
        return userRepository.save(user);
    }

    @Override
    public List<String> findAllChineseNameByFilter(String chineseName) {
        return userRepository.findAllChineseNameByFilter(chineseName);
    }

    // todo
    // just for testing, need to delete
    @Transactional
    @Override
    public void updateLineId(String userId, String newLineId) {
        userRepository.updateLineIdById(newLineId, userId);
    }
}
