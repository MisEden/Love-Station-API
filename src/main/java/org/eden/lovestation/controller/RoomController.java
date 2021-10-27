package org.eden.lovestation.controller;

import org.eden.lovestation.dao.model.Room;
import org.eden.lovestation.dto.request.RoomInfomationUpdate;
import org.eden.lovestation.exception.business.RepeatedRoomNumberException;
import org.eden.lovestation.exception.business.RoomNotFoundException;
import org.eden.lovestation.exception.business.RoomStateNotFoundException;
import org.eden.lovestation.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/rooms")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping(value = "/{id}/reservations/dates")
    public ResponseEntity<?> findAllReservationRoom(@PathVariable String id, @RequestParam(value = "date") String startDate) throws RoomNotFoundException, RoomStateNotFoundException {
        return new ResponseEntity<>(roomService.findAllOccupiedDateByRoomIdAndYear(id, startDate), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/name")
    public ResponseEntity<?> findRoomNameById(@PathVariable String id) throws RoomNotFoundException {
        return new ResponseEntity<>(roomService.findRoomInfoById(id), HttpStatus.OK);
    }

}
