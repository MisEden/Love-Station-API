package org.eden.lovestation.controller;

import org.eden.lovestation.service.ReferralTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/referral-titles")
public class ReferralTitleController {

    private final ReferralTitleService referralTitleService;

    @Autowired
    public ReferralTitleController(ReferralTitleService referralTitleService) {
        this.referralTitleService = referralTitleService;
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(referralTitleService.findAll(), HttpStatus.OK);
    }

}
