package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.Admin;
import org.eden.lovestation.dto.projection.AdminAccountDetail;
import org.eden.lovestation.dto.projection.AdminRegisterVerification;
import org.eden.lovestation.dto.projection.FirmDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    Optional<Admin> findByAccount(String account);

    Optional<Admin> findByAccountAndVerified(String account, Boolean verified);

    List<AdminRegisterVerification> findAllByVerifiedIsNull();

    Optional<AdminRegisterVerification> findAllByIdAndVerifiedIsNull(String id);

    Optional<Admin> findByEmail(String email);

    Boolean existsByAccount(String account);

    Boolean existsByEmail(String email);

    Boolean existsByLineId(String lineId);

    @Modifying
    @Query("update Admin a set a.password = ?1 where a.id = ?2")
    void updatePasswordById(String password, String id);


    @Query(value = "SELECT a.*, r.name AS role FROM admins a INNER JOIN roles r ON r.id = a.role_id WHERE "  +
        "a.id != ?1 And " +
        "a.verified = 1 And " +
        "r.name LIKE N'%' + ?2 And ( " +
        "a.name LIKE N'%' + ?3 + '%' OR " +
        "a.email LIKE N'%' + ?3 + '%' ) ORDER BY r.name",
        countQuery = "SELECT count(a.*) FROM admins a INNER JOIN roles r ON r.id = a.role_id WHERE "  +
            "a.id != ?1 And " +
            "a.verified = 1 And " +
            "r.name LIKE N'%' + ?2 And ( " +
            "a.name LIKE N'%' + ?3 + '%' OR " +
            "a.email LIKE N'%' + ?3 + '%' )",
        nativeQuery = true)
    Page<AdminAccountDetail> findAllBySearchCondition(String id, String role, String keyword, Pageable pageable);
}
