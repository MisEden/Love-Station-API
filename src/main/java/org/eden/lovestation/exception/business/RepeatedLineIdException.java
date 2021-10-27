package org.eden.lovestation.exception.business;

public class RepeatedLineIdException extends Exception {
    public RepeatedLineIdException() {
        super("使用者資訊已存在於資料庫-Line帳號(Repeated LineId");
    }
}
