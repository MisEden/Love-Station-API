package org.eden.lovestation.util.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class HouseFullDegreePanoramaMetaData {
    private String host;
    private MultipartFile houseFullDegreePanorama;
}
