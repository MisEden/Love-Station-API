package org.eden.lovestation.exception.business;

public class AdminNotFoundException extends Exception {
    public AdminNotFoundException() {
        super("無法根據條件取得Admin帳號(Admin Not Found)");
    }
}
