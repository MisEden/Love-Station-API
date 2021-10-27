package org.eden.lovestation.exception.business;

public class RoomStateDuplicatedException extends Exception{

    public RoomStateDuplicatedException() {
        super("申請時間已被佔用(Room State occupied)");
    }
}
