package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.CheckinApplication;
import org.eden.lovestation.dao.model.Room;
import org.eden.lovestation.dao.model.RoomState;
import org.eden.lovestation.dao.repository.CheckinApplicationRepository;
import org.eden.lovestation.dao.repository.RoomRepository;
import org.eden.lovestation.dao.repository.RoomStateRepository;
import org.eden.lovestation.dto.projection.OccupiedRoomDate;
import org.eden.lovestation.exception.business.CheckinApplicationNotFoundException;
import org.eden.lovestation.exception.business.RepeatedRoomNumberException;
import org.eden.lovestation.exception.business.RoomNotFoundException;
import org.eden.lovestation.exception.business.RoomStateNotFoundException;
import org.eden.lovestation.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomStateRepository roomStateRepository;
    private final CheckinApplicationRepository checkinApplicationRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository,
                           RoomStateRepository roomStateRepository,
                           CheckinApplicationRepository checkinApplicationRepository) {
        this.roomRepository = roomRepository;
        this.roomStateRepository = roomStateRepository;
        this.checkinApplicationRepository = checkinApplicationRepository;
    }

    @Override
    public List<OccupiedRoomDate> findAllOccupiedDateByRoomIdAndYear(String roomId, String startDate_string) throws RoomNotFoundException, RoomStateNotFoundException {

        //get the room info by roomId
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);

        //divide the string of start date
        String[] startDate_array = startDate_string.split("-");

        System.out.println(room.getId());

        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar cal = Calendar.getInstance(timeZone);
        cal.set(Calendar.YEAR, Integer.parseInt(startDate_array[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(startDate_array[1])-1);
        cal.set(Calendar.DATE, Integer.parseInt(startDate_array[2]));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        Date startDate = cal.getTime();

        // add 1 year to start date
        cal.add(Calendar.YEAR, 1);
        Date endDate = cal.getTime();

//        List<RoomState> roomStateList = roomStateRepository.findAllByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndDeletedIsFalse(room, endDate, startDate);
//
//        List<OccupiedRoomDate> occupiedRoomDateList = checkinApplicationRepository.findAllBySecondVerifiedAndFirstVerifiedAndRoomStateIn(true, true, roomStateList);

        return roomStateRepository.findAllOccupiedRoomDateByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndDeleted(room, endDate, startDate, false);
//        return occupiedRoomDateList;

    }

    @Override
    public Map<String, Object> findRoomInfoById(String roomId) throws RoomNotFoundException{
        Map<String, Object> roomInfo = new HashMap<>();

        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);

        roomInfo.put("houseName", room.getHouse().getName());
        roomInfo.put("roomNumber", room.getNumber());
        roomInfo.put("disable", room.isDisable());

        return  roomInfo;
    }
}
