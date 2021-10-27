package org.eden.lovestation.dto.projection;

import org.springframework.beans.factory.annotation.Value;

public interface VolunteerName {

    String getId();

    @Value("#{target.chinese_name}")
    String getName();

}
