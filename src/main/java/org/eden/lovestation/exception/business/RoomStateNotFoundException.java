package org.eden.lovestation.exception.business;

public class RoomStateNotFoundException extends Exception {
    public RoomStateNotFoundException() {
        super("無法根據條件取得房間預約記錄(Room State Not Found)");
    }
}
