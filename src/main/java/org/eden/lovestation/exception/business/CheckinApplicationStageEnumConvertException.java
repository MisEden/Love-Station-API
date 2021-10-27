package org.eden.lovestation.exception.business;

public class CheckinApplicationStageEnumConvertException extends Exception {
    public CheckinApplicationStageEnumConvertException() {
        super("查詢字串錯誤(Query String Stage Not Matching for First, Second, Finished Or All)");
    }
}
