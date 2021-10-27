package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.VolunteerServiceRecord;
import org.eden.lovestation.dto.projection.VolunteerServiceRecordDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VolunteerServiceRecordRepository extends JpaRepository<VolunteerServiceRecord, String> {
    @Query(value = "SELECT vs.*, l.chinese_name AS volunteerName, r.number AS roomNumber, h.name AS houseName FROM volunteer_service_record vs " +
        "INNER JOIN volunteers l ON l.id = vs.volunteer_id " +
        "INNER JOIN houses h ON h.id = vs.house_id " +
        "INNER JOIN rooms r ON r.id = vs.room_id WHERE " +
        "h.city like N'%' + ?1 + '%' AND " +
        "h.id like '%' + ?2 + '%' AND " +
        "r.id like '%' + ?3 + '%' AND " +
        "l.id like '%' + ?4 + '%' AND " +
        "vs.created_at >= ?5 AND " +
        "vs.created_at <= ?6 " +
        "ORDER BY h.city, vs.created_at DESC",
        nativeQuery = true)
    List<VolunteerServiceRecordDetail> findAllBySearchCondition(String houseCity, String houseId, String roomId, String volunteerId, Date startDate, Date endDate);

    @Query(value = "SELECT vsr.*, v.chinese_name AS volunteerName, r.number AS roomNumber, h.name AS houseName " +
                    "FROM volunteer_service_record vsr INNER JOIN volunteers v ON v.id = vsr.volunteer_id INNER JOIN houses h ON h.id = vsr.house_id INNER JOIN rooms r ON r.id = vsr.room_id " +
                    "WHERE vsr.volunteer_id = ?1 " +
                    "AND vsr.created_at >= ?2 AND vsr.created_at <= ?3",
            nativeQuery = true)
    List<VolunteerServiceRecordDetail> findAllBySearchCondition(String volunteerId, Date startDate, Date endDate);

    @Query(value = "SELECT COUNT(vs.id) FROM volunteer_service_record AS vs WHERE vs.viewed = N'未檢視' ",nativeQuery = true)
    int getCountNonView();


    @Query(value = "SELECT vs.*, l.chinese_name AS volunteerName, r.number AS roomNumber, h.name AS houseName FROM volunteer_service_record vs " +
            "INNER JOIN volunteers l ON l.id = vs.volunteer_id " +
            "INNER JOIN houses h ON h.id = vs.house_id " +
            "INNER JOIN rooms r ON r.id = vs.room_id WHERE " +
            "vs.viewed = N'未檢視' " +
            "ORDER BY h.city, vs.created_at DESC",nativeQuery = true)
    List<VolunteerServiceRecordDetail> findAllNonView();
}
