package org.eden.lovestation.dto.projection;

import org.springframework.beans.factory.annotation.Value;

public interface FirmEmployeeName {

    @Value("#{target.firm.id}")
    String getFirmId();

    @Value("#{target.firm.name}")
    String getFirmName();

    @Value("#{target.id}")
    String getFirmEmployeeId();

    @Value("#{target.chineseName}")
    String getFirmEmployeeName();
}
