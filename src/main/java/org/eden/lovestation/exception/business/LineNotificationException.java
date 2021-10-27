package org.eden.lovestation.exception.business;

public class LineNotificationException extends Exception {
    public LineNotificationException() {
        super("傳送Line通知失敗(Line Notification Fail)");
    }
}
