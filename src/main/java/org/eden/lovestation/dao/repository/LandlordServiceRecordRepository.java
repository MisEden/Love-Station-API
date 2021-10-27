package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.LandlordServiceRecord;
import org.eden.lovestation.dto.projection.LandlordServiceRecordDetail;
import org.eden.lovestation.dto.projection.LandlordRecordDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LandlordServiceRecordRepository extends JpaRepository<LandlordServiceRecord, String> {

    List<LandlordRecordDetail> findAllBy();

    @Query(value = "SELECT ls.*, l.chinese_name AS landlordName, r.number AS roomNumber, h.name AS houseName FROM landlord_service_record ls " +
        "INNER JOIN landlords l ON l.id = ls.landlord_id " +
        "INNER JOIN houses h ON h.id = ls.house_id " +
        "INNER JOIN rooms r ON r.id = ls.room_id WHERE " +
        "h.city like N'%' + ?1 + '%' AND " +
        "h.id like '%' + ?2 + '%' AND " +
        "r.id like '%' + ?3 + '%' AND " +
        "l.id like '%' + ?4 + '%' AND " +
        "ls.created_at >= ?5 AND " +
        "ls.created_at <= ?6 " +
        "ORDER BY h.city, ls.created_at DESC",
        nativeQuery = true)
    List<LandlordServiceRecordDetail> findAllBySearchCondition(String houseCity, String houseId, String roomId, String landlordId, Date startDate, Date endDate);

    List<LandlordRecordDetail> findAllByCheckinApplicationId(String checkinAppId);


    @Query(value = "SELECT COUNT(ls.id) FROM landlord_service_record AS ls WHERE ls.viewed = N'未檢視' ",nativeQuery = true)
    int getCountNonView();

    @Query(value = "SELECT ls.*, l.chinese_name AS landlordName, r.number AS roomNumber, h.name AS houseName FROM landlord_service_record AS ls " +
            "INNER JOIN landlords l ON l.id = ls.landlord_id " +
            "INNER JOIN houses h ON h.id = ls.house_id " +
            "INNER JOIN rooms r ON r.id = ls.room_id " +
            "WHERE ls.viewed = N'未檢視' " +
            "ORDER BY h.city, ls.created_at DESC",nativeQuery = true)
    List<LandlordServiceRecordDetail> findAllNonView();
}
