package org.eden.lovestation.exception.business;

public class EmailNotFoundException extends Exception {
    public EmailNotFoundException() {
        super("資料庫中無此電子信箱，請再次檢查(Email Not Found)");
    }
}
