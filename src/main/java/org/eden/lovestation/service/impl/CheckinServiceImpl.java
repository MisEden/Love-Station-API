package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.projection.CheckinLocation;
import org.eden.lovestation.dto.projection.HousePublicFurniture;
import org.eden.lovestation.dto.projection.HousePrivateFurniture;
import org.eden.lovestation.dto.request.CheckinCheckRequest;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CheckinServiceImpl implements CheckinService {

    private final UserRepository userRepository;
    private final RoomStateRepository roomStateRepository;
    private final RoomRepository roomRepository;
    private final CheckinFormRepository checkinFormRepository;
    private final CheckinFormConfirmRepository checkinFormConfirmRepository;
    private final CheckinFormPublicFurnitureRepository checkinFormPublicFurnitureRepository;
    private final CheckinFormPrivateFurnitureRepository checkinFormPrivateFurnitureRepository;
    private final HouseRepository houseRepository;
    private final HousePublicFurnitureRepository housePublicFurnitureRepository;
    private final HousePrivateFurnitureRepository housePrivateFurnitureRepository;
    private final PublicFurnitureRepository publicFurnitureRepository;
    private final PrivateFurnitureRepository privateFurnitureRepository;


    @Autowired
    public CheckinServiceImpl(CheckinFormRepository checkinFormRepository,
                              CheckinFormConfirmRepository checkinFormConfirmRepository,
                              CheckinFormPublicFurnitureRepository checkinFormPublicFurnitureRepository,
                              CheckinFormPrivateFurnitureRepository checkinFormPrivateFurnitureRepository,
                              UserRepository userRepository,
                              RoomRepository roomRepository,
                              RoomStateRepository roomStateRepository,
                              HouseRepository houseRepository,
                              HousePublicFurnitureRepository housePublicFurnitureRepository,
                              HousePrivateFurnitureRepository housePrivateFurnitureRepository,
                              PublicFurnitureRepository publicFurnitureRepository,
                              PrivateFurnitureRepository privateFurnitureRepository){
        this.checkinFormRepository = checkinFormRepository;
        this.checkinFormConfirmRepository = checkinFormConfirmRepository;
        this.checkinFormPublicFurnitureRepository = checkinFormPublicFurnitureRepository;
        this.checkinFormPrivateFurnitureRepository = checkinFormPrivateFurnitureRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.roomStateRepository = roomStateRepository;
        this.houseRepository = houseRepository;
        this.housePublicFurnitureRepository = housePublicFurnitureRepository;
        this.housePrivateFurnitureRepository = housePrivateFurnitureRepository;
        this.publicFurnitureRepository = publicFurnitureRepository;
        this.privateFurnitureRepository = privateFurnitureRepository;
    }

    @Override
    public CheckinLocation findUserCheckinLocation(String checkDate, String userId) throws UserNotFoundException, ParseException {
        //Get user by the number of Account
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        //Transform the string to date
        String dateString = checkDate + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdf.parse(dateString);

        dateString = checkDate + " 23:59:59";
        Date endDate = sdf.parse(dateString);

        return roomStateRepository.findAllByUserAndStartDateAndEndDate(startDate, endDate, user.getAccount());
    }


    public int countCheckinForm(String roomStateId) throws RoomStateNotFoundException {
        //check roomStateId
        RoomState roomState = roomStateRepository.findById(roomStateId).orElseThrow(RoomStateNotFoundException::new);
        List<CheckinForm> checkinForms = checkinFormRepository.findByRoomStateId(roomStateId);

        return checkinForms.size();
    }


    @Override
    public List<HousePublicFurniture> findAllHousePublicFurniture(String roomId) throws RoomNotFoundException {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        List<HousePublicFurniture> list_housePublicFurniture = housePublicFurnitureRepository.findAllByHouse(room.getHouse().getId());

        if (list_housePublicFurniture.size() > 0){
            Collections.sort(list_housePublicFurniture, new PublicFurnitureComparator());
        }

        return list_housePublicFurniture;
    }

    @Override
    public List<HousePrivateFurniture> findAllHousePrivateFurniture(String roomId) throws RoomNotFoundException {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        List<HousePrivateFurniture> list_housePrivateFurniture = housePrivateFurnitureRepository.findAllByRoom(room.getId());

        if (list_housePrivateFurniture.size() > 0){
            Collections.sort(list_housePrivateFurniture, new PrivateFurnitureComparator());
        }

        return list_housePrivateFurniture;
    }

    @Transactional
    @Override
    public CheckinForm saveCheckinResult(CheckinCheckRequest request, String userId) throws UserNotFoundException,RoomStateNotFoundException, FurnitureNotFoundException, RoomNotFoundException, HouseNotFoundException {

        org.eden.lovestation.dao.model.CheckinForm checkinForm = new org.eden.lovestation.dao.model.CheckinForm();
        RoomState roomState = roomStateRepository.findById(request.getRoomStateId()).orElseThrow(RoomStateNotFoundException::new);


        // Verify the right user
        User user = userRepository.findByIdAndVerified(userId, true).orElseThrow(UserNotFoundException::new);
        if(user != roomState.getUser()){ throw new UserNotFoundException(); }

        Room room = roomRepository.findById(roomState.getRoom().getId()).orElseThrow(RoomNotFoundException::new);
        House house = houseRepository.findById(room.getHouse().getId()).orElseThrow(HouseNotFoundException::new);
        checkinForm.setHouse(house);
        checkinForm.setRoomState(roomState);
        checkinFormRepository.save(checkinForm);

        //Save data to "checkin_form_confirm"
        CheckinFormConfirm checkinFormConfirm = new CheckinFormConfirm();
        checkinFormConfirm.setCheckinForm(checkinForm);
        checkinFormConfirm.setLock(request.getLock());
        checkinFormConfirm.setPower(request.getPower());
        checkinFormConfirm.setConvention(request.getConventionConverted());
        checkinFormConfirm.setContract(request.getContract());
        checkinFormConfirm.setSecurity(request.getSecurity());
        checkinFormConfirm.setHeater(request.getHeater());
        checkinFormConfirmRepository.save(checkinFormConfirm);


      //Save data to "checkin_form_public_furniture"
      for(int i = 0; i< request.getExistPublicFurnitures().size(); i++){
          String furnitureName = request.getExistPublicFurnitures().get(i);
          PublicFurniture publicFurniture = publicFurnitureRepository.findByName(furnitureName).orElseThrow(FurnitureNotFoundException::new);
          Boolean isBroken = false;
          if(request.getBrokenPublicFurnitures().indexOf(furnitureName) >= 0){ isBroken = true; }


          CheckinFormPublicFurniture checkinFormPublicFurniture = new CheckinFormPublicFurniture();
          checkinFormPublicFurniture.setCheckinForm(checkinForm);
          checkinFormPublicFurniture.setPublicFurniture(publicFurniture);
          checkinFormPublicFurniture.setBroken(isBroken);
          checkinFormPublicFurnitureRepository.save(checkinFormPublicFurniture);
      }

        //Save data to "checkin_form_private_furniture"
        for(int i = 0; i< request.getExistPrivateFurnitures().size(); i++){
            String furnitureName = request.getExistPrivateFurnitures().get(i);
            PrivateFurniture privateFurniture = privateFurnitureRepository.findByName(furnitureName).orElseThrow(FurnitureNotFoundException::new);
            Boolean isBroken = false;
            if(request.getBrokenPrivateFurnitures().indexOf(furnitureName) >= 0){ isBroken = true; }

            CheckinFormPrivateFurniture checkinFormPrivateFurniture = new CheckinFormPrivateFurniture();
            checkinFormPrivateFurniture.setCheckinForm(checkinForm);
            checkinFormPrivateFurniture.setPrivateFurniture(privateFurniture);
            checkinFormPrivateFurniture.setBroken(isBroken);
            checkinFormPrivateFurnitureRepository.save(checkinFormPrivateFurniture);
        }

        return checkinForm;
    }
}

class PublicFurnitureComparator implements Comparator<HousePublicFurniture> {
    @Override
    public int compare(HousePublicFurniture a, HousePublicFurniture b) {
        return a.getPublicFurniturePrecedence() - b.getPublicFurniturePrecedence();
    }
}

class PrivateFurnitureComparator implements Comparator<HousePrivateFurniture> {
    @Override
    public int compare(HousePrivateFurniture a, HousePrivateFurniture b) {
        return a.getPrivateFurniturePrecedence() - b.getPrivateFurniturePrecedence();
    }
}
