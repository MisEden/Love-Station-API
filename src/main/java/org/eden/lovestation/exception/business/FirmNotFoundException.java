package org.eden.lovestation.exception.business;

public class FirmNotFoundException extends Exception {
    public FirmNotFoundException() {
        super("無法根據條件取得廠商(Firm Not Found)");
    }
}
