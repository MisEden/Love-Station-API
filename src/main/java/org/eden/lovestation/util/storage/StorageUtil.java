package org.eden.lovestation.util.storage;

import lombok.Data;
import org.eden.lovestation.exception.business.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
@ConfigurationProperties(prefix = "storage")
@Data
public class StorageUtil {

    private String referralEmployeeImageBasePath;
    private String houseImageBasePath;
    private String houseFullDegreePanoramaBasePath;
    private String housePanimetricMapBasePath;
    private String checkinApplicationRentBasePath;
    private String checkinApplicationAffidavitBasePath;
    private String firmEmployeeBeforeCleaningImageBasePath;
    private String firmEmployeeAfterCleaningImageBasePath;

    public String[] storeCheckinApplicationMetaData(CheckinApplicationMetaData metaData) throws RentImageTypeMismatchException, AffidavitImageTypeMismatchException, UploadRentAndAffidavitImageException {
        String rentImageId = UUID.randomUUID().toString();
        String affidavitImageId = UUID.randomUUID().toString();

        String host = metaData.getHost();

        MultipartFile rentImage = metaData.getRentImage();
        MultipartFile affidavitImage = metaData.getAffidavitImage();

        if (!rentImage.getContentType().contains("image/jpeg") && !rentImage.getContentType().contains("image/png")) {
            throw new RentImageTypeMismatchException();
        }
        if (!affidavitImage.getContentType().contains("image/jpeg") && !affidavitImage.getContentType().contains("image/png")) {
            throw new AffidavitImageTypeMismatchException();
        }

        String rentImageExtension = rentImage.getContentType().split("/")[1];
        String affidavitImageExtension = affidavitImage.getContentType().split("/")[1];

        try {
            byte[] rentImageBytes = rentImage.getBytes();
            byte[] affidavitImageBytes = affidavitImage.getBytes();

            String rentImageFilePath = String.format(".%s%s.%s", checkinApplicationRentBasePath, rentImageId, rentImageExtension);
            String affidavitImageFilePath = String.format(".%s%s.%s", checkinApplicationAffidavitBasePath, affidavitImageId, affidavitImageExtension);

            Path rentImagePath = Paths.get(rentImageFilePath);
            Path affidavitImagePath = Paths.get(affidavitImageFilePath);

            Files.createDirectories(rentImagePath.getParent());
            Files.createDirectories(affidavitImagePath.getParent());

            Files.write(rentImagePath, rentImageBytes);
            Files.write(affidavitImagePath, affidavitImageBytes);
            return new String[]{checkinApplicationRentBasePath + rentImagePath.getFileName(), checkinApplicationAffidavitBasePath + affidavitImagePath.getFileName()};
        } catch (IOException e) {
            throw new UploadRentAndAffidavitImageException();
        }
    }

    public String storeHouseImageMetaData(HouseImageMetaData metaData) throws HouseImageTypeMismatchException, UploadHouseImageException {
        String houseImageId = UUID.randomUUID().toString();

        String host = metaData.getHost();

        MultipartFile houseImage = metaData.getHouseImage();
        if (!houseImage.getContentType().contains("image/jpeg") && !houseImage.getContentType().contains("image/png")) {
            throw new HouseImageTypeMismatchException();
        }
        String houseImageExtension = houseImage.getContentType().split("/")[1];

        try {
            byte[] houseImageBytes = houseImage.getBytes();
            String houseImageFilePath = String.format(".%s%s.%s", houseImageBasePath, houseImageId, houseImageExtension);
            Path houseImagePath = Paths.get(houseImageFilePath);
            Files.createDirectories(houseImagePath.getParent());
            Files.write(houseImagePath, houseImageBytes);

            return (houseImageBasePath + houseImagePath.getFileName());
        } catch (IOException e) {
            throw new UploadHouseImageException();
        }
    }

    public String storeHousePlanimetricMapMetaData(HousePlanimetricMapMetaData metaData) throws HousePlanimetricMapTypeMismatchException, UploadHousePlanimetricMapException {
        String housePlanimetricMapId = UUID.randomUUID().toString();

        String host = metaData.getHost();

        MultipartFile housePlanimetricMap = metaData.getHousePlanimetricMap();
        if (!housePlanimetricMap.getContentType().contains("image/jpeg") && !housePlanimetricMap.getContentType().contains("image/png")) {
            throw new HousePlanimetricMapTypeMismatchException();
        }
        String housePlanimetricMapExtension = housePlanimetricMap.getContentType().split("/")[1];

        try {
            byte[] housePlanimetricMapBytes = housePlanimetricMap.getBytes();
            String housePlanimetricMapFilePath = String.format(".%s%s.%s", housePanimetricMapBasePath, housePlanimetricMapId, housePlanimetricMapExtension);
            Path housePlanimetricMapPath = Paths.get(housePlanimetricMapFilePath);
            Files.createDirectories(housePlanimetricMapPath.getParent());
            Files.write(housePlanimetricMapPath, housePlanimetricMapBytes);

            return (housePanimetricMapBasePath + housePlanimetricMapPath.getFileName());
        } catch (IOException e) {
            throw new UploadHousePlanimetricMapException();
        }

    }

    public String storeHouseFullDegreePanoramaMetaData(HouseFullDegreePanoramaMetaData metaData) throws HouseFullDegreePanoramaTypeMismatchException, UploadHouseFullDegreePanoramaException {
        String houseFullDegreePanoramaId = UUID.randomUUID().toString();

        String host = metaData.getHost();

        MultipartFile houseFullDegreePanorama = metaData.getHouseFullDegreePanorama();
        if (!houseFullDegreePanorama.getContentType().contains("image/jpeg") && !houseFullDegreePanorama.getContentType().contains("image/png")) {
            throw new HouseFullDegreePanoramaTypeMismatchException();
        }
        String houseFullDegreePanoramaExtension = houseFullDegreePanorama.getContentType().split("/")[1];

        try {
            byte[] houseFullDegreePanoramaBytes = houseFullDegreePanorama.getBytes();
            String houseFullDegreePanoramaFilePath = String.format(".%s%s.%s", houseFullDegreePanoramaBasePath, houseFullDegreePanoramaId, houseFullDegreePanoramaExtension);
            Path houseFullDegreePanoramaPath = Paths.get(houseFullDegreePanoramaFilePath);
            Files.createDirectories(houseFullDegreePanoramaPath.getParent());
            Files.write(houseFullDegreePanoramaPath, houseFullDegreePanoramaBytes);

            return (houseFullDegreePanoramaBasePath + houseFullDegreePanoramaPath.getFileName());
        } catch (IOException e) {
            throw new UploadHouseFullDegreePanoramaException();
        }
    }

    public String storeReferralEmployeeMetaData(ReferralEmployeeMetaData metaData) throws IOException {

        String id = UUID.randomUUID().toString();
        String host = metaData.getHost();
        MultipartFile image = metaData.getImage();
        String imageExtension = image.getContentType().split("/")[1];

        byte[] imageBytes = image.getBytes();
        String imageFilePath = String.format(".%s%s.%s", referralEmployeeImageBasePath, id, imageExtension);
        Path imagePath = Paths.get(imageFilePath);
        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, imageBytes);

        return referralEmployeeImageBasePath + imagePath.getFileName();
    }

    public String[] storeFirmEmployeeCleaningImageMetaData(FirmEmployeeCleaningImageMetaData metaData) throws UploadCleaningImageException, CleaningImageTypeMismatchException {

        String beforeImageId = UUID.randomUUID().toString();
        String afterImageId = UUID.randomUUID().toString();

        String host = metaData.getHost();

        try{

            MultipartFile beforeImage = metaData.getBeforeImage();
            MultipartFile afterImage = metaData.getAfterImage();

            System.out.println(beforeImage.getContentType()); // image/png
            System.out.println(afterImage.getContentType());

            if (!beforeImage.getContentType().contains("image/jpeg") && !beforeImage.getContentType().contains("image/png")) {
                throw new CleaningImageTypeMismatchException();
            }
            if (!afterImage.getContentType().contains("image/jpeg") && !afterImage.getContentType().contains("image/png")) {
                throw new CleaningImageTypeMismatchException();
            }

            String beforeImageExtension = beforeImage.getContentType().split("/")[1]; // png
            String afterImageExtension = afterImage.getContentType().split("/")[1];

//        try {
            byte[] beforeImageBytes = beforeImage.getBytes();
            byte[] afterImageBytes = afterImage.getBytes();

//            ./storage/firm-employee-before-cleaning/UUID.png
//            ./storage/firm-employee-after-cleaning/UUID.png
            String beforeImageFilePath = String.format(".%s%s.%s", firmEmployeeBeforeCleaningImageBasePath, beforeImageId, beforeImageExtension);
            String afterImageFilePath = String.format(".%s%s.%s", firmEmployeeAfterCleaningImageBasePath, afterImageId, afterImageExtension);

//            .\storage\firm-employee-before-cleaning\UUID.png
//            .\storage\firm-employee-after-cleaning\UUID.png
            Path beforeImagePath = Paths.get(beforeImageFilePath);
            Path afterImagePath = Paths.get(afterImageFilePath);

//            Files.createDirectories()
//            creates a directory by creating all nonexistent parent directories first. Exception is not thrown if the directory could not be created because it already exists.
            Files.createDirectories(beforeImagePath.getParent());
            Files.createDirectories(afterImagePath.getParent());

            Files.write(beforeImagePath, beforeImageBytes);
            Files.write(afterImagePath, afterImageBytes);

            return new String[]{firmEmployeeBeforeCleaningImageBasePath + beforeImagePath.getFileName(), firmEmployeeAfterCleaningImageBasePath + afterImagePath.getFileName()};

        } catch (NullPointerException nullPointerException) {
            throw new UploadCleaningImageException();
        } catch (IOException ioException) {
            throw new UploadCleaningImageException();
        }

    }

}
