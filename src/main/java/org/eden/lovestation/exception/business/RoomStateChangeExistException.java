package org.eden.lovestation.exception.business;

public class RoomStateChangeExistException extends Exception{

    public RoomStateChangeExistException() {
        super("變更申請已提出，請等候審核(Room State change already exist !)");
    }

}
