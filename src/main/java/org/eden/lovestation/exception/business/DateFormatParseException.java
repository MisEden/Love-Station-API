package org.eden.lovestation.exception.business;

public class DateFormatParseException extends Exception {
    public DateFormatParseException() {
        super("無法將輸入資料轉換為日期資料(Fail to Parse into Format of Date)");
    }
}
