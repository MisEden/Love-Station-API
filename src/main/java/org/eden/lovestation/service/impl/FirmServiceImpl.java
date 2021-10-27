package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.repository.FirmRepository;
import org.eden.lovestation.dto.projection.FirmDetail;
import org.eden.lovestation.dto.projection.FirmName;
import org.eden.lovestation.dto.response.FirmSearchResultPagedResponse;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.FirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class FirmServiceImpl implements FirmService {

    private final FirmRepository firmRepository;

    @Autowired
    public FirmServiceImpl(FirmRepository firmRepository) {
        this.firmRepository = firmRepository;
    }

    @Override
    public List<FirmName> getFirmNames() throws FirmNotFoundException{
        List<FirmName> firmNameList = firmRepository.findAllFirmNames();
        if (firmNameList.isEmpty()){
            throw new FirmNotFoundException();
        }
        return firmNameList;
    }

}
