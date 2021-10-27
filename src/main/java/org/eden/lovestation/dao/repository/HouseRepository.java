package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.House;
import org.eden.lovestation.dao.model.PrivateFurniture;
import org.eden.lovestation.dao.model.PublicFurniture;
import org.eden.lovestation.dto.projection.FurnitureDetail;
import org.eden.lovestation.dto.projection.HouseDetail;
import org.eden.lovestation.dto.projection.HouseDetailWithoutImg;
import org.eden.lovestation.dto.projection.HouseName;
import org.eden.lovestation.exception.business.FurnitureNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, String> {

    Optional<House> findById(String id);

    Optional<House> findByName(String houseName);

    @Query(value = "SELECT h.* FROM houses h ORDER BY h.serial DESC", nativeQuery = true)
    List<HouseDetail> findAllSimpleDetail();

    List<House> findAllById(String houseId);

    @Query("select h.id as id, h.name as name from House h ORDER BY h.serial")
    List<HouseName> findAllHouseName();

    @Query("select h.id as id, h.name as name from House h WHERE h.disable = 0 ORDER BY h.serial")
    List<HouseName> findAllHouseNameWithoutDisable();

    List<HouseName> findAllByLandlordId(String landlordId);

    List<House> findByNameContains(String name);

    Page<HouseDetail> findAllByOrderBySerial(Pageable pageable);

//    List<HouseDetailWithoutImg> findAllByName(String Name);

    Optional<HouseDetail> findHouseById(String id);

    @Query(value = "SELECT COUNT(h.id) FROM houses h WHERE h.name = N'' + ?1", nativeQuery = true)
    int countByName(String houseName);

    @Query(value = "SELECT h.*, l.* FROM houses h INNER JOIN landlords l ON l.id = h.landlord_id WHERE h.name = N'' + ?1  ORDER BY h.serial DESC", nativeQuery = true)
    List<HouseDetail> findAllSimpleDetailByName(String houseName);
}
