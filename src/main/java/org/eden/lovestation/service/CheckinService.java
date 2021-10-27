package org.eden.lovestation.service;

import org.eden.lovestation.dao.model.CheckinForm;
import org.eden.lovestation.dto.projection.CheckinLocation;
import org.eden.lovestation.dto.projection.HousePrivateFurniture;
import org.eden.lovestation.dto.projection.HousePublicFurniture;
import org.eden.lovestation.dto.request.CheckinCheckRequest;
import org.eden.lovestation.exception.business.*;

import java.text.ParseException;
import java.util.List;

public interface CheckinService {
    CheckinLocation findUserCheckinLocation(String checkDate, String userId)throws UserNotFoundException, ParseException;

    int countCheckinForm(String roomStateId) throws RoomStateNotFoundException;

    //Get Furniture List
    List<HousePublicFurniture> findAllHousePublicFurniture(String roomId) throws RoomNotFoundException;
    List<HousePrivateFurniture> findAllHousePrivateFurniture(String roomId) throws RoomNotFoundException;


    CheckinForm saveCheckinResult(CheckinCheckRequest request, String userId) throws UserNotFoundException,RoomStateNotFoundException, FurnitureNotFoundException, RoomNotFoundException, HouseNotFoundException;
}
