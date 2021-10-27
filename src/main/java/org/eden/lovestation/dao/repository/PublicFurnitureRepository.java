package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.PublicFurniture;
import org.eden.lovestation.dto.projection.FurnitureDetail;
import org.eden.lovestation.exception.business.FurnitureNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicFurnitureRepository extends JpaRepository<PublicFurniture, String> {
//    Optional<PublicFurniture> findPublicFurnitureByName(String name) throws FurnitureNotFoundException;
//    Optional<FurnitureDetail> findByName(String name) throws FurnitureNotFoundException;

    Optional<PublicFurniture> findByName(String name) throws FurnitureNotFoundException;

    @Query(value = "SELECT COUNT(p.id) FROM public_furniture p WHERE p.name = N'' + ?1", nativeQuery = true)
    int countByName(String name);

    @Query(value = "SELECT p.* FROM public_furniture p ORDER BY p.precedence", nativeQuery = true)
    List<PublicFurniture> findAll();
}
