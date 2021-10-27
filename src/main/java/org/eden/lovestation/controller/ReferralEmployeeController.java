package org.eden.lovestation.controller;

import org.eden.lovestation.dao.model.ReferralEmployee;
import org.eden.lovestation.dao.model.ReferralNumber;
import org.eden.lovestation.dto.request.GenerateReferralNumberRequest;
import org.eden.lovestation.dto.request.RegisterReferralEmployeeRequest;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.ReferralEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.security.Principal;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/referral-employees")
public class ReferralEmployeeController {

    private final ReferralEmployeeService referralEmployeeService;


    @Autowired
    public ReferralEmployeeController(ReferralEmployeeService referralEmployeeService) {
        this.referralEmployeeService = referralEmployeeService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> register(@RequestHeader("host") String host, @ModelAttribute @Valid RegisterReferralEmployeeRequest request) throws RoleNotFoundException, ReferralNotFoundException, ReferralTitleNotFoundException, RepeatedAccountException, SaveReferralEmployeeMetaDataException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException {
        ReferralEmployee referralEmployee = referralEmployeeService.register(request, host);
        return new ResponseEntity<>(Map.of("id", referralEmployee.getId()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<?> findDetail(Principal principal) throws ReferralEmployeeNotFoundException {
        return new ResponseEntity<>(referralEmployeeService.findDetailById(principal.getName()), HttpStatus.OK);
    }

    @PostMapping(value = "/me/referral-numbers")
    public ResponseEntity<?> generateReferralNumber(@RequestBody @Valid GenerateReferralNumberRequest request, Principal principal) throws ReferralEmployeeNotFoundException, RepeatedGenerateReferralNumberException {
        ReferralNumber referralNumber = referralEmployeeService.generateReferralNumber(request, principal.getName());
        return new ResponseEntity<>(Map.of("id", referralNumber.getId()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/me/room-states/change")
    public ResponseEntity<?> getCheckinApplicationRoomStatesChangePaged(@RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage, Principal principal) throws RoomStateChangeNotFoundException{
        return new ResponseEntity<>(referralEmployeeService.getRoomStatesChange(principal.getName(), currentPage), HttpStatus.OK);
    }

    @PatchMapping(value = "/{checkinAppId}/update/room-state/change")
    public ResponseEntity<?> updateCheckinApplicationRoomStateChange(@PathVariable String checkinAppId,
                                                                     @RequestParam(value = "newStartDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date newStartDate,
                                                                     @RequestParam(value = "newEndDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date newEndDate,
                                                                     @RequestParam(value = "changedItem", required = false, defaultValue = "") String changedItem,
                                                                     @RequestParam(value = "reason", required = false, defaultValue = "") String reason,
                                                                     Principal principal) throws CheckinApplicationNotFoundException, RoomStateChangeNotFoundException{
        return new ResponseEntity<>(referralEmployeeService.updateRoomStateChange(checkinAppId, newStartDate, newEndDate, changedItem, reason), HttpStatus.OK);
    }



}
