package org.eden.lovestation.controller;

import org.eden.lovestation.dto.request.SendCheckoutFeedbackRequest;
import org.eden.lovestation.dto.request.SendCheckoutInvestigationRequest;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.CheckinApplicationService;
import org.eden.lovestation.service.CheckoutApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/checkout-applications")
public class CheckoutController {

    private CheckoutApplicationService checkoutApplicationService;

    @Autowired
    public CheckoutController(CheckoutApplicationService checkoutApplicationService) {
        this.checkoutApplicationService = checkoutApplicationService;
    }

    @PostMapping(value = "/{roomStatesId}/investigation")
    public ResponseEntity<?> uploadInvestigation(@PathVariable String roomStatesId, @RequestBody SendCheckoutInvestigationRequest request) throws RoomStateNotFoundException{
        return new ResponseEntity<>(checkoutApplicationService.uploadCheckoutApplicationInvestigation(request, roomStatesId), HttpStatus.OK);
    }

    @GetMapping(value = "/{roomStateId}/investigation/isExisted")
    public ResponseEntity<?> checkUserInvestigationIsExisted(@PathVariable String roomStateId) throws RoomStateNotFoundException{
        return new ResponseEntity<>(checkoutApplicationService.checkInvestigationIsExisted(roomStateId), HttpStatus.OK);
    }

    @PostMapping(value = "/{roomStatesId}/feedback")
    public ResponseEntity<?> sendFeedback(@PathVariable String roomStatesId, @RequestBody SendCheckoutFeedbackRequest request) throws RoomStateNotFoundException{
        return new ResponseEntity<>(checkoutApplicationService.sendCheckoutApplicationFeedback(request, roomStatesId), HttpStatus.OK);
    }

    @GetMapping(value = "/{roomStateId}/feedback/isExisted")
    public ResponseEntity<?> checkUserFeedbackIsExisted(@PathVariable String roomStateId) throws RoomStateNotFoundException{
        return new ResponseEntity<>(checkoutApplicationService.checkFeedbackIsExisted(roomStateId), HttpStatus.OK);
    }

    @GetMapping(value = "/{roomStatesId}/daysOfStay")
    public ResponseEntity<?> getUserDaysOfStay(@PathVariable String roomStatesId) throws RoomNotFoundException{
        return new ResponseEntity<>(checkoutApplicationService.getDaysOfStay(roomStatesId), HttpStatus.OK);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<?> readyToCheckout(Principal principal) throws UserNotFoundException, RoomStateNotFoundException, CheckinApplicationNotFoundException, CheckinFormNotFoundException{
        return new ResponseEntity<>(checkoutApplicationService.checkStatusToCheckout(principal.getName()), HttpStatus.OK);
    }

}
