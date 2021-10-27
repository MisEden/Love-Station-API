package org.eden.lovestation.service;

import org.eden.lovestation.dao.model.User;
import org.eden.lovestation.dto.request.SendCheckoutFeedbackRequest;
import org.eden.lovestation.dto.request.SendCheckoutInvestigationRequest;
import org.eden.lovestation.dto.response.CheckoutApplicationRoomStateResponse;
import org.eden.lovestation.dto.response.CheckoutStatusResponse;
import org.eden.lovestation.dto.response.IsExistedResponse;
import org.eden.lovestation.exception.business.*;

import java.text.ParseException;

public interface CheckoutApplicationService {

    IsExistedResponse uploadCheckoutApplicationInvestigation(SendCheckoutInvestigationRequest request, String roomStatesId) throws RoomStateNotFoundException;

    IsExistedResponse checkInvestigationIsExisted(String roomStateId) throws RoomStateNotFoundException;

    IsExistedResponse sendCheckoutApplicationFeedback(SendCheckoutFeedbackRequest request, String roomStatesId) throws RoomStateNotFoundException;

    IsExistedResponse checkFeedbackIsExisted(String roomStateId) throws RoomStateNotFoundException;

    CheckoutApplicationRoomStateResponse getDaysOfStay(String roomStatesId) throws RoomNotFoundException;

    CheckoutStatusResponse checkStatusToCheckout(String userId) throws UserNotFoundException, RoomStateNotFoundException, CheckinApplicationNotFoundException, CheckinFormNotFoundException;
}
