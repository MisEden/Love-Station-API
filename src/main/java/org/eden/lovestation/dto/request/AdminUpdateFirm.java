package org.eden.lovestation.dto.request;

import lombok.Data;

@Data
public class AdminUpdateFirm {
    private String name;
    private String address;
    private String phone;
    private String contactPeople;
    private String contactTitle;
    private String contactPhone;
    private String contactEmail;
}
