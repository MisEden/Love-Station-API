package org.eden.lovestation.exception.business;

public class RoomStateChangeNotFoundException extends Exception{

    public RoomStateChangeNotFoundException() {
        super("無法根據條件取得變更申請(Room State change Not Found)");
    }

}
