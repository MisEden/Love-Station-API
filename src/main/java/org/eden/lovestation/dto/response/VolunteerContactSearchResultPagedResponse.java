package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.eden.lovestation.dto.projection.VolunteerContact;
import org.eden.lovestation.dto.projection.VolunteerDetail;

import java.util.List;

@Data
@AllArgsConstructor
public class VolunteerContactSearchResultPagedResponse {
    private List<VolunteerContact> volunteer;
    private int currentPage;
    private int totalPage;
}
