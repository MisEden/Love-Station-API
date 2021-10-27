package org.eden.lovestation.exception.business;

public class RepeatedFirmNameException extends Exception {
    public RepeatedFirmNameException() {
        super("此合作廠商的名稱已存在於資料庫(Repeated Firm Name)");
    }
}
