package org.eden.lovestation.exception.business;

public class RepeatedReferralHospitalChineseNameException extends Exception {
    public RepeatedReferralHospitalChineseNameException() {
        super("此醫院中文名稱已存在於資料庫(Repeated HospitalChineseName)");
    }
}
