package org.eden.lovestation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Access;
import java.util.Date;

@Data
@AllArgsConstructor
public class NotificationDateResponse {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Taipei")
    private Date notificationDate;

}
