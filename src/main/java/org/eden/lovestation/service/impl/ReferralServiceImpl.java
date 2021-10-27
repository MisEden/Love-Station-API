package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.repository.ReferralRepository;
import org.eden.lovestation.dto.projection.ReferralDetail;
import org.eden.lovestation.dto.projection.ReferralSearchResult;
import org.eden.lovestation.dto.response.ReferralSearchResultPagedResponse;
import org.eden.lovestation.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferralServiceImpl implements ReferralService {

    private final ReferralRepository referralRepository;

    @Autowired
    public ReferralServiceImpl(ReferralRepository referralRepository) {
        this.referralRepository = referralRepository;
    }

    @Override
    public List<ReferralDetail> findAllDetail() {
        return referralRepository.findAllBy();
    }

}
