package org.eden.lovestation.controller;

import org.eden.lovestation.dao.model.User;
import org.eden.lovestation.dto.request.RegisterUserRequest;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserRequest request) throws RoleNotFoundException, ReferralNumberNotFoundException, RepeatedAccountException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException {
        User user = userService.register(request);
        return new ResponseEntity<>(Map.of("id", user.getId()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/chinese-names")
    public ResponseEntity<?> findAll(@RequestParam(value = "filter") String chineseNameFilter) {
        return new ResponseEntity<>(userService.findAllChineseNameByFilter(chineseNameFilter), HttpStatus.OK);
    }
}
