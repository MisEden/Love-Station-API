package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface CheckinLocation {
    String getId();

    String getEdate();

    //the 'ID/Number' column's nickname of rooms table which is join with room_state
    String getRid();
    int getRnumber();

    //the 'ID/Name' column's nickname of houses table which is join with room
    String getHid();
    String getHname();

}
