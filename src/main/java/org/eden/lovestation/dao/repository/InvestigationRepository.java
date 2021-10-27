package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.Investigation;
import org.eden.lovestation.dao.model.RoomState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestigationRepository extends JpaRepository<Investigation, String> {
    Investigation findByRoomStates(RoomState roomState);

    Optional<Investigation> findByRoomStatesId(String roomStateId);
}
