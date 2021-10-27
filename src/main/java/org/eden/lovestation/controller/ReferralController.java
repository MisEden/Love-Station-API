package org.eden.lovestation.controller;

import org.eden.lovestation.dto.request.AdminUpdateReferral;
import org.eden.lovestation.dto.request.DataExport;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/referrals")
public class ReferralController {

    private final ReferralService referralService;

    @Autowired
    public ReferralController(ReferralService referralService) {
        this.referralService = referralService;
    }

    @CrossOrigin
    @GetMapping(value = "")
    public ResponseEntity<?> getAllDetail() {
        return new ResponseEntity<>(referralService.findAllDetail(), HttpStatus.OK);
    }


}
