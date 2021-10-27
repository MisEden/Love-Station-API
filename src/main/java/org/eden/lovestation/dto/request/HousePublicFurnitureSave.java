package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class HousePublicFurnitureSave {
    private String houseId;
    private String furnitureName;
}
