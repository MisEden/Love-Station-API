package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.Firm;
import org.eden.lovestation.dto.projection.FirmDetail;
import org.eden.lovestation.dto.projection.FirmName;
import org.eden.lovestation.dto.projection.LandlordName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirmRepository extends JpaRepository<Firm, String> {

    @Query(value = "SELECT f.id as firmId, f.name as firmName FROM firms f", nativeQuery = true)
    List<FirmName> findAllFirmNames();

    @Query(value = "SELECT f.* FROM firms f WHERE "  +
        "f.name LIKE N'%' + ?1 + '%' OR " +
        "f.address LIKE N'%' + ?1 + '%' OR " +
        "f.phone LIKE N'%' + ?1 + '%' OR " +
        "f.contact_people LIKE N'%' + ?1 + '%' OR " +
        "f.contact_title LIKE N'%' + ?1 + '%' OR " +
        "f.contact_phone LIKE N'%' + ?1 + '%' OR " +
        "f.contact_email LIKE N'%' + ?1 + '%'",
        countQuery = "SELECT count(*) FROM firms f WHERE "  +
            "f.name LIKE N'%' + ?1 + '%' OR " +
            "f.address LIKE N'%' + ?1 + '%' OR " +
            "f.phone LIKE N'%' + ?1 + '%' OR " +
            "f.contact_people LIKE N'%' + ?1 + '%' OR " +
            "f.contact_title LIKE N'%' + ?1 + '%' OR " +
            "f.contact_phone LIKE N'%' + ?1 + '%' OR " +
            "f.contact_email LIKE N'%' + ?1 + '%'",
        nativeQuery = true)
    Page<FirmDetail> findAllBySearchCondition(String keyword, Pageable pageable);

    @Query(value = "SELECT f.* FROM firms f WHERE "  +
        "f.name LIKE N'%' + ?1 + '%' OR " +
        "f.address LIKE N'%' + ?1 + '%' OR " +
        "f.phone LIKE N'%' + ?1 + '%' OR " +
        "f.contact_people LIKE N'%' + ?1 + '%' OR " +
        "f.contact_title LIKE N'%' + ?1 + '%' OR " +
        "f.contact_phone LIKE N'%' + ?1 + '%' OR " +
        "f.contact_email LIKE N'%' + ?1 + '%'",
        nativeQuery = true)
    List<FirmDetail> findAllBySearchConditionWithoutPage(String keyword);

    @Query(value = "SELECT COUNT(f.id) FROM firms f WHERE f.name = N'' + ?1", nativeQuery = true)
    int countByName(String name);
}
