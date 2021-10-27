package org.eden.lovestation.exception.business;

public class CheckinApplicationFileObtainFailException extends Exception {
    public CheckinApplicationFileObtainFailException() {
        super("無法取得入住申請文件(CheckinApplication Obtain File Fail)");
    }
}
