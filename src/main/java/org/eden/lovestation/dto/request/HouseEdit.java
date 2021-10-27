package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class HouseEdit {
    @Size(min = 1, max = 200)
    private String city;

    @Size(min = 1, max = 200)
    private String name;


    private String introduction;

    @Min(1)
    private int squareFootage;

    @Size(min = 1, max = 200)
    private String roomLayout;

    private int totalFloor;

    @Size(min = 1, max = 200)
    private String roomDescription;

    @Size(min = 1, max = 200)
    private String style;

    @Size(min = 1, max = 200)
    private String feature;

    @Size(min = 1, max = 200)
    private String landlordId;

    @Size(min = 1, max = 200)
    private String traffic;

    @Size(min = 1, max = 200)
    private String address;

    @Size(min = 1, max = 200)
    private String nearHospital;

    @Size(min = 1, max = 200)
    private String lifeFunction;
}
