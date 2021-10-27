package org.eden.lovestation.controller;

import org.eden.lovestation.dao.model.Landlord;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.LandlordService;
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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/landlords")
public class LandlordController {


    private final LandlordService landlordService;

    @Autowired
    public LandlordController(LandlordService landlordService) {
        this.landlordService = landlordService;
    }


    @PostMapping(value = "")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterLandlordRequest request) throws RepeatedAccountException, RoleNotFoundException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException {
        Landlord landlord = landlordService.register(request);
        return new ResponseEntity<>(Map.of("id", landlord.getId()), HttpStatus.CREATED);
    }


    @GetMapping(value = "/house/names")
    public ResponseEntity<?> getLandlordHouse(Principal principal) throws HouseNotFoundException{
        return new ResponseEntity<>(landlordService.getHouseName(principal.getName()), HttpStatus.OK);
    }

    @GetMapping(value = "/house/checkin-applications")
    public ResponseEntity<?> getCheckinApplicationForHouseWithHouseIdAndDate(@RequestParam String houseId, @RequestParam String yearAndMonth) throws HouseNotFoundException, CheckinApplicationNotFoundException, ParseException {
        return new ResponseEntity<>(landlordService.getCheckinAppForHouse(houseId, yearAndMonth), HttpStatus.OK);
    }

    @PostMapping(value = "/service/record")
    public ResponseEntity<?> addLandlordServiceRecord(@RequestBody SendServiceRecordRequest request, Principal principal) throws CheckinApplicationNotFoundException, LandlordNotFoundException, HouseNotFoundException, RoomNotFoundException {
        return new ResponseEntity<>(landlordService.addServiceRecord(request, principal.getName()), HttpStatus.OK);
    }

}
