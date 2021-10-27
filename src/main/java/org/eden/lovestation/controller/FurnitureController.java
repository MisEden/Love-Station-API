package org.eden.lovestation.controller;

import org.eden.lovestation.dao.model.HousePrivateFurniture;
import org.eden.lovestation.dao.model.HousePublicFurniture;
import org.eden.lovestation.dao.repository.HousePrivateFurnitureRepository;
import org.eden.lovestation.dao.repository.HousePublicFurnitureRepository;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.HouseService;
import org.hibernate.sql.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/furniture")
public class FurnitureController {

    private final HouseService houseService;

    @Autowired
    public FurnitureController(HouseService houseService) {
        this.houseService = houseService;
    }


    @GetMapping(value = "public")
    public ResponseEntity<?> getPublicFurnitureByName(@RequestParam(value = "furnitureName")String furnitureName) throws FurnitureNotFoundException {
        return new ResponseEntity<>(houseService.findPublicFurnitureByName(furnitureName), HttpStatus.OK);
    }

    @GetMapping(value = "private")
    public ResponseEntity<?> getPrivateFurnitureByName(@RequestParam(value = "furnitureName")String furnitureName) throws FurnitureNotFoundException {
        return new ResponseEntity<>(houseService.findPrivateFurnitureByName(furnitureName), HttpStatus.OK);
    }

    @GetMapping(value = "public/all")
    public ResponseEntity<?> findAllPublicFurniture() {
        return new ResponseEntity<>(houseService.findAllPublicFurniture(), HttpStatus.OK);
    }

    @GetMapping(value = "private/all")
    public ResponseEntity<?> findAllPrivateFurniture() {
        return new ResponseEntity<>(houseService.findAllPrivateFurniture(), HttpStatus.OK);
    }

}
