package org.eden.lovestation.service;

import org.eden.lovestation.dao.model.User;
import org.eden.lovestation.dto.request.RegisterUserRequest;
import org.eden.lovestation.exception.business.*;

import java.util.List;

public interface UserService {
    // defined some crud service
    User register(RegisterUserRequest request) throws RepeatedAccountException, RoleNotFoundException, ReferralNumberNotFoundException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException;

    List<String> findAllChineseNameByFilter(String chineseName);

    // todo
    // just for testing, need to delete
    void updateLineId(String userId, String newLineId);
}
