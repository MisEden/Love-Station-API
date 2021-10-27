package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.House;
import org.eden.lovestation.dto.projection.HousePublicFurniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HousePublicFurnitureRepository extends JpaRepository<org.eden.lovestation.dao.model.HousePublicFurniture, String> {
    @Query(value = "select h.furniture_id AS publicFurnitureID, p.name AS publicFurnitureName, p.precedence AS publicFurniturePrecedence from house_public_furniture h INNER JOIN public_furniture p ON p.id=h.furniture_id INNER JOIN houses o ON o.id=h.house_id WHERE o.id = ?1", nativeQuery = true)
    List<HousePublicFurniture> findAllByHouse(@Param("houseId")String houseId);


    @Query(value = "select h.* FROM house_public_furniture h INNER JOIN public_furniture p ON p.id=h.furniture_id WHERE p.id = ?1", nativeQuery = true)
    List<org.eden.lovestation.dao.model.HousePublicFurniture> findAllByPublicFurniture(String furnitureId);

    @Query(value = "SELECT h.* FROM house_public_furniture h INNER JOIN public_furniture p ON p.id=h.furniture_id INNER JOIN houses o ON o.id=h.house_id  WHERE o.id = ?1 AND p.name = N''+?2", nativeQuery = true)
    org.eden.lovestation.dao.model.HousePublicFurniture findByFurnitureName(String houseId, String furnitureName);
}
