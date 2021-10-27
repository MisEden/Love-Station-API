package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface CheckinApplicationBrief {

    @Value("#{target.id}")
    String getCheckinAppId();

    @Value("#{target.house.id}")
    String getHouseId();

    @Value("#{target.house.name}")
    String getHouse();

    @Value("#{target.room.number}")
    String getRoomNumber();

    @Value("#{target.roomState.id}")
    String getRoomStateId();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    @Value("#{target.roomState.startDate}")
    Date getStartDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    @Value("#{target.roomState.endDate}")
    Date getEndDate();

    @Value("#{target.user.chineseName}")
    String getChineseName();

    @Value("#{target.user.identityCard}")
    String getIdentityCard();

    @Value("#{target.user.gender}")
    String getGender();

    @Value("#{target.user.birthday}")
    String getBirthday();

    @Value("#{target.user.phone}")
    String getPhone();

    @Value("#{target.user.cellphone}")
    String getCellphone();

    @Value("#{target.referralEmployee.id}")
    String getReferralEmployeeId();

    @Value("#{target.referralEmployee.name}")
    String getReferralEmployeeName();

    @Value("#{target.volunteer != null ? target.volunteer.id : null}")
    String getVolunteerId();

    @Value("#{target.volunteer != null ? target.volunteer.chineseName : null}")
    String getVolunteerName();

    @Value("#{target.firmEmployees != null ? target.firmEmployees.id : null}")
    String getFirmEmployeeId();

    @Value("#{target.firmEmployees != null ? target.firmEmployees.chineseName : null}")
    String getFirmEmployeeName();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Taipei")
    Date getCreatedAt();

}
