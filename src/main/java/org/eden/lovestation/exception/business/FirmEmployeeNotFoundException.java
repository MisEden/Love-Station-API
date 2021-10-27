package org.eden.lovestation.exception.business;

public class FirmEmployeeNotFoundException extends Exception{
    public FirmEmployeeNotFoundException() {
        super("無法根據條件取得廠商人員(firm employee Not Found)");
    }

}
