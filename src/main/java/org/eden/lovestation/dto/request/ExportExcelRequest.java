package org.eden.lovestation.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ExportExcelRequest {
    private List<String> title;
    private List<List<String>> data;
    private List<Integer> columnWidth;

    public ExportExcelRequest(List<String> title, List<List<String>> data, List<Integer> columnWidth) {
        this.title = title;
        this.data = data;
        this.columnWidth = columnWidth;
    }
}
