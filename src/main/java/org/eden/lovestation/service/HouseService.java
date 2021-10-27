package org.eden.lovestation.service;

import org.eden.lovestation.dao.model.House;
import org.eden.lovestation.dao.model.PrivateFurniture;
import org.eden.lovestation.dao.model.PublicFurniture;
import org.eden.lovestation.dao.model.Room;
import org.eden.lovestation.dto.projection.*;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.dto.response.HouseDetailPage;
import org.eden.lovestation.exception.business.*;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface HouseService {
    List<HouseName> findAllHouseNameWithoutDisable();

    List<PublicFurniture> findAllPublicFurniture();
    List<PrivateFurniture> findAllPrivateFurniture();

    List<HouseRoomId> findAllHouseRoomId(String houseId) throws HouseNotFoundException;
    List<HouseRoomId> findAllHouseRoomIdWithDisable(String houseId) throws HouseNotFoundException;

    HouseDetailPage findAllHouseDetail(int currentPage);

    HouseDetail findHouseById(String houseId) throws HouseNotFoundException;

    String findHouseNameById(String houseId) throws HouseNotFoundException;


    //Furniture api
    PublicFurniture findPublicFurnitureByName(String name) throws FurnitureNotFoundException;
    PrivateFurniture findPrivateFurnitureByName(String name) throws FurnitureNotFoundException;
    List<HousePublicFurniture> findAllHousePublicFurniture(String houseId) throws HouseNotFoundException;


}
