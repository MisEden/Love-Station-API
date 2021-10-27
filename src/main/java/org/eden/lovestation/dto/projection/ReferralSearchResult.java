package org.eden.lovestation.dto.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface ReferralSearchResult {
    String getId();

    String getChinese();

    String getEnglish();

    String getNumber();

    String getCity();
}
