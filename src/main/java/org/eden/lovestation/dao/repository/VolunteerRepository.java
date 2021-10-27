package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.User;
import org.eden.lovestation.dao.model.Volunteer;
import org.eden.lovestation.dto.projection.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, String> {

    Optional<Volunteer> findByIdAndVerified(String id, Boolean verified);

    Optional<Volunteer> findByIdAndVerifiedIsNotNull(String id);

    @Query(value = "SELECT v.* FROM volunteers v",nativeQuery = true)
    List<VolunteerName> findAllName();

    @Query(value = "SELECT v.*, r.name AS role FROM volunteers v INNER JOIN roles r ON r.id = v.role_id WHERE "  +
        "v.account LIKE N'%' + ?1 + '%' OR " +
        "v.chinese_name LIKE N'%' + ?1 + '%' OR " +
        "v.english_name LIKE N'%' + ?1 + '%' OR " +
        "v.email LIKE N'%' + ?1 + '%' OR " +
        "v.birthday LIKE N'%' + ?1 + '%' OR " +
        "v.identity_card LIKE N'%' + ?1 + '%' OR " +
        "v.gender LIKE N'%' + ?1 + '%' OR " +
        "v.address LIKE N'%' + ?1 + '%' OR " +
        "v.phone LIKE N'%' + ?1 + '%' OR " +
        "v.cellphone LIKE N'%' + ?1 + '%'",
        countQuery = "SELECT count(v.*) FROM volunteers v INNER JOIN roles r ON r.id = v.role_id WHERE "  +
            "v.account LIKE N'%' + ?1 + '%' OR " +
            "v.chinese_name LIKE N'%' + ?1 + '%' OR " +
            "v.english_name LIKE N'%' + ?1 + '%' OR " +
            "v.email LIKE N'%' + ?1 + '%' OR " +
            "v.birthday LIKE N'%' + ?1 + '%' OR " +
            "v.identity_card LIKE N'%' + ?1 + '%' OR " +
            "v.gender LIKE N'%' + ?1 + '%' OR " +
            "v.address LIKE N'%' + ?1 + '%' OR " +
            "v.phone LIKE N'%' + ?1 + '%' OR " +
            "v.cellphone LIKE N'%' + ?1 + '%'",
        nativeQuery = true)
    Page<VolunteerDetail> findAllBySearchCondition(String keyword, Pageable pageable);

    @Query(value = "SELECT v.*, r.name AS role FROM volunteers v INNER JOIN roles r ON r.id = v.role_id WHERE "  +
        "v.chinese_name LIKE N'%' + ?1 + '%' OR " +
        "v.english_name LIKE N'%' + ?1 + '%' OR " +
        "v.email LIKE N'%' + ?1 + '%' OR " +
        "v.gender LIKE N'%' + ?1 + '%' OR " +
        "v.address LIKE N'%' + ?1 + '%' OR " +
        "v.phone LIKE N'%' + ?1 + '%' OR " +
        "v.cellphone LIKE N'%' + ?1 + '%'",
        countQuery = "SELECT count(v.*) FROM volunteers v INNER JOIN roles r ON r.id = v.role_id WHERE "  +
            "v.chinese_name LIKE N'%' + ?1 + '%' OR " +
            "v.english_name LIKE N'%' + ?1 + '%' OR " +
            "v.email LIKE N'%' + ?1 + '%' OR " +
            "v.gender LIKE N'%' + ?1 + '%' OR " +
            "v.address LIKE N'%' + ?1 + '%' OR " +
            "v.phone LIKE N'%' + ?1 + '%' OR " +
            "v.cellphone LIKE N'%' + ?1 + '%'",
        nativeQuery = true)
    Page<VolunteerContact> findAllContactBySearchCondition(String keyword, Pageable pageable);

    @Query(value = "SELECT v.*, r.name AS role FROM volunteers v INNER JOIN roles r ON r.id = v.role_id WHERE "  +
        "v.chinese_name LIKE N'%' + ?1 + '%' OR " +
        "v.english_name LIKE N'%' + ?1 + '%' OR " +
        "v.email LIKE N'%' + ?1 + '%' OR " +
        "v.gender LIKE N'%' + ?1 + '%' OR " +
        "v.address LIKE N'%' + ?1 + '%' OR " +
        "v.phone LIKE N'%' + ?1 + '%' OR " +
        "v.cellphone LIKE N'%' + ?1 + '%'",
        nativeQuery = true)
    List<VolunteerContact> findAllContactBySearchConditionWithoutPage(String keyword);

    Boolean existsByAccount(String account);

    Boolean existsByAccountAndVerifiedIsTrue(String account);

    Boolean existsByEmail(String email);

    Boolean existsByEmailAndVerifiedIsTrue(String email);

    Boolean existsByLineId(String lineId);

    Boolean existsByLineIdAndVerifiedIsTrue(String lineId);

    Boolean existsByIdentityCard(String identityCard);

    Boolean existsByIdentityCardAndVerifiedIsTrue(String identityCard);

    Optional<Volunteer> findByEmailAndVerified(String email, Boolean verified);

    Optional<Volunteer> findByAccountAndVerified(String account, Boolean verified);

    @Modifying
    @Query("update Volunteer v set v.password = ?1 where v.id = ?2")
    void updatePasswordById(String password, String id);

    @Modifying
    @Query("update Volunteer v set v.verified = ?1 where v.id = ?2")
    void updateVerificationById(Boolean verification, String id);

    Page<VolunteerRegisterVerification> findAllByVerifiedIsNull(Pageable pageable);
}
