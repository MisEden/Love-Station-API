package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.PrivateFurniture;
import org.eden.lovestation.dto.projection.FurnitureDetail;
import org.eden.lovestation.exception.business.FurnitureNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivateFurnitureRepository extends JpaRepository<PrivateFurniture, String> {
//    Optional<PrivateFurniture> findPrivateFurnitureByName(String name)
    Optional<PrivateFurniture> findByName(String name) throws FurnitureNotFoundException;


    @Query(value = "SELECT COUNT(p.id) FROM private_furniture p WHERE p.name = N'' + ?1", nativeQuery = true)
    int countByName(String name);

}
