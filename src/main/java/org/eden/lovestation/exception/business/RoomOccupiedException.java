package org.eden.lovestation.exception.business;

public class RoomOccupiedException extends Exception {
    public RoomOccupiedException() {
        super("此房間已有預約記錄(Room Already Occupied)");
    }
}
