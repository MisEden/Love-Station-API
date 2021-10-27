package org.eden.lovestation.dto.request;

import lombok.Data;

@Data
public class AdminUpdateReferral {
    private String hospitalChineseName;
    private String hospitalEnglishName;
    private String number;
    private String city;
}
