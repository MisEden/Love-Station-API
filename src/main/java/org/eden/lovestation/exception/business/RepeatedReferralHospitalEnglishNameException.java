package org.eden.lovestation.exception.business;

public class RepeatedReferralHospitalEnglishNameException extends Exception {
    public RepeatedReferralHospitalEnglishNameException() {
        super("此醫院英文名稱已存在於資料庫(Repeated HospitalEnglishName)");
    }
}
