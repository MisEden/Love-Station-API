package org.eden.lovestation.exception.business;

public class RepeatedPrivateFurnitureNameException extends Exception {
    public RepeatedPrivateFurnitureNameException() {
        super("此房間家具名稱已存在於資料庫中了(Repeated Private Furniture)");
    }
}
