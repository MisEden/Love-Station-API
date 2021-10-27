package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.CheckinForm;
import org.eden.lovestation.dao.model.CheckinFormPublicFurniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CheckinFormPublicFurnitureRepository extends JpaRepository<CheckinFormPublicFurniture,String> {
    List<CheckinFormPublicFurniture> findAllByCheckinForm(CheckinForm checkinForm);

    @Query(value = "SELECT c.* FROM checkin_form_public_furniture c INNER JOIN public_furniture f ON f.id = c.furniture_id WHERE f.id = ?1", nativeQuery = true)
    List<CheckinFormPublicFurniture> findAllByPublicFurniture(String furnitureId);
}
