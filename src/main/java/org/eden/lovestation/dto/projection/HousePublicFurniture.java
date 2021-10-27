package org.eden.lovestation.dto.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface HousePublicFurniture {
    String getPublicFurnitureId();

    String getPublicFurnitureName();

    int getPublicFurniturePrecedence();
}
