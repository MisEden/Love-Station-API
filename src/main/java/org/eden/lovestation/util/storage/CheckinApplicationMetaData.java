package org.eden.lovestation.util.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class CheckinApplicationMetaData {
    private String host;
    private MultipartFile rentImage;
    private MultipartFile affidavitImage;
}
