package org.eden.lovestation.exception.business;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException() {
        super("無法根據條件取得房間(Room Not Found)");
    }
}
