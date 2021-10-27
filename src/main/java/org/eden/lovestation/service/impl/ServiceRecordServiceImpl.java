package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.CheckinApplication;
import org.eden.lovestation.dao.model.LandlordServiceRecord;
import org.eden.lovestation.dao.repository.CheckinApplicationRepository;
import org.eden.lovestation.dao.repository.LandlordServiceRecordRepository;
import org.eden.lovestation.dto.projection.LandlordRecordDetail;
import org.eden.lovestation.exception.business.LandlordServiceRecordNotFoundException;
import org.eden.lovestation.service.ServiceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ServiceRecordServiceImpl implements ServiceRecordService {

    private final CheckinApplicationRepository checkinApplicationRepository;
    private final LandlordServiceRecordRepository landlordServiceRecordRepository;

    @Autowired
    public ServiceRecordServiceImpl(LandlordServiceRecordRepository landlordServiceRecordRepository,
                                    CheckinApplicationRepository checkinApplicationRepository){
        this.checkinApplicationRepository = checkinApplicationRepository;
        this.landlordServiceRecordRepository = landlordServiceRecordRepository;
    }

    @Override
    public List<LandlordRecordDetail> getLandlordServiceRecord(String landlordId, String houseName, Integer roomNumber, Date startDate, Date endDate, int currentPage){

        CheckinApplication checkinApplication = checkinApplicationRepository.findAllBySearchCondition(houseName, roomNumber, startDate, endDate, landlordId);

        return landlordServiceRecordRepository.findAllByCheckinApplicationId(checkinApplication.getId());
    }

    @Transactional
    @Override
    public String updateServiceRecordViewed(String viewed, String serviceRecordId) throws LandlordServiceRecordNotFoundException{

        LandlordServiceRecord landlordServiceRecord = landlordServiceRecordRepository.findById(serviceRecordId).orElseThrow(LandlordServiceRecordNotFoundException::new);
        landlordServiceRecord.updatedAt = new Date();
        landlordServiceRecord.setViewed(viewed);
        landlordServiceRecordRepository.save(landlordServiceRecord);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.format(landlordServiceRecord.updatedAt);
    }

}
