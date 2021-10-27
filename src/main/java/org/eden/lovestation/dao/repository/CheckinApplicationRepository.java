package org.eden.lovestation.dao.repository;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dto.projection.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CheckinApplicationRepository extends JpaRepository<CheckinApplication, String>, JpaSpecificationExecutor<CheckinApplication> {

    Optional<CheckinApplication> findById(String id);

    Optional<CheckinApplicationDetail> findDetailById(String id);

    Optional<CheckinApplication> findByIdAndFirstVerifiedIsNotNull(String id);

    Optional<CheckinApplication> findByIdAndSecondVerifiedIsNotNull(String id);

    Page<CheckinApplicationDetail> findAllByUserAndFirstVerifiedIsNullAndSecondVerifiedIsNull(User user, Pageable pageable);

    Page<CheckinApplicationDetail> findAllByReferralEmployeeAndFirstVerifiedIsNullAndSecondVerifiedIsNull(ReferralEmployee referralEmployee, Pageable pageable);

    Page<CheckinApplicationDetail> findAllByFirstVerifiedIsNullAndSecondVerifiedIsNull(Admin admin, Pageable pageable);

    Page<CheckinApplicationDetail> findAllByFirstVerifiedIsNullAndSecondVerifiedIsNull(Pageable pageable);

    Page<CheckinApplicationDetail> findAllByUserAndFirstVerifiedIsNotNullAndSecondVerifiedIsNotNull(User user, Pageable pageable);

    Page<CheckinApplicationDetail> findAllByReferralEmployeeAndFirstVerifiedIsNotNullAndSecondVerifiedIsNotNull(ReferralEmployee referralEmployee, Pageable pageable);

    Page<CheckinApplicationDetail> findAllByFirstVerifiedIsNotNullAndSecondVerifiedIsNotNull(Pageable pageable);

    Page<CheckinApplicationDetail> findAllByUserAndFirstVerifiedIsNotNullAndSecondVerifiedIsNullAndRentImageIsNotNullAndAffidavitImageIsNotNull(User user, Pageable pageable);

    Page<CheckinApplicationDetail> findAllByReferralEmployeeAndFirstVerifiedIsNotNullAndSecondVerifiedIsNullAndRentImageIsNotNullAndAffidavitImageIsNotNull(ReferralEmployee referralEmployee, Pageable pageable);

    Page<CheckinApplicationDetail> findAllByRentImageIsNotNullAndAffidavitImageIsNotNullAndFirstVerifiedIsNotNullAndSecondVerifiedIsNullOrSecondVerifiedIsFalse(Pageable pageable);

    Page<CheckinApplicationDetail> findAllByUser(User user, Pageable pageable);

    Page<CheckinApplicationDetail> findAllByReferralEmployee(ReferralEmployee referralEmployee, Pageable pageable);

    Page<CheckinApplicationDetail> findAllBy(Pageable pageable);

    CheckinApplicationDetail findByIdAndUser(String id, User user);

    CheckinApplicationDetail findByIdAndReferralEmployee(String id, ReferralEmployee referralEmployee);

    @Query(value = "SELECT " +
            "c.id as id, c.user_id as userId, c.caregiver_name as careGiverName, c.rent_image as rentImage, c.affidavit_image as affidavitImage, " +
            "u.identity_card as userIdentityCard, u.gender as userGender, u.cellphone as userCellphone, u.birthday as userBirthday, " +
            "u.chinese_name as userName, rs.start_date as startDate, rs.end_date as endDate, rs.deleted as deleted, r.number as roomNumber, " +
            "h.name as houseName, re.name as referralEmployeeName, rf.hospital_chinese_name as referralHospitalName " +
//            "rsc.changed_item as changeItem, rsc.admin_verified " +
            "FROM checkin_applications AS c INNER JOIN users AS u ON u.id = c.user_id " +
            "INNER JOIN room_states AS rs ON rs.id = c.room_state_id " +
            "INNER JOIN rooms AS r ON r.id = c.room_id " +
            "INNER JOIN houses AS h ON h.id = c.house_id " +
            "INNER JOIN referral_employees AS re ON re.id = referral_employee_id " +
            "INNER JOIN referrals AS rf ON re.referral_id = rf.id " +
//            "LEFT JOIN room_state_changes AS rsc ON (c.room_state_id = rsc.room_state_id AND rsc.changed_item = N'取消入住' AND rsc.admin_verified = 1) " +
            "WHERE c.admin_id IS NOT null AND (?1 = '' OR (u.chinese_name = ?1)) AND ?2 <= rs.start_date AND ?3 >= rs.start_date AND (?4 = 0 OR r.number = ?4) AND h.name = (N'' + ?5)",
            countQuery = "SELECT count(*) " +
                    "FROM checkin_applications AS c INNER JOIN users AS u ON u.id = c.user_id " +
                    "INNER JOIN room_states AS rs ON rs.id = c.room_state_id " +
                    "INNER JOIN rooms AS r ON r.id = c.room_id " +
                    "INNER JOIN houses AS h ON h.id = c.house_id " +
                    "INNER JOIN referral_employees AS re ON re.id = referral_employee_id " +
                    "INNER JOIN referrals AS rf ON re.referral_id = rf.id " +
//                    "LEFT JOIN room_state_changes AS rsc ON (c.room_state_id = rsc.room_state_id AND rsc.changed_item = N'取消入住' AND rsc.admin_verified = 1) " +
                    "WHERE c.admin_id IS NOT null AND (?1 = '' OR (u.chinese_name = ?1)) AND ?2 <= rs.start_date AND ?3 >= rs.start_date AND (?4 = 0 OR r.number = ?4) AND h.name = (N'' + ?5)",
            nativeQuery = true)
    Page<AdminCheckinApplicationSearchResult> findAllBySearchCondition(@Param("chineseName") String chineseName, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("roomNumber") Integer roomNumber, @Param("houseName") String houseName, Pageable pageable);

    @Query(value = "SELECT c.id as id, rs.start_date as startDate, rs.end_date as endDate, u.chinese_name as userName, r.number as roomNumber, h.name as houseName, c.first_verified as firstVerified, c.first_verified_reason as firstVerifiedReason, c.second_verified as secondVerified " +
        "FROM checkin_applications AS c INNER JOIN users AS u ON u.id = c.user_id INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id INNER JOIN referral_employees AS re ON re.id = referral_employee_id INNER JOIN referrals AS rf ON re.referral_id = rf.id " +
        "WHERE c.admin_id IS NOT null AND (?1 = '' OR (u.chinese_name = ?1)) AND ?2 <= rs.start_date AND ?3 >= rs.start_date AND (?4 = 0 OR r.number = ?4) AND h.name like (N'%' + ?5 + '%') AND (?6 = '' OR c.first_verified = ?6) AND (?7 = '' OR c.second_verified = ?7) " +
        "ORDER BY startDate DESC",
        nativeQuery = true)
    List<CheckinApplicationID> findAllBySearchCondition_allHouse_ID(@Param("chineseName") String chineseName, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("roomNumber") Integer roomNumber, @Param("houseName") String houseName, String first, String second);

    @Query(value = "SELECT " +
        "c.id as id, c.user_id as userId, c.caregiver_name as careGiverName, c.rent_image as rentImage, c.affidavit_image as affidavitImage, " +
        "u.identity_card as userIdentityCard, u.gender as userGender, u.cellphone as userCellphone, u.birthday as userBirthday, " +
        "u.chinese_name as userName, rs.start_date as startDate, rs.deleted as deleted, rs.end_date as endDate, r.number as roomNumber, " +
//            "rsc.changed_item as changeItem, rsc.admin_verified, " +
            "h.name as houseName, re.name as referralEmployeeName, rf.hospital_chinese_name as referralHospitalName , c.first_verified as firstVerified, c.first_verified_reason as firstVerifiedReason, c.second_verified as secondVerified " +
        "FROM checkin_applications AS c INNER JOIN users AS u ON u.id = c.user_id INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id INNER JOIN referral_employees AS re ON re.id = referral_employee_id INNER JOIN referrals AS rf ON re.referral_id = rf.id " +
//            "LEFT JOIN room_state_changes AS rsc ON (c.room_state_id = rsc.room_state_id AND rsc.changed_item = N'取消入住' AND rsc.admin_verified = 1) " +
            "WHERE c.admin_id IS NOT null AND (?1 = '' OR (u.chinese_name = ?1)) AND ?2 <= rs.start_date AND ?3 >= rs.start_date AND (?4 = 0 OR r.number = ?4) AND h.name like (N'%' + ?5 + '%') AND (?6 = '' OR c.first_verified = ?6) AND (?7 = '' OR c.second_verified = ?7) " +
        "ORDER BY startDate DESC",
        countQuery = "SELECT count(*) " +
            "FROM checkin_applications AS c INNER JOIN users AS u ON u.id = c.user_id INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id INNER JOIN referral_employees AS re ON re.id = referral_employee_id INNER JOIN referrals AS rf ON re.referral_id = rf.id " +
//                "LEFT JOIN room_state_changes AS rsc ON (c.room_state_id = rsc.room_state_id AND rsc.changed_item = N'取消入住' AND rsc.admin_verified = 1) " +
                "WHERE c.admin_id IS NOT null AND (?1 = '' OR (u.chinese_name = ?1)) AND ?2 <= rs.start_date AND ?3 >= rs.start_date AND (?4 = 0 OR r.number = ?4) AND h.name like (N'%' + ?5 + '%') AND (?6 = '' OR c.first_verified = ?6) AND (?7 = '' OR c.second_verified = ?7)",
        nativeQuery = true)
    Page<AdminCheckinApplicationSearchResult> findAllBySearchCondition_allHouse(@Param("chineseName") String chineseName, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("roomNumber") Integer roomNumber, @Param("houseName") String houseName, String first, String second, Pageable pageable);

    @Query(value = "SELECT c.id as id, c.rent_image as rentImage, c.first_verified as firstVerified, c.second_verified as secondVerified, u.chinese_name as userName, rs.start_date as startDate, rs.end_date as endDate, h.name as houseName FROM checkin_applications as c, users as u, room_states as rs, houses as h " +
            "where c.user_id = ?1 and c.house_id = h.id and c.room_state_id = rs.id and c.user_id = u.id" +
            " and exists (select 1 from room_states as rs where c.room_state_id = rs.id and ?2 <= rs.start_date and ?3 >= rs.start_date)" +
            " and exists (select 1 from houses h where c.house_id = h.id and h.name = (N'' + ?4))",
            countQuery = "SELECT count(*) FROM checkin_applications as c, users as u, room_states as rs, houses as h " +
                    "where c.user_id = ?1 and c.house_id = h.id and c.room_state_id = rs.id and c.user_id = u.id" +
                    " and exists (select 1 from room_states as rs where c.room_state_id = rs.id and ?2 <= rs.start_date and ?3 >= rs.start_date)" +
                    " and exists (select 1 from houses h where c.house_id = h.id and h.name = (N'' + ?4))",
            nativeQuery = true)
    Page<UserCheckinApplicationSearchResult> findAllBySearchCondition(@Param("userId") String userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("houseName") String houseName, Pageable pageable);

    @Query(value = "SELECT c.id as id, c.rent_image as rentImage, c.first_verified as firstVerified, c.second_verified as secondVerified, u.chinese_name as userName, rs.start_date as startDate, rs.end_date as endDate, h.name as houseName " +
                    "FROM checkin_applications as c, users as u, room_states as rs, houses as h " +
                    "WHERE c.user_id = ?1 AND c.room_state_id = rs.id AND c.user_id = u.id AND c.house_id = h.id " +
                    "AND EXISTS (SELECT 1 " +
                                "FROM room_states as rs where c.room_state_id = rs.id AND ?2 <= rs.start_date AND ?3 >= rs.start_date) ",
            countQuery = "SELECT count(*) " +
                            "FROM checkin_applications as c, users as u, room_states as rs, houses as h " +
                            "WHERE c.user_id = ?1 AND c.room_state_id = rs.id AND c.user_id = u.id AND c.house_id = h.id " +
                            "AND EXISTS (SELECT 1 " +
                                        "FROM room_states as rs where c.room_state_id = rs.id AND ?2 <= rs.start_date AND ?3 >= rs.start_date)",
            nativeQuery = true)
    Page<UserCheckinApplicationSearchResult> findAllBySearchCondition(String userId, Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT c.id as id, c.rent_image as rentImage, c.first_verified as firstVerified, c.second_verified as secondVerified, u.chinese_name as userName, rs.start_date as startDate, rs.end_date as endDate, h.name as houseName FROM checkin_applications as c, users as u, room_states as rs, houses as h " +
            "where c.referral_employee_id = ?1 and c.house_id = h.id and c.room_state_id = rs.id and c.user_id = u.id" +
            " and exists (select 1 from users as u where (?2 = '' or (c.user_id = u.id and u.chinese_name like N'%' + ?2 + '%')))" +
            " and exists (select 1 from room_states as rs where c.room_state_id = rs.id and ?3 <= rs.start_date and ?4 >= rs.start_date)" +
            " and exists (select 1 from houses h where c.house_id = h.id and h.name = (N'' + ?5))",
            countQuery = "SELECT count(*) FROM checkin_applications as c, users as u, room_states as rs, houses as h " +
                    "where c.referral_employee_id = ?1 and c.house_id = h.id and c.room_state_id = rs.id and c.user_id = u.id" +
                    " and exists (select 1 from users as u where (?2 = '' or (c.user_id = u.id and u.chinese_name like N'%' + ?2 + '%')))" +
                    " and exists (select 1 from room_states as rs where c.room_state_id = rs.id and ?3 <= rs.start_date and ?4 >= rs.start_date)" +
                    " and exists (select 1 from houses h where c.house_id = h.id and h.name = (N'' + ?5))",
            nativeQuery = true)
    Page<ReferralEmployeeCheckinApplicationSearchResult> findAllBySearchCondition(@Param("referralEmployeeId") String referralEmployeeId, @Param("chineseName") String chineseName, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("houseName") String houseName, Pageable pageable);

    @Query(value = "SELECT c.id as checkinAppId, h.name as houseName, r.number as roomNumber, u.chinese_name as userName, c.caregiver_name as careGiverName, rs.start_date as startDate, rs.end_date as endDate "
                    + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                    + "WHERE h.landlord_id IN ( SELECT l.id as landlordId "
                                                + "FROM landlords as l "
                                                + "WHERE l.id = ?1 )"
                    + "AND h.name = (N'' + ?2) AND (r.number = ?3 OR ?3 = 0)"
                    + "AND rs.start_date >= ?4 AND rs.start_date <= ?5",
            countQuery = "SELECT count(*) "
                        + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                        + "WHERE h.landlord_id IN ( SELECT l.id as landlordId "
                                                    + "FROM landlords as l "
                                                    + "WHERE l.id = ?1 ) "
                        + "AND h.name = (N'' + ?2) AND (r.number = ?3 OR ?3 = 0)"
                        + "AND rs.start_date >= ?4 AND rs.start_date <= ?5",
            nativeQuery = true)
    Page<LandlordCheckinApplicationSearchResult> findAllBySearchCondition(String landlordId, String houseName, Integer roomNumber, Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT c.id as checkinAppId, h.name as houseName, r.number as roomNumber, u.chinese_name as userName, c.caregiver_name as careGiverName, rs.start_date as startDate, rs.end_date as endDate "
                    + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                    + "WHERE h.name = (N'' + ?1) AND (r.number = ?2 OR ?2 = 0) "
                    + "AND rs.start_date >= ?3 AND rs.start_date <= ?4",
            countQuery = "SELECT count(*) "
                        + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                        + "WHERE h.name = (N'' + ?1) AND (r.number = ?2 OR ?2 = 0) "
                        + "AND rs.start_date >= ?3 AND rs.start_date <= ?4",
            nativeQuery = true)
    Page<VolunteerCheckinApplicationSearchResult> findAllBySearchCondition(String houseName, Integer roomNumber, Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT c.id as checkinAppId, h.name as houseName, r.number as roomNumber, u.chinese_name as userName, c.caregiver_name as careGiverName, rs.start_date as startDate, rs.end_date as endDate "
                    + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                    + "WHERE rs.start_date >= ?1 AND rs.start_date <= ?2 "
                    + "AND h.name = (N'' + ?3) AND (r.number = ?4 OR ?4 = 0)",
            countQuery = "SELECT count(*) "
                        + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                        + "WHERE rs.start_date >= ?1 AND rs.start_date <= ?2 "
                        + "AND h.name = (N'' + ?3) AND (r.number = ?4 OR ?4 = 0)",
            nativeQuery = true)
    Page<FirmEmployeeCheckinApplicationSearchResult> findAllBySearchCondition(Date startDate, Date endDate, String houseName, Integer roomNumber, Pageable pageable);

    @Query(value = "SELECT c.* "
                    + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                    + "WHERE h.name = (N'' + ?1) AND r.number = ?2 AND rs.start_date >= ?3 AND rs.start_date <= ?4 "
                    + "AND EXISTS ( SELECT 1 "
                                    + "FROM landlord_service_record as lsr "
                                    + "WHERE lsr.landlord_id = ?5 ) ",
            nativeQuery = true)
    CheckinApplication findAllBySearchCondition(String houseName, Integer roomNumber, Date startDate, Date endDate, String landlordId);

    @Query(value = "SELECT c.*, rs.start_date AS startDate, rs.end_date AS endDate" +
                            ", u.chinese_name as userName, u.gender AS userGender, u.birthday AS userBirthday, u.identity_card AS userIdentityCard, u.cellphone AS userCellphone, u.address AS userAddress " +
                            ", h.name as houseName, r.number as roomNumber " +
                            ", rsc.new_start_date AS newStartDate, rsc.new_end_date AS newEndDate, rsc.created_at AS roomStateChangeDate, rsc.changed_item AS changedItem, rsc.reason AS reason " +
                            ", rf.hospital_chinese_name AS referralName, rfe.name AS referralEmployeeName, rfe.cellphone AS referralEmployeeCellphone, rft.name AS referralEmployeeTitle "
                    + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN room_state_changes AS rsc ON rs.id = rsc.room_state_id " +
                                                    "INNER JOIN referral_employees AS rfe ON c.referral_employee_id = rfe.id " +
                                                    "INNER JOIN referrals AS rf ON rfe.referral_id = rf.id " +
                                                    "INNER JOIN referral_titles AS rft ON rfe.referral_title_id = rft.id " +
                                                    "INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                    + "WHERE c.referral_employee_id = ?1 AND (rsc.referral_verified != 1 OR (rsc.referral_verified IS NULL)) "
                    + "AND EXISTS ( SELECT 1 "
                                    + "FROM room_state_changes AS rsc "
                                    + "WHERE c.room_state_id = rsc.room_state_id ) ",
//                                    + "AND rsc.referral_verified != 1 OR rsc.referral_verified IS NULL ) ",
            countQuery = "SELECT count(*) "
                        + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN room_state_changes AS rsc ON rs.id = rsc.room_state_id " +
                                                        "INNER JOIN referral_employees AS rfe ON c.referral_employee_id = rfe.id " +
                                                        "INNER JOIN referrals AS rf ON rfe.referral_id = rf.id " +
                                                        "INNER JOIN referral_titles AS rft ON rfe.referral_title_id = rft.id " +
                                                        "INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                        + "WHERE c.referral_employee_id = ?1 AND (rsc.referral_verified != 1 OR (rsc.referral_verified IS NULL)) "
                        + "AND EXISTS ( SELECT 1 "
                                        + "FROM room_state_changes AS rsc "
                                        + "WHERE c.room_state_id = rsc.room_state_id )",
            nativeQuery = true)
    Page<CheckinApplicationWithRoomStateChangeDetail> findAllBySearchCondition(String referralEmployeeId, Pageable pageable);

    @Query(value = "SELECT c.id AS checkinAppId, c.created_at AS created_at" +
                            ", rsc.new_start_date AS newStartDate, rsc.new_end_date AS newEndDate, rsc.created_at AS roomStateChangeDate, rsc.changed_item AS changedItem, rsc.reason AS reason " +
                            ", rf.hospital_chinese_name AS referralName, rfe.name AS referralEmployeeName, rft.name AS referralEmployeeTitle "
                    + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN room_state_changes AS rsc ON rs.id = rsc.room_state_id " +
                                                    "INNER JOIN referral_employees AS rfe ON c.referral_employee_id = rfe.id " +
                                                    "INNER JOIN referrals AS rf ON rfe.referral_id = rf.id " +
                                                    "INNER JOIN referral_titles AS rft ON rfe.referral_title_id = rft.id " +
                                                    "INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                    + "WHERE rsc.referral_verified = 1 AND (rsc.admin_verified != 1 OR (rsc.admin_verified IS NULL)) "
                    + "AND EXISTS ( SELECT 1 "
                                    + "FROM room_state_changes AS rsc "
                                    + "WHERE c.room_state_id = rsc.room_state_id )",
            countQuery = "SELECT count(*) "
                        + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN room_state_changes AS rsc ON rs.id = rsc.room_state_id " +
                                                        "INNER JOIN referral_employees AS rfe ON c.referral_employee_id = rfe.id " +
                                                        "INNER JOIN referrals AS rf ON rfe.referral_id = rf.id " +
                                                        "INNER JOIN referral_titles AS rft ON rfe.referral_title_id = rft.id " +
                                                        "INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                        + "WHERE rsc.referral_verified = 1 AND (rsc.admin_verified != 1 OR (rsc.admin_verified IS NULL)) "
                        + "AND EXISTS ( SELECT 1 "
                                        + "FROM room_state_changes AS rsc "
                                        + "WHERE c.room_state_id = rsc.room_state_id )",
            nativeQuery = true)
    Page<CheckinApplicationWithRoomStateChangeInfoAdminNeed> findAllBySearchCondition(Pageable pageable);

    @Query(value = "SELECT c.*, rs.start_date AS startDate, rs.end_date AS endDate" +
                            ", u.chinese_name as userName, u.gender AS userGender, u.birthday AS userBirthday, u.identity_card AS userIdentityCard, u.cellphone AS userCellphone, u.address AS userAddress " +
                            ", h.name as houseName, r.number as roomNumber " +
                            ", rsc.new_start_date AS newStartDate, rsc.new_end_date AS newEndDate, rsc.created_at AS roomStateChangeDate, rsc.changed_item AS changedItem, rsc.reason AS reason " +
                            ", rf.hospital_chinese_name AS referralName, rfe.name AS referralEmployeeName, rfe.cellphone AS referralEmployeeCellphone, rft.name AS referralEmployeeTitle "
                    + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id INNER JOIN room_state_changes AS rsc ON rs.id = rsc.room_state_id " +
                                                    "INNER JOIN referral_employees AS rfe ON c.referral_employee_id = rfe.id " +
                                                    "INNER JOIN referrals AS rf ON rfe.referral_id = rf.id " +
                                                    "INNER JOIN referral_titles AS rft ON rfe.referral_title_id = rft.id " +
                                                    "INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                    + "WHERE c.id = ?1 AND rsc.referral_verified = 1 AND (rsc.admin_verified != 1 OR (rsc.admin_verified IS NULL)) "
                    + "AND EXISTS ( SELECT 1 "
                                    + "FROM room_state_changes AS rsc "
                                    + "WHERE c.room_state_id = rsc.room_state_id )",
//                                    + "AND rsc.referral_verified != 1 OR rsc.referral_verified IS NULL ) ",
            nativeQuery = true)
    CheckinApplicationWithRoomStateChangeDetail findBySearchCondition(String checkinAppId);

    @Query(value = "SELECT c.*, rs.start_date AS startDate, rs.end_date AS endDate, cf.created_at AS checkinActualTime" +
                            ", u.chinese_name AS chineseName, u.phone AS phone, u.cellphone AS cellphone" +
                            ", h.name as house, r.number as room "
                    + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id " +
                                                    "LEFT OUTER JOIN checkin_forms AS cf ON cf.room_state_id = rs.id " +
                                                    "INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                    + "WHERE rs.start_date >= ?1 AND rs.start_date <= ?2 AND rs.deleted != 1",
            countQuery = "SELECT count(*) "
                        + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id " +
                                                        "LEFT OUTER JOIN checkin_forms AS cf ON cf.room_state_id = rs.id " +
                                                        "INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                        + "WHERE rs.start_date >= ?1 AND rs.start_date <= ?2 AND rs.deleted != 1",
            nativeQuery = true)
    Page<CheckinInfoAdminNeed> findAllScheduledCheckin(Date date, Date dateEnd, Pageable pageable);

    @Query(value = "SELECT c.*, rs.start_date AS startDate, rs.end_date AS endDate, cf.created_at AS checkoutActualTime" +
                            ", u.chinese_name AS chineseName, u.phone AS phone, u.cellphone AS cellphone " +
                            ", h.name as house, r.number as room "
                    + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id " +
                                                    "LEFT OUTER JOIN checkout_feedback AS cf ON cf.room_state_id = rs.id " +
                                                    "INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                    + "WHERE rs.end_date >= ?1 AND rs.end_date <= ?2 AND rs.deleted != 1",
            countQuery = "SELECT count(*) "
                        + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id " +
                                                        "LEFT OUTER JOIN checkout_feedback AS cf ON cf.room_state_id = rs.id " +
                                                        "INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                        + "WHERE rs.end_date >= ?1 AND rs.end_date <= ?2 AND rs.deleted != 1",
            nativeQuery = true)
    Page<CheckoutInfoAdminNeed> findAllScheduledCheckout(Date date, Date dateEnd, Pageable pageable);

    @Query(value = "SELECT DISTINCT c.id AS checkinAppId, rs.id AS roomStateId, rs.start_date AS startDate, rs.end_date AS endDate" +
                                            ", u.chinese_name AS chineseName" +
                                            ", h.id AS houseId, h.name AS house, r.number AS roomNumber "
                    + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id " +
                                                    "LEFT OUTER JOIN room_state_changes AS rsc ON rsc.room_state_id = rs.id " +
                                                    "LEFT OUTER JOIN checkout_feedback AS cf ON cf.room_state_id = rs.id " +
                                                    "INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                    + "WHERE c.user_id = ?1 AND c.first_verified = 1 AND c.second_verified = 1 AND cf.room_state_id IS NULL " +
                                            "AND (rsc.room_state_id IS NULL OR NOT EXISTS(SELECT 1 FROM room_state_changes AS rsc WHERE rsc.referral_verified = 0 OR admin_verified = 0))",
            countQuery = "SELECT count(*) "
                    + "FROM checkin_applications AS c INNER JOIN room_states AS rs ON rs.id = c.room_state_id " +
                                                    "LEFT OUTER JOIN room_state_changes AS rsc ON rsc.room_state_id = rs.id " +
                                                    "LEFT OUTER JOIN checkout_feedback AS cf ON cf.room_state_id = rs.id " +
                                                    "INNER JOIN users AS u ON u.id = c.user_id INNER JOIN rooms AS r ON r.id = c.room_id INNER JOIN houses AS h ON h.id = c.house_id "
                    + "WHERE c.user_id = ?1 AND c.first_verified = 1 AND c.second_verified = 1 AND cf.room_state_id IS NULL " +
                                            "AND (rsc.room_state_id IS NULL OR NOT EXISTS(SELECT 1 FROM room_state_changes AS rsc WHERE rsc.referral_verified = 0 OR admin_verified = 0))",
            nativeQuery = true)
    List<CheckinApplicationBeforeCheckoutBrief> findAllBeforeCheckout(String userId);

    Page<CheckinApplicationBrief> findAllByVolunteerDateIsNullAndFirmEmployeeDateIsNullAndSecondVerifiedAndRoomStateIn(boolean secondVerified, List<RoomState> roomStateList, Pageable pageable);

    List<CheckinApplicationBrief> findAllByVolunteerIdAndRoomStateIn(String volunteerId, List<RoomState> roomStateList);

    List<CheckinApplicationBrief> findAllByFirmEmployeesIdAndRoomStateIn(String firmEmployeeId, List<RoomState> roomStateList);

    CheckinApplicationBrief findFirstByFirstVerifiedIsTrueAndSecondVerifiedIsTrueAndUserOrderByCreatedAtDesc(User user);

    CheckinApplicationDetail findFirstByFirstVerifiedIsNotNullAndSecondVerifiedIsNotNullAndUserOrderByCreatedAtDesc(User user);

    @Modifying
    @Query("update CheckinApplication c set c.firstVerified = ?1, c.firstVerifiedReason = ?2, c.admin = ?3, c.adminDate = ?4 where c.id = ?5")
    void updateFirstStageStatusById(Boolean firstVerified, String firstVerfiedReason, Admin admin, Date adminDate, String id);

    @Modifying
    @Query("update CheckinApplication c set c.firstVerified = ?1, c.firstVerifiedReason = ?2, c.room = ?3, c.house = ?4, c.admin = ?5, c.adminDate = ?6 where c.id = ?7")
    void updateFirstStageStatusAndChangeRoomAndHouseById(Boolean firstVerified, String firstVerifiedReason, Room room, House house, Admin admin, Date adminDate, String id);

    @Modifying
    @Query("update CheckinApplication c set c.secondVerified = ?1 where c.id = ?2")
    void updateSecondStageStatusById(Boolean secondVerified, String id);

    @Modifying
    @Query("update CheckinApplication c set c.rentImage = ?1, c.affidavitImage = ?2 where c.id = ?3 and c.user = ?4")
    void updateRentImageAndAffidavitImageById(String rentImage, String affidavitImage, String id, User user);

    Optional<CheckinApplication> findByRoomState(RoomState roomState);

    List<CheckinInfoAdminNeed> findAllByRoomStateIn(List<RoomState> roomStateDetailList);

    List<CheckinApplicationForHouse> findAllByHouseAndSecondVerifiedAndRoomStateIn(House house, boolean secondVerified, List<RoomState> roomStateList);

    CheckinApplication findByRoom(Room room);

    List<OccupiedRoomDate> findAllBySecondVerifiedAndFirstVerifiedAndRoomStateIn(Boolean secondVerified, Boolean firstVerified, List<RoomState> roomStateList);

    List<CheckinInfoAdminNeed> findAllByRoomStateInAndFirstVerifiedAndSecondVerified(List<RoomState> roomStateDetailList, Boolean firstVerified, Boolean secondVerified);

}
