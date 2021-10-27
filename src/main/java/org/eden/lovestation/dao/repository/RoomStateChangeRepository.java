package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.RoomState;
import org.eden.lovestation.dao.model.RoomStateChange;
import org.eden.lovestation.dto.projection.CheckinApplicationWithRoomStateChangeDetail;
import org.eden.lovestation.dto.projection.RoomStateChangeDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomStateChangeRepository extends JpaRepository<RoomStateChange, String>{

    Page<RoomStateChangeDetail> findAllByRoomState(RoomState roomState, Pageable pageable);

    Optional<RoomStateChange> findByRoomState(RoomState roomState);

    Optional<RoomStateChange> findByRoomStateAndReferralVerifiedAndAdminVerified(RoomState roomState, boolean referralVerified, boolean adminVerified);

    @Modifying
    @Query(value = "UPDATE room_state_changes SET room_state_changes.new_start_date = ?1, room_state_changes.new_end_date = ?2, room_state_changes.reason = ?3 WHERE room_state_changes.room_state_id = ?4", nativeQuery = true)
    void updateRoomStateChangeByRoomState(Date newStartDate, Date newEndDate, String reason, String roomStateId);

    @Query(value = "SELECT * from room_state_changes AS rsc WHERE rsc.room_state_id = ?1 AND rsc.admin_verified = ?2", nativeQuery = true)
    List<RoomStateChange> findByRoomStateAndAdminVerified(String roomStateId, boolean adminVerified);

    boolean existsByRoomStateAndAdminVerifiedIsFalse(RoomState roomState);

}
