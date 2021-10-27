package org.eden.lovestation.service;

import org.eden.lovestation.dto.projection.LandlordRecordDetail;
import org.eden.lovestation.exception.business.LandlordServiceRecordNotFoundException;

import java.util.Date;
import java.util.List;

public interface ServiceRecordService {

    List<LandlordRecordDetail> getLandlordServiceRecord(String landlordId, String houseName, Integer roomNumber, Date startDate, Date endDate, int currentPage);

    String updateServiceRecordViewed(String viewed, String serviceRecordId) throws LandlordServiceRecordNotFoundException;

}
