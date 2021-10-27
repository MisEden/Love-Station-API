package org.eden.lovestation.exception.business;

public class CheckinFormConfirmNotFoundException extends Exception  {
    public CheckinFormConfirmNotFoundException() { super("無法根據條件取得入住回報紀錄(Checkin Form Confirm Not Found)"); }
}
