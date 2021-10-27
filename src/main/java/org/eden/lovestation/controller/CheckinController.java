package org.eden.lovestation.controller;


import org.eden.lovestation.dao.model.CheckinForm;
import org.eden.lovestation.dto.projection.CheckinLocation;
import org.eden.lovestation.dto.request.CheckinCheckRequest;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/checkin")
public class CheckinController {

    private final CheckinService checkinService;

    @Autowired
    public CheckinController(CheckinService checkinService){ this.checkinService = checkinService;}

    //get the houseName and roomNumber by userAccount and checkinDate
    @GetMapping(value = "eligible/{date}")
    public ResponseEntity<?> getApplication(@PathVariable String date, Principal principal) throws UserNotFoundException, ParseException, RoomStateNotFoundException {
        CheckinLocation checkinLocation =checkinService.findUserCheckinLocation(date, principal.getName());

        Map<String, Object> eligible = new HashMap<String, Object>();
        if(checkinLocation != null){
            eligible.put("id", checkinLocation.getId());
            eligible.put("hid", checkinLocation.getHid());
            eligible.put("hname", checkinLocation.getHname());
            eligible.put("rid", checkinLocation.getRid());
            eligible.put("rname", checkinLocation.getRnumber());
            eligible.put("edate", checkinLocation.getEdate());
            eligible.put("countForms", checkinService.countCheckinForm(checkinLocation.getId()));
        }

        return new ResponseEntity<>(eligible, HttpStatus.OK);
    }

    //Get all public furniture by room ID
    @GetMapping(value = "/room/{id}/furniture/public")
    public ResponseEntity<?> findAllHousePublicFurniture(@PathVariable String id) throws RoomNotFoundException,HouseNotFoundException {
        return new ResponseEntity<>(checkinService.findAllHousePublicFurniture(id), HttpStatus.OK);
    }

    //Get all private furniture by room ID
    @GetMapping(value = "/room/{id}/furniture/private")
    public ResponseEntity<?> findAllHousePrivateFurniture(@PathVariable String id) throws RoomNotFoundException,HouseNotFoundException {
        return new ResponseEntity<>(checkinService.findAllHousePrivateFurniture(id), HttpStatus.OK);
    }


    //post the checkin result of user
    @PostMapping(value = "/")
    public ResponseEntity<?> saveCheckinResult(@RequestBody CheckinCheckRequest request, Principal principal) throws RoomStateNotFoundException, FurnitureNotFoundException, HouseNotFoundException, RoomNotFoundException, UserNotFoundException {
        CheckinForm checkinForms = checkinService.saveCheckinResult(request, principal.getName());
        return new ResponseEntity<>(Map.of("id", checkinForms.getId()), HttpStatus.OK);
    }

}
