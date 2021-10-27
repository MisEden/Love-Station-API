package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.CheckinForm;
import org.eden.lovestation.dao.model.CheckinFormPrivateFurniture;
import org.eden.lovestation.dao.model.CheckinFormPublicFurniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CheckinFormPrivateFurnitureRepository extends JpaRepository<CheckinFormPrivateFurniture,String> {
    List<CheckinFormPrivateFurniture> findAllByCheckinForm(CheckinForm checkinForm);

    @Query(value = "SELECT c.* FROM checkin_form_private_furniture c INNER JOIN private_furniture f ON f.id = c.furniture_id WHERE f.id = ?1", nativeQuery = true)
    List<CheckinFormPrivateFurniture> findAllByPrivateFurniture(String furnitureId);
}
