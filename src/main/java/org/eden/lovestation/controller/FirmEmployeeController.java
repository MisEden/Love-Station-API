package org.eden.lovestation.controller;

import org.eden.lovestation.dao.model.FirmEmployees;
import org.eden.lovestation.dto.request.RegisterFirmEmployeeRequest;
import org.eden.lovestation.dto.request.SendFirmEmployeeServiceRecordRequest;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.FirmEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/firm-employees")
public class FirmEmployeeController {

    private final FirmEmployeeService firmEmployeeService;

    @Autowired
    public FirmEmployeeController(FirmEmployeeService firmEmployeeService) {
        this.firmEmployeeService = firmEmployeeService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterFirmEmployeeRequest request) throws RoleNotFoundException, RepeatedAccountException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException, FirmNotFoundException {
        FirmEmployees firmEmployees = firmEmployeeService.register(request);
        return new ResponseEntity<>(Map.of("id", firmEmployees.getId()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/checkin-applications/assigned")
    public ResponseEntity<?> getCheckinApplicationAssigned(Principal principal,
                                                           @RequestParam(value = "yearAndMonth") String yearAndMonth) throws ParseException{
        System.out.println(principal.getName());
        return new ResponseEntity<>(firmEmployeeService.getAssignedCheckinApplications(principal.getName(), yearAndMonth), HttpStatus.OK);
    }

    @PostMapping(value = "/service/record")
    public ResponseEntity<?> addFirmEmployeeServiceRecord(@RequestHeader("host") String host, @ModelAttribute @Valid SendFirmEmployeeServiceRecordRequest request, Principal principal) throws CheckinApplicationNotFoundException, UploadCleaningImageException, CleaningImageTypeMismatchException, HouseNotFoundException, RoomNotFoundException, FirmEmployeeNotFoundException {
        return new ResponseEntity<>(firmEmployeeService.addServiceRecord(principal.getName(), host, request), HttpStatus.OK);
    }

    @GetMapping(value = "/service/record/get")
    public ResponseEntity<?> getFirmEmployeeServiceRecords(@RequestParam(value = "houseName") String houseName,
                                                           @RequestParam(value = "roomNumber", required = false, defaultValue = "0") Integer roomNumber,
                                                           @RequestParam(value = "yearAndMonth") String yearAndMonth,
                                                           Principal principal) throws ParseException {
        return new ResponseEntity<>(firmEmployeeService.getFirmEmployeeServiceRecord(principal.getName(), houseName, roomNumber, yearAndMonth), HttpStatus.OK);
    }

    @GetMapping(value = "/firm/name")
    public ResponseEntity<?> getFirmAndFirmEmployeeName(Principal principal) throws FirmNotFoundException, FirmEmployeeNotFoundException {
        return new ResponseEntity<>(firmEmployeeService.getFirmAndFirmEmployeeName(principal.getName()), HttpStatus.OK);
    }

}
