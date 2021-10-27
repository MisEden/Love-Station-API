package org.eden.lovestation.exception.business;

public class IncorrectFormatCityException extends Exception {
    public IncorrectFormatCityException() {
        super("此城市名稱不合法，請再次檢查(Incorrect City Format)");
    }
}
