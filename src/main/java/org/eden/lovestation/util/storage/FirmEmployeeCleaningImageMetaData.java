package org.eden.lovestation.util.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class FirmEmployeeCleaningImageMetaData {

    private String host;
    private MultipartFile beforeImage;
    private MultipartFile afterImage;

}
