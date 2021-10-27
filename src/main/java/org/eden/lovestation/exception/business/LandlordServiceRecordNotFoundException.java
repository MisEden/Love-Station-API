package org.eden.lovestation.exception.business;

public class LandlordServiceRecordNotFoundException extends Exception{

    public LandlordServiceRecordNotFoundException() {
        super("無法根據條件取得房東服務紀錄(Landlord Service Record Not Found)");
    }

}
