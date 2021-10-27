package org.eden.lovestation.exception;

import org.eden.lovestation.exception.business.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    // handle login fail
    @ExceptionHandler({LoginFailException.class})
    protected ResponseEntity<Object> handleUnauthorized(
            LoginFailException ex) {
        return buildResponseEntity(new APIError(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    @ExceptionHandler({AffidavitFileObtainFailException.class,
                        CheckinApplicationFileObtainFailException.class,
                        DeleteHouseImageException.class,
                        DeleteHousePlanimetricMapException.class,
                        DeleteHouseFullDegreePanoramaException.class,
                        DiseasesFormFileObtainFailException.class,
                        DownloadExportExcelException.class,
                        HouseFullDegreePanoramaExistedException.class,
                        HousePlanimetricMapExistedException.class,
                        PersonalAgreementInfoFileObtainFailException.class,
                        SaveReferralEmployeeMetaDataException.class,
                        UploadHouseFullDegreePanoramaException.class,
                        UploadHouseImageException.class,
                        UploadHousePlanimetricMapException.class,
                        UploadCleaningImageException.class,
                        UploadRentAndAffidavitImageException.class})
    protected ResponseEntity<Object> handleInternalServerException(
            Exception ex) {
        return buildResponseEntity(new APIError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler({RepeatedGenerateReferralNumberException.class,
            RoomStateDuplicatedException.class,
            CleaningImageTypeMismatchException.class})
    protected ResponseEntity<Object> handleConflictException(
            Exception ex) {
        return buildResponseEntity(new APIError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    // handle when database constraint error
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();
        int lastDashIndex = message.lastIndexOf("_");
        int lastParenthesesIndex = message.lastIndexOf(")");
        String fieldName = message.substring(lastDashIndex + 1, lastParenthesesIndex);
        FieldError fieldError = new FieldError("", fieldName, "duplicate value");
        List<FieldError> fieldErrors = List.of(fieldError);
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, "database constraint error");
        apiError.addValidationErrors(fieldErrors);
        return buildResponseEntity(apiError);
    }

    // handle when model valid error
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, "validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        return buildResponseEntity(apiError);
    }

    // handle unsupported media type
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    protected ResponseEntity<Object> handleUnsupportedMediaTypeException(
            HttpMediaTypeNotSupportedException ex) {
        return buildResponseEntity(new APIError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage()));
    }

    // handle unsupported media type
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<Object> handleMethodNotAllowedException(
            HttpRequestMethodNotSupportedException ex) {
        return buildResponseEntity(new APIError(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage()));
    }

    // handle when model valid error
    @ExceptionHandler({BindException.class})
    protected ResponseEntity<Object> handleBindException(
            BindException ex) {
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, "validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({AffidavitImageTypeMismatchException.class,
            CheckinApplicationStageEnumConvertException.class,
            HouseFullDegreePanoramaTypeMismatchException.class,
            HouseImageTypeMismatchException.class,
            HousePlanimetricMapTypeMismatchException.class,
            IncorrectFormatCityException.class,
            LineNotificationException.class,
            MissingServletRequestParameterException.class,
            PasswordResetConfirmException.class,
            PasswordResetOriginalException.class,
            RentImageTypeMismatchException.class,
            RepeatedAccountException.class,
            RepeatedEmailException.class,
            RepeatedFirmNameException.class,
            RepeatedHouseNameException.class,
            RepeatedIdentityCardException.class,
            RepeatedLineIdException.class,
            RepeatedPrivateFurnitureNameException.class,
            RepeatedPublicFurnitureNameException.class,
            RepeatedReferralHospitalChineseNameException.class,
            RepeatedReferralHospitalEnglishNameException.class,
            RepeatedReferralNumberException.class,
            RepeatedRoomNumberException.class,
            RoomStateChangeExistException.class,
            ServletRequestBindingException.class})
    protected ResponseEntity<Object> handleBadRequest(
            Exception ex) {
        return buildResponseEntity(new APIError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return buildResponseEntity(new APIError(HttpStatus.BAD_REQUEST, "parse JSON error"));
    }

    // handle when resource not found
    @ExceptionHandler({AdminNotFoundException.class,
        CheckinApplicationNotFoundException.class,
        CheckinFormConfirmNotFoundException.class,
        CheckinFormNotFoundException.class,
        CheckoutFormNotFoundException.class,
        DateFormatParseException.class,
        EmailNotFoundException.class,
        FirmNotFoundException.class,
        FirmEmployeeNotFoundException.class,
        FurnitureNotFoundException.class,
        HouseFullDegreePanoramaNotFoundException.class,
        HouseImageNotFoundException.class,
        HousePlanimetricMapNotFoundException.class,
        HousePlanimetricMapNotFoundException.class,
        HouseNotFoundException.class,
        InvestigationNotFoundException.class,
        LandlordNotFoundException.class,
        LandlordServiceRecordNotFoundException.class,
        NoHandlerFoundException.class,
        PasswordResetUpdateFailExcetion.class,
        PasswordResetUpdateNotFoundException.class,
        PrivateFurnitureNotFoundException.class,
        PublicFurnitureNotFoundException.class,
        ReferralEmployeeNotFoundException.class,
        ReferralNotFoundException.class,
        ReferralNumberNotFoundException.class,
        ReferralTitleNotFoundException.class,
        RoleNotFoundException.class,
        RoomNotFoundException.class,
        RoomOccupiedException.class,
        RoomStateNotFoundException.class,
        RoomStateChangeNotFoundException.class,
        UserNotFoundException.class,
        VolunteerNotFoundException.class,
        VolunteerServiceRecordNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFound(
            Exception ex) {
        return buildResponseEntity(new APIError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler({PasswordResetRateLimitException.class})
    protected ResponseEntity<Object> handleTooManyRequests(
            Exception ex) {
        return buildResponseEntity(new APIError(HttpStatus.TOO_MANY_REQUESTS, ex.getMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(APIError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
