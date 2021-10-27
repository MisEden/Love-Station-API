package org.eden.lovestation.dto.request;

import lombok.Data;
import org.eden.lovestation.exception.business.DateFormatParseException;
import org.eden.lovestation.util.validator.CustomDateConstraint;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Data
public class UpdateFirstStageCheckinApplicationVerificationRequest {
    @NotNull
    private Boolean firstVerified;
    private String firstVerifiedReason;
    @CustomDateConstraint
    private String startDate;
    @CustomDateConstraint
    private String endDate;
    private String roomId;

    public Date getStartDateConverted() throws DateFormatParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return simpleDateFormat.parse(this.startDate);
        } catch (ParseException e) {
            throw new DateFormatParseException();
        }
    }

    public Date getEndDateConverted() throws DateFormatParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return simpleDateFormat.parse(this.endDate);
        } catch (ParseException e) {
            throw new DateFormatParseException();
        }
    }
}
