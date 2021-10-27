package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.ReferralTitle;
import org.eden.lovestation.dao.repository.ReferralTitleRepository;
import org.eden.lovestation.service.ReferralTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferralTitleServiceImpl implements ReferralTitleService {
    private final ReferralTitleRepository referralTitleRepository;

    @Autowired
    public ReferralTitleServiceImpl(ReferralTitleRepository referralTitleRepository) {
        this.referralTitleRepository = referralTitleRepository;
    }

    @Override
    public List<ReferralTitle> findAll() {
        return referralTitleRepository.findAll();
    }
}
