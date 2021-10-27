package org.eden.lovestation.controller;

import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/houses")
public class HouseController {

    private final HouseService houseService;


    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping(value = "/get")
    public BufferedImage getImage() throws IOException {

        String houseFullDegreePanoramaFilePath = String.format(".%s%s.%s", "/storage/house-full-degree-panorama/", "test", "jpeg");
        Path houseFullDegreePanoramaPath = Paths.get(houseFullDegreePanoramaFilePath);

        return ImageIO.read(new FileInputStream(new File(houseFullDegreePanoramaFilePath)));
    }

    @GetMapping(value = "")
    public ResponseEntity<?> findAllHouseDetail(@RequestParam(value = "currentPage", required = false, defaultValue = "0") int currentPage) throws HouseNotFoundException {
        return new ResponseEntity<>(houseService.findAllHouseDetail(currentPage), HttpStatus.OK);
    }

    @GetMapping(value = "/names")
    public ResponseEntity<?> findAllHouseNameWithoutDisable() {
        return new ResponseEntity<>(houseService.findAllHouseNameWithoutDisable(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/room-numbers")
    public ResponseEntity<?> findAllHouseRoomId(@PathVariable String id) throws HouseNotFoundException {
        return new ResponseEntity<>(houseService.findAllHouseRoomId(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/room-numbers/all")
    public ResponseEntity<?> findAllHouseRoomIdWithDisable(@PathVariable String id) throws HouseNotFoundException {
        return new ResponseEntity<>(houseService.findAllHouseRoomIdWithDisable(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/detail")
    public ResponseEntity<?> findHouseDetailById(@PathVariable String id) throws HouseNotFoundException {
        return new ResponseEntity<>(houseService.findHouseById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/name")
    public ResponseEntity<?> findHouseNameById(@PathVariable String id) throws HouseNotFoundException {
        Map<String, String> houseName = new HashMap<String, String>();
        houseName.put("name", houseService.findHouseNameById(id));

        return new ResponseEntity<>(houseName, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/public-furniture")
    public ResponseEntity<?> findHousePublicFurnitureById(@PathVariable String id) throws HouseNotFoundException {
        return new ResponseEntity<>(houseService.findAllHousePublicFurniture(id), HttpStatus.OK);
    }

}
