package org.eden.lovestation.dto.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface HousePrivateFurniture {
    String getPrivateFurnitureId();

    String getPrivateFurnitureName();

    int getPrivateFurniturePrecedence();
}
