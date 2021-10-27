package org.eden.lovestation.exception.business;

public class InvestigationNotFoundException extends Exception {
    public InvestigationNotFoundException() { super("無法根據條件取得退房問卷調查紀錄(Investigation Not Found)"); }
}
