package org.eden.lovestation.service.impl;

import org.eden.lovestation.dao.model.*;
import org.eden.lovestation.dao.repository.*;
import org.eden.lovestation.dto.projection.*;
import org.eden.lovestation.dto.projection.HousePublicFurniture;
import org.eden.lovestation.dto.request.*;
import org.eden.lovestation.dto.response.HouseDetailPage;
import org.eden.lovestation.exception.business.*;
import org.eden.lovestation.service.HouseService;
import org.eden.lovestation.util.storage.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private final ModelMapper modelMapper;
    private final HouseRepository houseRepository;
    private final HouseImageRepository houseImageRepository;
    private final LandlordRepository landlordRepository;
    private final RoomRepository roomRepository;
    private final HousePublicFurnitureRepository housePublicFurnitureRepository;
    private final HousePrivateFurnitureRepository housePrivateFurnitureRepository;
    private final PublicFurnitureRepository publicFurnitureRepository;
    private final PrivateFurnitureRepository privateFurnitureRepository;
    private final CheckinFormPublicFurnitureRepository checkinFormPublicFurnitureRepository;
    private final CheckinFormPrivateFurnitureRepository checkinFormPrivateFurnitureRepository;
    private final StorageUtil storageUtil;

    @Autowired
    public HouseServiceImpl(ModelMapper modelMapper,
                            HouseRepository houseRepository,
                            HouseImageRepository houseImageRepository,
                            LandlordRepository landlordRepository,
                            RoomRepository roomRepository,
                            HousePublicFurnitureRepository housePublicFurnitureRepository,
                            HousePrivateFurnitureRepository housePrivateFurnitureRepository,
                            PublicFurnitureRepository publicFurnitureRepository,
                            PrivateFurnitureRepository privateFurnitureRepository,
                            CheckinFormPublicFurnitureRepository checkinFormPublicFurnitureRepository,
                            CheckinFormPrivateFurnitureRepository checkinFormPrivateFurnitureRepository,
                            StorageUtil storageUtil) {
        this.modelMapper = modelMapper;
        this.houseRepository = houseRepository;
        this.houseImageRepository = houseImageRepository;
        this.landlordRepository = landlordRepository;
        this.roomRepository = roomRepository;
        this.housePublicFurnitureRepository = housePublicFurnitureRepository;
        this.housePrivateFurnitureRepository = housePrivateFurnitureRepository;
        this.publicFurnitureRepository = publicFurnitureRepository;
        this.privateFurnitureRepository = privateFurnitureRepository;
        this.checkinFormPublicFurnitureRepository = checkinFormPublicFurnitureRepository;
        this.checkinFormPrivateFurnitureRepository = checkinFormPrivateFurnitureRepository;
        this.storageUtil = storageUtil;
    }

    @Override
    public List<HouseName> findAllHouseNameWithoutDisable() {
        return houseRepository.findAllHouseNameWithoutDisable();
    }

    @Override
    public List<PublicFurniture> findAllPublicFurniture() {
        return publicFurnitureRepository.findAll();
    }

    @Override
    public List<PrivateFurniture> findAllPrivateFurniture() {
        return privateFurnitureRepository.findAll(Sort.by("precedence"));
    }

    @Override
    public List<HouseRoomId> findAllHouseRoomId(String houseId) throws HouseNotFoundException {
        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);
        return roomRepository.findAllByHouseWithoutDisabled(house.getId());
    }

    @Override
    public List<HouseRoomId> findAllHouseRoomIdWithDisable(String houseId) throws HouseNotFoundException {
        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);
        return roomRepository.findAllByHouse(house.getId());
    }

    @Override
    public HouseDetailPage findAllHouseDetail(int currentPage) {
        Page<HouseDetail> pageResult = houseRepository.findAllByOrderBySerial(PageRequest.of(currentPage,
                5));
        return new HouseDetailPage(pageResult.getContent(), pageResult.getNumber(), pageResult.getTotalPages());
    }

    @Override
    public PublicFurniture findPublicFurnitureByName(String name) throws FurnitureNotFoundException {
        PublicFurniture publicFurniture = publicFurnitureRepository.findByName(name).orElseThrow(FurnitureNotFoundException::new);
        return publicFurniture;
    }

    @Override
    public PrivateFurniture findPrivateFurnitureByName(String name) throws FurnitureNotFoundException{
        PrivateFurniture privateFurniture = privateFurnitureRepository.findByName(name).orElseThrow(FurnitureNotFoundException::new);
        return privateFurniture;
    }

    @Override
    public HouseDetail findHouseById(String houseId) throws HouseNotFoundException{
        HouseDetail house = houseRepository.findHouseById(houseId).orElseThrow(HouseNotFoundException::new);

        return house;
    }

    @Override
    public String findHouseNameById(String houseId) throws HouseNotFoundException{
        HouseDetail house = houseRepository.findHouseById(houseId).orElseThrow(HouseNotFoundException::new);

        return house.getName();
    }

    @Override
    public List<HousePublicFurniture> findAllHousePublicFurniture(String houseId) throws HouseNotFoundException {
        House house = houseRepository.findById(houseId).orElseThrow(HouseNotFoundException::new);
        List<HousePublicFurniture> list_housePublicFurniture = housePublicFurnitureRepository.findAllByHouse(house.getId());

        if (list_housePublicFurniture.size() > 0){
            Collections.sort(list_housePublicFurniture, new PublicFurnitureComparator());
        }

        return list_housePublicFurniture;
    }


}
