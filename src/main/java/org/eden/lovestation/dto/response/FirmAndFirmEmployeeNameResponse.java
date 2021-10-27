package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FirmAndFirmEmployeeNameResponse {

    private String firmId;

    private String firmName;

    private String firmEmployeeId;

    private String firmEmployeeName;

}
