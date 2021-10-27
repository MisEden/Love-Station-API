package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.Referral;
import org.eden.lovestation.dto.projection.AdminCheckinApplicationSearchResult;
import org.eden.lovestation.dto.projection.ReferralDetail;
import org.eden.lovestation.dto.projection.ReferralSearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, String> {
    List<ReferralDetail> findAllBy();

    @Query(value = "SELECT r.*, r.hospital_chinese_name AS chinese, r.hospital_english_name AS english FROM referrals r WHERE "  +
        "r.hospital_chinese_name LIKE N'%' + ?1 + '%' OR " +
        "r.hospital_english_name LIKE N'%' + ?1 + '%' OR " +
        "r.number LIKE N'%' + ?1 + '%' OR " +
        "r.city LIKE N'%' + ?1 + '%'",
        countQuery = "SELECT count(*) FROM referrals r WHERE " +
        "r.hospital_chinese_name LIKE N'%' + ?1 + '%' OR " +
        "r.hospital_english_name LIKE N'%' + ?1 + '%' OR " +
        "r.number LIKE N'%' + ?1 + '%' OR " +
        "r.city LIKE N'%' + ?1 + '%'",
        nativeQuery = true)
    Page<ReferralSearchResult> findAllBySearchCondition(String keyword, Pageable pageable);

    @Query(value = "SELECT r.*, r.hospital_chinese_name AS chinese, r.hospital_english_name AS english FROM referrals r WHERE "  +
        "r.hospital_chinese_name LIKE N'%' + ?1 + '%' OR " +
        "r.hospital_english_name LIKE N'%' + ?1 + '%' OR " +
        "r.number LIKE N'%' + ?1 + '%' OR " +
        "r.city LIKE N'%' + ?1 + '%'",
        nativeQuery = true)
    List<ReferralSearchResult> findAllBySearchConditionWithoutPage(String keyword);

    @Query(value = "SELECT COUNT(r.id) FROM referrals r WHERE r.hospital_chinese_name = N'' + ?1", nativeQuery = true)
    int countByHospitalChineseName(String name);

    @Query(value = "SELECT COUNT(r.id) FROM referrals r WHERE r.hospital_english_name = N'' + ?1", nativeQuery = true)
    int countByHospitalEnglishName(String name);

    @Query(value = "SELECT COUNT(r.id) FROM referrals r WHERE r.number = N'' + ?1", nativeQuery = true)
    int countByReferralNumber(String number);
}
