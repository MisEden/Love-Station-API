package org.eden.lovestation.controller;

import org.eden.lovestation.dao.model.Volunteer;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.VolunteerService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> save(@RequestBody @Valid RegisterVolunteerRequest request) throws RoleNotFoundException, RepeatedAccountException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException {
        Volunteer volunteer = volunteerService.register(request);
        return new ResponseEntity<>(Map.of("id", volunteer.getId()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/checkin-applications/assigned")
    public ResponseEntity<?> getCheckinApplicationAssigned(Principal principal,
                                                           @RequestParam(value = "yearAndMonth") String yearAndMonth) throws ParseException{
        System.out.println(principal.getName());
        return new ResponseEntity<>(volunteerService.getAssignedCheckinApplications(principal.getName(), yearAndMonth), HttpStatus.OK);
    }

    @PostMapping(value = "/service/record")
    public ResponseEntity<?> addCheckinApplicationVolunteerServiceRecord(@RequestBody SendServiceRecordRequest request, Principal principal) throws CheckinApplicationNotFoundException, VolunteerNotFoundException, HouseNotFoundException, RoomNotFoundException{
        return new ResponseEntity<>(volunteerService.addVolunteerServiceRecord(request, principal.getName()), HttpStatus.OK);
    }

    @GetMapping(value = "/service/record/get")
    public ResponseEntity<?> getVolunteerServiceRecords(@RequestParam String yearAndMonth, Principal principal) throws VolunteerServiceRecordNotFoundException, ParseException {
        return new ResponseEntity<>(volunteerService.getVolunteerServiceRecord(principal.getName(), yearAndMonth), HttpStatus.OK);
    }


}

