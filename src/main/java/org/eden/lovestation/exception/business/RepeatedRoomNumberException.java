package org.eden.lovestation.exception.business;

public class RepeatedRoomNumberException extends Exception {
    public RepeatedRoomNumberException() {
        super("此房間號碼已存在於資料庫中了(Repeated Room Number)");
    }
}
