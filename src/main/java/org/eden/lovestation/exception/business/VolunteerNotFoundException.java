package org.eden.lovestation.exception.business;

public class VolunteerNotFoundException extends Exception {
    public VolunteerNotFoundException() {
        super("無法根據條件取得志工帳號(Volunteer Not Found)");
    }
}
