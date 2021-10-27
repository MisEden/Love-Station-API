package org.eden.lovestation.exception.business;

public class FirmServiceRecordNotFoundException extends Exception{

    public FirmServiceRecordNotFoundException() {
        super("無法根據條件取得廠商清潔回報(Firm Service Record Not Found)");
    }
}
