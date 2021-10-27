package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.Landlord;
import org.eden.lovestation.dao.model.ReferralEmployee;
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
public interface LandlordRepository extends JpaRepository<Landlord, String> {

    Optional<Landlord> findById(String id);

    @Query(value = "SELECT l.*, r.name AS role FROM Landlords l INNER JOIN roles r ON r.id = l.role_id", nativeQuery = true)
    List<LandlordDetail> findAllDetail();

    @Query(value = "SELECT l.*, r.name AS role FROM Landlords l INNER JOIN roles r ON r.id = l.role_id WHERE "  +
        "l.account LIKE N'%' + ?1 + '%' OR " +
        "l.chinese_name LIKE N'%' + ?1 + '%' OR " +
        "l.english_name LIKE N'%' + ?1 + '%' OR " +
        "l.email LIKE N'%' + ?1 + '%' OR " +
        "l.birthday LIKE N'%' + ?1 + '%' OR " +
        "l.identity_card LIKE N'%' + ?1 + '%' OR " +
        "l.gender LIKE N'%' + ?1 + '%' OR " +
        "l.address LIKE N'%' + ?1 + '%' OR " +
        "l.phone LIKE N'%' + ?1 + '%' OR " +
        "l.cellphone LIKE N'%' + ?1 + '%'",
        countQuery = "SELECT count(r.*) FROM Landlords l INNER JOIN roles r ON r.id = l.role_id WHERE "  +
            "l.account LIKE N'%' + ?1 + '%' OR " +
            "l.chinese_name LIKE N'%' + ?1 + '%' OR " +
            "l.english_name LIKE N'%' + ?1 + '%' OR " +
            "l.email LIKE N'%' + ?1 + '%' OR " +
            "l.birthday LIKE N'%' + ?1 + '%' OR " +
            "l.identity_card LIKE N'%' + ?1 + '%' OR " +
            "l.gender LIKE N'%' + ?1 + '%' OR " +
            "l.address LIKE N'%' + ?1 + '%' OR " +
            "l.phone LIKE N'%' + ?1 + '%' OR " +
            "l.cellphone LIKE N'%' + ?1 + '%'",
        nativeQuery = true)
    Page<LandlordDetail> findAllBySearchCondition(String keyword, Pageable pageable);

    @Query(value = "SELECT DISTINCT l.*, r.name AS role," +
        "    STUFF((" +
        "            SELECT convert(nvarchar(36),h.id) + ','" +
        "            FROM houses h" +
        "            WHERE h.landlord_id = l.id" +
        "            FOR XML PATH('')" +
        "            ), 1, 0, '') AS housesId," +
        "    STUFF((" +
        "            SELECT h.name + ','" +
        "            FROM houses h" +
        "            WHERE h.landlord_id = l.id" +
        "            FOR XML PATH('')" +
        "            ), 1, 0, '') AS housesName " +
        "FROM Landlords l INNER JOIN roles r ON r.id = l.role_id ",
        nativeQuery = true)
    List<LandlordContact> findAllContactBySearchConditionWithoutPage();

    @Query(value = "select l.id as landlordId, l.chinese_name as landlordName from Landlords l", nativeQuery = true)
    List<LandlordName> findAllLandlordNames();

    Optional<Landlord> findByIdAndVerified(String account, Boolean verified);

    Optional<Landlord> findByIdAndVerifiedIsNotNull(String id);

    Boolean existsByAccount(String account);

    Boolean existsByAccountAndVerifiedIsTrue(String account);

    Boolean existsByEmail(String email);

    Boolean existsByEmailAndVerifiedIsTrue(String email);

    Boolean existsByIdentityCardAndVerifiedIsTrue(String identityCard);

    Boolean existsByLineId(String lineId);

    Boolean existsByLineIdAndVerifiedIsTrue(String lineId);

    Boolean existsByIdentityCard(String identityCard);

    Optional<Landlord> findByAccountAndVerified(String account, Boolean verified);

    Optional<Landlord> findByEmailAndVerified(String email, Boolean verified);

    @Modifying
    @Query("update Landlord l set l.password = ?1 where l.id = ?2")
    void updatePasswordById(String password, String id);

    @Modifying
    @Query("update Landlord l set l.verified = ?1 where l.id = ?2")
    void updateVerificationById(Boolean verification, String id);

    Page<LandlordRegisterVerification> findAllByVerifiedIsNull(Pageable pageable);

}
