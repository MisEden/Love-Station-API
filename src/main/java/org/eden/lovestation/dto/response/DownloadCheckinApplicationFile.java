package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.Resource;

import java.io.File;

@Data
@AllArgsConstructor
public class DownloadCheckinApplicationFile {
    private Resource resource;
}
