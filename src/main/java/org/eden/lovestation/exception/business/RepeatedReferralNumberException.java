package org.eden.lovestation.exception.business;

public class RepeatedReferralNumberException extends Exception {
    public RepeatedReferralNumberException() {
        super("此轉介編號已存在於資料庫(Repeated Referral Number)");
    }
}
