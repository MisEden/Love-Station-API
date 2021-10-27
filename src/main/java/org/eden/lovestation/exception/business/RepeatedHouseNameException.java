package org.eden.lovestation.exception.business;

public class RepeatedHouseNameException extends Exception {
    public RepeatedHouseNameException() {
        super("此棧點名稱已存在於資料庫(Repeated House Name)");
    }
}
