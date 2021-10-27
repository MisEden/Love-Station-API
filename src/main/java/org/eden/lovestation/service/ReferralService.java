package org.eden.lovestation.service;

import org.eden.lovestation.dao.model.Referral;
import org.eden.lovestation.dto.projection.ReferralDetail;
import org.eden.lovestation.dto.projection.ReferralSearchResult;
import org.eden.lovestation.dto.request.AdminUpdateReferral;
import org.eden.lovestation.dto.response.ReferralSearchResultPagedResponse;
import org.eden.lovestation.exception.business.*;

import java.util.List;

public interface ReferralService {
    List<ReferralDetail> findAllDetail();

}
