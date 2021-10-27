package org.eden.lovestation.service;

import org.eden.lovestation.dao.model.Room;
import org.eden.lovestation.dto.projection.OccupiedRoomDate;
import org.eden.lovestation.exception.business.RepeatedRoomNumberException;
import org.eden.lovestation.exception.business.RoomNotFoundException;
import org.eden.lovestation.exception.business.RoomStateNotFoundException;

import java.util.List;
import java.util.Map;

public interface RoomService {

    List<OccupiedRoomDate> findAllOccupiedDateByRoomIdAndYear(String roomId, String startDate) throws RoomNotFoundException, RoomStateNotFoundException;

    Map<String, Object> findRoomInfoById(String roomId) throws RoomNotFoundException;
}
