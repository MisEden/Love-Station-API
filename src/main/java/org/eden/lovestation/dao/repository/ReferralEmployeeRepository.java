package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.ReferralEmployee;
import org.eden.lovestation.dto.projection.ReferralEmployeeDetail;
import org.eden.lovestation.dto.projection.ReferralEmployeeRegisterVerification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReferralEmployeeRepository extends JpaRepository<ReferralEmployee, String> {
    Optional<ReferralEmployee> findByIdAndVerified(String id, Boolean verified);

    Optional<ReferralEmployee> findByIdAndVerifiedIsNotNull(String id);

    Optional<ReferralEmployee> findByAccountAndVerified(String account, Boolean verified);

    Optional<ReferralEmployee> findByEmailAndVerified(String email, Boolean verified);

    @Query("select r.id as id, r.name as referralEmployeeName, r.referral.hospitalChineseName as referralHospitalName, r.referralTitle.name as referralTitleName, r.cellphone as referralEmployeeCellphone from ReferralEmployee r where r.id = ?1")
    Optional<ReferralEmployeeDetail> findDetailById(String id);

    Boolean existsByAccount(String account);

    Boolean existsByAccountAndVerifiedIsTrue(String account);

    Boolean existsByLineId(String lineId);

    Boolean existsByLineIdAndVerifiedIsTrue(String lineId);

    @Modifying
    @Query("update ReferralEmployee r set r.password = ?1 where r.id = ?2")
    void updatePasswordById(String password, String id);

    Boolean existsByEmail(String email);

    Boolean existsByEmailAndVerifiedIsTrue(String email);

    @Modifying
    @Query("update ReferralEmployee r set r.verified = ?1 where r.id = ?2")
    void updateVerificationById(Boolean verification, String id);

    Page<ReferralEmployeeRegisterVerification> findAllByVerifiedIsNull(Pageable pageable);

    @Modifying
    @Query("update ReferralEmployee r set r.lineId = ?1 where r.id = ?2")
    void updateLineIdById(String newLineId, String id);
}

