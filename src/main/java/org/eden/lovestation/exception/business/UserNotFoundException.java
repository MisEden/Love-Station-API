package org.eden.lovestation.exception.business;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("無法根據條件取得就醫民眾帳號(User Not Found)");
    }
}
