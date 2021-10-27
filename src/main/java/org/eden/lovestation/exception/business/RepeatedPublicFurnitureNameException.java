package org.eden.lovestation.exception.business;

public class RepeatedPublicFurnitureNameException extends Exception {
    public RepeatedPublicFurnitureNameException() {
        super("此棧點家具名稱已存在於資料庫中了(Repeated Public Furniture)");
    }
}
