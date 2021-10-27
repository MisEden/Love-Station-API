package org.eden.lovestation.exception.business;

public class CheckinApplicationNotFoundException extends Exception {
    public CheckinApplicationNotFoundException() {
        super("無法根據條件取得入住申請(CheckinApplication Not Found)");
    }
}
