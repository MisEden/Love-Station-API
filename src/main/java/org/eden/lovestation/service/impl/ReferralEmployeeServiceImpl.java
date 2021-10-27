package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.projection.*;
import org.eden.lovestation.dto.request.GenerateReferralNumberRequest;
import org.eden.lovestation.dto.request.RegisterReferralEmployeeRequest;
import org.eden.lovestation.dto.response.CheckinApplicationWithRoomStateChangeDetailPagedResponse;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.ReferralEmployeeService;
import org.eden.lovestation.util.checker.CheckerUtil;
import org.eden.lovestation.util.password.PasswordUtil;
import org.eden.lovestation.util.storage.ReferralEmployeeMetaData;
import org.eden.lovestation.util.storage.StorageUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ReferralEmployeeServiceImpl implements ReferralEmployeeService {

    private final ReferralEmployeeRepository referralEmployeeRepository;
    private final StorageUtil storageUtil;
    private final CheckerUtil checkerUtil;
    private final PasswordUtil passwordUtil;
    private final ReferralRepository referralRepository;
    private final ReferralNumberRepository referralNumberRepository;
    private final ReferralTitleRepository referralTitleRepository;
    private final RoomStateRepository roomStateRepository;
    private final RoomStateChangeRepository roomStateChangeRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final CheckinApplicationRepository checkinApplicationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReferralEmployeeServiceImpl(ReferralEmployeeRepository referralEmployeeRepository,
                                       StorageUtil storageUtil,
                                       CheckerUtil checkerUtil,
                                       PasswordUtil passwordUtil,
                                       ReferralRepository referralRepository,
                                       ReferralNumberRepository referralNumberRepository, ReferralTitleRepository referralTitleRepository,
                                       RoomStateRepository roomStateRepository,
                                       RoomStateChangeRepository roomStateChangeRepository,
                                       RoomRepository roomRepository,
                                       UserRepository userRepository,
                                       CheckinApplicationRepository checkinApplicationRepository,
                                       ModelMapper modelMapper) {
        this.referralEmployeeRepository = referralEmployeeRepository;
        this.storageUtil = storageUtil;
        this.checkerUtil = checkerUtil;
        this.passwordUtil = passwordUtil;
        this.referralNumberRepository = referralNumberRepository;
        this.referralRepository = referralRepository;
        this.referralTitleRepository = referralTitleRepository;
        this.roomStateRepository = roomStateRepository;
        this.roomStateChangeRepository = roomStateChangeRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.checkinApplicationRepository = checkinApplicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReferralEmployee register(RegisterReferralEmployeeRequest request, String host) throws SaveReferralEmployeeMetaDataException, RepeatedAccountException, RoleNotFoundException, ReferralTitleNotFoundException, ReferralNotFoundException, RepeatedEmailException, RepeatedLineIdException, RepeatedIdentityCardException {
        try {
            // check mapping constraint and get model
            ReferralEmployee referralEmployee = checkerUtil.checkRegisterReferralEmployeeMappingAndReturn(request);

            // check repeated account
            checkerUtil.checkRepeatedAccount(request.getAccount());
//            checkerUtil.checkRepeatedEmail(request.getEmail());
//            checkerUtil.checkRepeatedLineId(request.getLineId());

            // save metadata
            String filePath = storageUtil.storeReferralEmployeeMetaData(new ReferralEmployeeMetaData(host, request.getImage()));
            referralEmployee.setImage(filePath);
            // get hash password
            String plainPassword = referralEmployee.getPassword();
            String hashPassword = passwordUtil.generateHashPassword(plainPassword);
            referralEmployee.setPassword(hashPassword);
            // save model
            return referralEmployeeRepository.save(referralEmployee);
        } catch (RepeatedAccountException e) {
            throw new RepeatedAccountException();
        } catch (IOException e) {
            throw new SaveReferralEmployeeMetaDataException();
        } catch (RoleNotFoundException e) {
            throw new RoleNotFoundException();
        } catch (ReferralTitleNotFoundException e) {
            throw new ReferralTitleNotFoundException();
        } catch (ReferralNotFoundException e) {
            throw new ReferralNotFoundException();
        }
    }

    @Override
    public ReferralNumber generateReferralNumber(GenerateReferralNumberRequest request, String referralEmployeeId) throws RepeatedGenerateReferralNumberException, ReferralEmployeeNotFoundException {
        checkerUtil.checkUserAlreadyGenerateReferralNumber(request.getIdentityCard());
        ReferralEmployee referralEmployee = referralEmployeeRepository.findById(referralEmployeeId).orElseThrow(ReferralEmployeeNotFoundException::new);
        String prefix = referralEmployee.getReferral().getNumber();
        long count = referralNumberRepository.countByIdContains(prefix) + 1;
        String referralNumberId = String.format("%s-%03d", prefix, count);
        ReferralNumber referralNumber = new ReferralNumber();
        referralNumber.setId(referralNumberId);
        referralNumber.setReferralEmployee(referralEmployee);
        referralNumber.setIdentityCard(request.getIdentityCard());
        return referralNumberRepository.save(referralNumber);
    }

    @Override
    public ReferralEmployeeDetail findDetailById(String referralEmployeeId) throws ReferralEmployeeNotFoundException {
        return referralEmployeeRepository.findDetailById(referralEmployeeId).orElseThrow(ReferralEmployeeNotFoundException::new);
    }

    @Override
    public CheckinApplicationWithRoomStateChangeDetailPagedResponse getRoomStatesChange(String referralEmployeeId, int currentPage) throws RoomStateChangeNotFoundException {

        System.out.println(referralEmployeeId);

        System.out.println(currentPage);

        Page<CheckinApplicationWithRoomStateChangeDetail> checkinApplicationWithRoomStateChangeDetailPage = checkinApplicationRepository.findAllBySearchCondition(referralEmployeeId, PageRequest.of(currentPage,
                5,
                Sort.by("created_at").descending()));

        return new CheckinApplicationWithRoomStateChangeDetailPagedResponse(checkinApplicationWithRoomStateChangeDetailPage.getContent(), checkinApplicationWithRoomStateChangeDetailPage.getNumber(), checkinApplicationWithRoomStateChangeDetailPage.getTotalPages());

    }

    @Override
    @Transactional
    public String updateRoomStateChange(String checkinAppId, Date newStartDate, Date newEndDate, String changedItem, String reason) throws CheckinApplicationNotFoundException, RoomStateChangeNotFoundException{

        CheckinApplication checkinApplication = checkinApplicationRepository.findById(checkinAppId).orElseThrow(CheckinApplicationNotFoundException::new);

        RoomStateChange roomStateChange = roomStateChangeRepository.findByRoomStateAndReferralVerifiedAndAdminVerified(checkinApplication.getRoomState(), false, false).orElseThrow(RoomStateChangeNotFoundException::new);

        if (newStartDate != null){
            roomStateChange.setNewStartDate(newStartDate);
        }
        if (newEndDate != null){
            roomStateChange.setNewEndDate(newEndDate);
        }
        if (changedItem != ""){
            roomStateChange.setChangedItem(changedItem);
        }
        if (reason != ""){
            roomStateChange.setReason(reason);
        }
        roomStateChange.setReferralVerified(true);
        roomStateChangeRepository.save(roomStateChange);

//        roomStateChangeRepository.updateRoomStateChangeByRoomState(newStartDate, newEndDate, reason, checkinApplication.getRoomState().getId());

        return "checkinAppId : " + checkinAppId + " update room state change success ! ";
    }

}
