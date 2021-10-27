package org.eden.lovestation.dto.projection;

public interface ReferralEmployeeRegisterVerification {
    String getId();

    ReferralDetail getReferral();

    ReferralTitleDetail getReferralTitle();

    String getName();

    String getWorkIdentity();

    String getImage();

    String getEmail();

    String getAddress();

    String getPhone();

    String getCellphone();

    Boolean getVerified();

    Boolean getAgreePersonalInformation();
}
