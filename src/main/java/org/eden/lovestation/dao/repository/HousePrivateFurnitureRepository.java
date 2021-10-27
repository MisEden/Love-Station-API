package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.House;
import org.eden.lovestation.dto.projection.HousePrivateFurniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HousePrivateFurnitureRepository extends JpaRepository<org.eden.lovestation.dao.model.HousePrivateFurniture, String> {
    @Query(value = "select h.furniture_id AS privateFurnitureID, p.name AS privateFurnitureName, p.precedence AS privateFurniturePrecedence FROM house_private_furniture h INNER JOIN private_furniture p ON p.id=h.furniture_id WHERE h.room_id = ?1", nativeQuery = true)
    List<HousePrivateFurniture> findAllByRoom(@Param("roomId")String roomId);

    @Query(value = "SELECT h.* FROM house_private_furniture h INNER JOIN private_furniture p ON p.id=h.furniture_id WHERE p.id = ?1", nativeQuery = true)
    List<org.eden.lovestation.dao.model.HousePrivateFurniture> findAllByPrivateFurniture(String furnitureId);

    @Query(value = "SELECT h.* FROM house_private_furniture h INNER JOIN private_furniture p ON p.id=h.furniture_id INNER JOIN rooms r ON r.id=h.room_id  WHERE r.id = ?1 AND p.name = N''+?2", nativeQuery = true)
    org.eden.lovestation.dao.model.HousePrivateFurniture findByFurnitureName(String roomId, String furnitureName);
}
