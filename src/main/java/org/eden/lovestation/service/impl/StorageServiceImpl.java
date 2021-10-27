package org.eden.lovestation.service.impl;

import org.eden.lovestation.dto.response.DownloadAffidavitFile;
import org.eden.lovestation.dto.response.DownloadCheckinApplicationFile;
import org.eden.lovestation.dto.response.DownloadDiseasesFormFile;
import org.eden.lovestation.dto.response.DownloadPersonalAgreementInfoFile;
import org.eden.lovestation.exception.business.AffidavitFileObtainFailException;
import org.eden.lovestation.exception.business.CheckinApplicationFileObtainFailException;
import org.eden.lovestation.exception.business.DiseasesFormFileObtainFailException;
import org.eden.lovestation.exception.business.PersonalAgreementInfoFileObtainFailException;
import org.eden.lovestation.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StorageServiceImpl implements StorageService {
    @Value("static/personal_agreement_info.docx")
    private String personalAgreementInfoPath;
    @Value("static/checkin.docx")
    private String checkinPath;
    @Value("static/affidavit.docx")
    private String affidavitPath;
    @Value("static/diseases_form.docx")
    private String diseasesPath;


    @Override
    public DownloadPersonalAgreementInfoFile downloadPersonalAgreementInfoFile() throws PersonalAgreementInfoFileObtainFailException {
        try {
            InputStreamResource resource = new InputStreamResource(new ClassPathResource(personalAgreementInfoPath).getInputStream());
            return new DownloadPersonalAgreementInfoFile(resource);
        } catch (IOException e) {
            throw new PersonalAgreementInfoFileObtainFailException();
        }
    }

    @Override
    public DownloadCheckinApplicationFile downloadCheckinApplicationFile() throws CheckinApplicationFileObtainFailException {
        try {
            InputStreamResource resource = new InputStreamResource(new ClassPathResource(checkinPath).getInputStream());
            return new DownloadCheckinApplicationFile(resource);
        } catch (IOException e) {
            throw new CheckinApplicationFileObtainFailException();
        }
    }

    @Override
    public DownloadAffidavitFile downloadAffidavitFile() throws AffidavitFileObtainFailException {
        try {
            InputStreamResource resource = new InputStreamResource(new ClassPathResource(affidavitPath).getInputStream());
            return new DownloadAffidavitFile(resource);
        } catch (IOException e) {
            throw new AffidavitFileObtainFailException();
        }
    }

    @Override
    public DownloadDiseasesFormFile downloadDiseasesFormFile() throws DiseasesFormFileObtainFailException {
        try {
            InputStreamResource resource = new InputStreamResource(new ClassPathResource(diseasesPath).getInputStream());
            return new DownloadDiseasesFormFile(resource);
        } catch (IOException e) {
            throw new DiseasesFormFileObtainFailException();
        }
    }
}
