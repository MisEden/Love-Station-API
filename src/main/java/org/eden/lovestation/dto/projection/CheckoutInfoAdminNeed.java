package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface CheckoutInfoAdminNeed {

    @Value("#{target.id}")
    String getCheckinAppId();

    String getHouse();

    String getRoom();

    @Value("#{target.room_state_id}")
    String getRoomStateId();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    @Value("#{target.startDate}")
    Date getStartDate();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="Asia/Taipei")
    Date getEndDate();

    String getChineseName();

    String getPhone();

    String getCellphone();

    @Value("#{target.referral_employee_id}")
    String getReferralEmployeeId();

    @Value("#{target.caregiver_name}")
    String getCaregiverName();

    @Value("#{target.caregiver_phone}")
    String getCaregiverPhone();

    @Value("#{target.checkout_notification_date}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Taipei")
    Date getNotificationDate();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Taipei")
    Date getCheckoutActualTime();

}
