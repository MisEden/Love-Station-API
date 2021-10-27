package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.FirmEmployeeServiceRecord;
import org.eden.lovestation.dto.projection.FirmEmployeeServiceRecordDetail;
import org.eden.lovestation.dto.projection.FirmEmployeeServiceRecordDetailByAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FirmEmployeeServiceRecordRepository extends JpaRepository<FirmEmployeeServiceRecord, String> {

    @Query(value = "SELECT fesr.*, fe.chinese_name AS firmEmployeeName, r.number AS roomNumber, h.name AS houseName " +
                    "FROM firm_employee_service_record fesr INNER JOIN firm_employees fe ON fe.id = fesr.firm_employee_id INNER JOIN houses h ON h.id = fesr.house_id INNER JOIN rooms r ON r.id = fesr.room_id " +
                    "WHERE fesr.firm_employee_id = ?1 " +
                    "AND fesr.created_at >= ?2 AND fesr.created_at <= ?3 " +
                    "AND h.name = (N'' + ?4) AND (r.number = ?5 OR ?5 = 0)",
            nativeQuery = true)
    List<FirmEmployeeServiceRecordDetail> findAllBySearchCondition(String firmEmployeeId, Date startDate, Date endDate, String houseName, Integer roomNumber);


    @Query(value = "SELECT COUNT(fs.id) FROM firm_employee_service_record AS fs WHERE fs.viewed = N'未檢視' ",nativeQuery = true)
    int getCountNonView();

    @Query(value = "SELECT fesr.*, fe.chinese_name AS firmEmployeeName, f.name AS firmName, r.number AS roomNumber, h.name AS houseName " +
            "FROM firm_employee_service_record fesr INNER JOIN firm_employees fe ON fe.id = fesr.firm_employee_id INNER JOIN houses h ON h.id = fesr.house_id INNER JOIN rooms r ON r.id = fesr.room_id INNER JOIN firms f ON f.id = fe.firm_id " +
            "WHERE fesr.viewed = N'未檢視'",nativeQuery = true)
    List<FirmEmployeeServiceRecordDetailByAdmin> findAllNonView();
}
