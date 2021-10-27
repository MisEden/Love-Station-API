package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.CheckinForm;
import org.eden.lovestation.dao.model.RoomState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckinFormRepository extends JpaRepository<CheckinForm, String> {
    Optional<CheckinForm> findById(String Id);

    List<CheckinForm> findByRoomStateId(String roomStateId);

    Optional<CheckinForm> findByRoomState(RoomState roomState);
}
