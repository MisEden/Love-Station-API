package org.eden.lovestation.dto.projection;

import org.springframework.beans.factory.annotation.Value;

public interface FirmDetail {

    String getId();

    String getName();

    String getAddress();

    String getPhone();

    @Value("#{target.contact_people}")
    String getContactPeople();

    @Value("#{target.contact_title}")
    String getContactTitle();

    @Value("#{target.contact_phone}")
    String getContactPhone();

    @Value("#{target.contact_email}")
    String getContactEmail();
}
