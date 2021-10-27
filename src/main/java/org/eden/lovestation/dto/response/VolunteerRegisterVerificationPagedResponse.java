package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.VolunteerRegisterVerification;

import java.util.List;

@Data
@AllArgsConstructor
public class VolunteerRegisterVerificationPagedResponse {
    private List<VolunteerRegisterVerification> volunteers;
    private int currentPage;
    private int totalPage;
}
