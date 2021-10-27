package org.eden.lovestation.exception.business;

public class VolunteerServiceRecordNotFoundException extends Exception{

    public VolunteerServiceRecordNotFoundException() {
        super("無法根據條件取得志工服務回報(volunteer service record Not Found)");
    }
}
