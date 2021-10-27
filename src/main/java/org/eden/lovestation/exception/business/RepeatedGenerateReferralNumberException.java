package org.eden.lovestation.exception.business;

public class RepeatedGenerateReferralNumberException extends Exception {
    public RepeatedGenerateReferralNumberException() {
        super("轉介編號已存在於資料庫/已經生成過了(Repeated Referral Number)");
    }
}
