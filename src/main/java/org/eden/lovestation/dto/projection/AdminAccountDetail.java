package org.eden.lovestation.dto.projection;

import org.springframework.beans.factory.annotation.Value;

public interface AdminAccountDetail {

    String getId();

    String getRole();

    String getName();

    String getAccount();

    String getEmail();
}
