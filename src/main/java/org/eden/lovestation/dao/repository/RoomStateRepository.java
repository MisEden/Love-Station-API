package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.House;
import org.eden.lovestation.dao.model.Room;
import org.eden.lovestation.dao.model.RoomState;
import org.eden.lovestation.dao.model.User;
import org.eden.lovestation.dto.projection.CheckinLocation;
import org.eden.lovestation.dto.projection.OccupiedRoomDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RoomStateRepository extends JpaRepository<RoomState, String>, JpaSpecificationExecutor<RoomState> {
    
    Optional<RoomState> findById(String id);

    Boolean existsByIdNotAndRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndDeletedIsFalse(String Id, Room room, Date endDate, Date startDate);

    Boolean existsByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndDeletedIsFalse(Room room, Date endDate, Date startDate);

    List<OccupiedRoomDate> findAllOccupiedRoomDateByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndDeleted(Room room, Date endDate, Date startDate, boolean deleted);

    @Query(value = "SELECT TOP(1) rs.*,r.id AS rid, r.number AS rnumber, h.id AS hid, h.name AS hname, rs.end_date AS edate FROM room_states rs INNER JOIN rooms r ON r.id=rs.room_id INNER JOIN houses h ON h.id=r.house_id INNER JOIN users u ON u.id=rs.user_id WHERE rs.start_date>= ?1 AND rs.start_date<= ?2 AND u.account like ?3", nativeQuery = true)
    CheckinLocation findAllByUserAndStartDateAndEndDate(Date startDateFilter, Date endDateFilter, String userAccount);

    @Modifying
    @Query("update RoomState r set r.room = ?1, r.startDate = ?2, r.endDate = ?3 where r.id = ?4")
    void updateRoomStateById(Room room, Date startDate, Date endDate, String id);

    @Modifying
    @Query("update RoomState r set r.state = ?1 where r.id = ?2")
    void updateStateById(String state, String id);

    List<RoomState> findAllByStartDateBetween(Date startDate, Date startDateEnd);

    List<RoomState> findAllByEndDateBetween(Date endDate, Date endDateEnd);

    Optional<RoomState> findByUser(User user);

    List<RoomState> findAllByStartDateBetweenOrEndDateBetween(Date startDate1, Date endDate1, Date startDate2, Date endDate2);

    List<RoomState> findAllByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndDeletedIsFalse(Room room, Date endDate, Date startDate);

    @Modifying
    @Query("update RoomState r set r.startDate = ?1, r.endDate = ?2 where r.id = ?3")
    void updateRoomStateDateTimeById(Date newStartDate, Date newEndDate, String roomStateId);

//    boolean existsByIdNotAndRoomAndDeletedIsFalseAndIn(String roomStateId, Room room, List<RoomState> roomStateList);
//
//    List<RoomState> findAllByStartDateBetweenOrEndDateBetweenAndIdNotAndRoomAndDeletedIsFalse(Date newStartDate1, Date newEndDate1, Date newStartDate2, Date newEndDate2, String roomStateId, Room room);

    @Query(value = "SELECT rs.* " +
                    "FROM room_states AS rs " +
                    "WHERE id != ?1 AND room_id = ?2 AND deleted = 0 AND ((start_date >= ?3 AND start_date <= ?4) OR (end_date >= ?3 AND end_date <= ?4))",
            countQuery = "SELECT count(*) " +
                            "FROM room_states AS rs " +
                            "WHERE id != ?1 AND room_id = ?2 AND deleted = 0 AND ((start_date >= ?3 AND start_date <= ?4) OR (end_date >= ?3 AND end_date <= ?4))",
            nativeQuery = true)
    List<RoomState> findAllBySearchCondition(String roomStateId, String roomId, Date newStartDate, Date newEndDate);

}
