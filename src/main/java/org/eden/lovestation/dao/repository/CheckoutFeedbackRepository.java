package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.CheckoutFeedback;
import org.eden.lovestation.dao.model.RoomState;
import org.eden.lovestation.dto.projection.CheckoutFeedbackDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckoutFeedbackRepository extends JpaRepository<CheckoutFeedback, String> {

    List<CheckoutFeedbackDetail> findAllByRoomStates(RoomState roomState);

    Optional<CheckoutFeedback> findByRoomStates(RoomState roomState);

}
