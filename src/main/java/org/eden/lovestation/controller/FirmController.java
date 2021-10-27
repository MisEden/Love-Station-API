package org.eden.lovestation.controller;

import org.eden.lovestation.dao.repository.UserRepository;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.FirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/firms")
public class FirmController {

    private final FirmService firmService;
    private final UserRepository userRepository;

    @Autowired
    public FirmController(FirmService firmService,
                          UserRepository userRepository) {
        this.firmService = firmService;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/names/all")
    public ResponseEntity<?> getAllFirmNames() throws FirmNotFoundException{
        return new ResponseEntity<>(firmService.getFirmNames(), HttpStatus.OK);
    }
}

