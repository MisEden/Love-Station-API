package org.eden.lovestation.service;

import org.eden.lovestation.dao.model.ReferralEmployee;
import org.eden.lovestation.dao.model.ReferralNumber;
import org.eden.lovestation.dto.projection.CheckinApplicationWithRoomStateChangeDetail;
import org.eden.lovestation.dto.projection.ReferralEmployeeDetail;
import org.eden.lovestation.dto.request.GenerateReferralNumberRequest;
import org.eden.lovestation.dto.request.RegisterReferralEmployeeRequest;
import org.eden.lovestation.dto.response.CheckinApplicationWithRoomStateChangeDetailPagedResponse;
import org.eden.lovestation.exception.business.*;

import java.util.Date;
import java.util.List;

public interface ReferralEmployeeService {

    ReferralEmployee register(RegisterReferralEmployeeRequest request, String host) throws SaveReferralEmployeeMetaDataException, RepeatedAccountException, RoleNotFoundException, ReferralTitleNotFoundException, ReferralNotFoundException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException;

    ReferralNumber generateReferralNumber(GenerateReferralNumberRequest request, String referralEmployeeId) throws RepeatedGenerateReferralNumberException, ReferralEmployeeNotFoundException;

    ReferralEmployeeDetail findDetailById(String referralEmployeeId) throws ReferralEmployeeNotFoundException;

    CheckinApplicationWithRoomStateChangeDetailPagedResponse getRoomStatesChange(String referralEmployeeId, int currentPage) throws RoomStateChangeNotFoundException;

    String updateRoomStateChange(String checkinAppId, Date newStartDate, Date newEndDate, String changedItem, String reason) throws CheckinApplicationNotFoundException, RoomStateChangeNotFoundException;

}
