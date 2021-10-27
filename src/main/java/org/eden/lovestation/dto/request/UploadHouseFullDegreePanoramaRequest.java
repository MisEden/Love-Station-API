package org.eden.lovestation.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class UploadHouseFullDegreePanoramaRequest {
    @NotNull
    private MultipartFile houseFullDegreePanorama;
}
