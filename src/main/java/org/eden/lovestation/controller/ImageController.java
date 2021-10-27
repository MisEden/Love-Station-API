package org.eden.lovestation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping(value = "/image")
public class ImageController {

    @Autowired
    ServletContext context;

    @GetMapping(value = "/get")
    @ResponseBody
    public BufferedImage getImage() throws IOException {

        String houseFullDegreePanoramaFilePath = String.format(".%s%s.%s", "/storage/house-full-degree-panorama/", "test", "jpeg");
        Path houseFullDegreePanoramaPath = Paths.get(houseFullDegreePanoramaFilePath);

        return ImageIO.read(new FileInputStream(new File(houseFullDegreePanoramaFilePath)));
    }
}
