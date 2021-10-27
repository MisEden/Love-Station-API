package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.FirmEmployees;
import org.eden.lovestation.dto.projection.FirmEmployeeName;
import org.eden.lovestation.dto.projection.FirmEmployeeRegisterVerification;
import org.eden.lovestation.dto.projection.FirmEmployeeServiceRecordDetailByAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FirmEmployeesRepository extends JpaRepository<FirmEmployees, String> {

    List<FirmEmployeeName> findAllByVerifiedIsNotNull();

    Optional<FirmEmployees> findByIdAndVerified(String id, Boolean verified);

    Optional<FirmEmployees> findByIdAndVerifiedIsNotNull(String id);

    Boolean existsByAccount(String account);

    Boolean existsByEmail(String email);

    Boolean existsByAccountAndVerifiedIsTrue(String account);

    Boolean existsByEmailAndVerifiedIsTrue(String email);

    Boolean existsByLineId(String lineId);

    Boolean existsByLineIdAndVerifiedIsTrue(String lineId);

    Boolean existsByIdentityCard(String identityCard);

    Boolean existsByIdentityCardAndVerifiedIsTrue(String identityCard);

    Optional<FirmEmployees> findByAccountAndVerified(String account, Boolean verified);

    Optional<FirmEmployees> findByEmailAndVerified(String email, Boolean verified);

    @Modifying
    @Query("update FirmEmployees f set f.password = ?1 where f.id = ?2")
    void updatePasswordById(String password, String id);

    @Modifying
    @Query("update FirmEmployees f set f.verified = ?1 where f.id = ?2")
    void updateVerificationById(Boolean verification, String id);

    @Query(value = "SELECT fe.*, f.name AS firmName " +
                    "FROM firm_employees AS fe INNER JOIN firms AS f ON fe.firm_id = f.id " +
                    "WHERE fe.verified IS NULL", nativeQuery = true)
    Page<FirmEmployeeRegisterVerification> findAllByVerifiedIsNull(Pageable pageable);

    @Query(value = "SELECT fs.*, fe.chinese_name AS firmEmployeeName, h.name AS houseName, r.number AS roomNumber, f.name AS firmName FROM firm_employee_service_record fs " +
        "INNER JOIN firm_employees fe ON fe.id = fs.firm_employee_id " +
        "INNER JOIN firms f ON f.id = fe.firm_id " +
        "INNER JOIN houses h ON h.id = fs.house_id " +
        "INNER JOIN rooms r ON r.id = fs.room_id WHERE " +
        "h.city like N'%' + ?1 + '%' AND " +
        "h.id like '%' + ?2 + '%' AND " +
        "r.id like '%' + ?3 + '%' AND " +
        "f.id like '%' + ?4 + '%' AND " +
        "fs.created_at >= ?5 AND " +
        "fs.created_at <= ?6 " +
        "ORDER BY h.city, fs.created_at DESC",
        nativeQuery = true)
    List<FirmEmployeeServiceRecordDetailByAdmin> findAllBySearchCondition(String houseCity, String houseId, String roomId, String firmId, Date startDate, Date endDate);


    @Query(value = "SELECT COUNT(vs.id) FROM volunteer_service_record AS vs WHERE viewed = N'未檢視' ",nativeQuery = true)
    int getCountNonView();
}
