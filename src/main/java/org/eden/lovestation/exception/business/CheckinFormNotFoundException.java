package org.eden.lovestation.exception.business;

public class CheckinFormNotFoundException extends Exception {
    public CheckinFormNotFoundException(){ super("無法根據條件取得入住回報紀錄(Checkin_Form Not Found)"); }
}
