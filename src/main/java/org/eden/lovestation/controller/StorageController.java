package org.eden.lovestation.controller;

import org.eden.lovestation.dto.response.DownloadAffidavitFile;
import org.eden.lovestation.dto.response.DownloadCheckinApplicationFile;
import org.eden.lovestation.dto.response.DownloadDiseasesFormFile;
import org.eden.lovestation.dto.response.DownloadPersonalAgreementInfoFile;
import org.eden.lovestation.exception.business.AffidavitFileObtainFailException;
import org.eden.lovestation.exception.business.CheckinApplicationFileObtainFailException;
import org.eden.lovestation.exception.business.DiseasesFormFileObtainFailException;
import org.eden.lovestation.exception.business.PersonalAgreementInfoFileObtainFailException;
import org.eden.lovestation.service.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/storage")
public class StorageController {
    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value = "/personal-agreement-info")
    public ResponseEntity<Resource> downloadPersonalAgreementInfoFile() throws PersonalAgreementInfoFileObtainFailException {
        DownloadPersonalAgreementInfoFile metaData = storageService.downloadPersonalAgreementInfoFile();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=personal_agreement_info.docx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(metaData.getResource());
    }

    @GetMapping(value = "/checkin-application")
    public ResponseEntity<Resource> downloadCheckinApplicationFile() throws CheckinApplicationFileObtainFailException {
        DownloadCheckinApplicationFile metaData = storageService.downloadCheckinApplicationFile();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=checkin.docx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(metaData.getResource());
    }

    @GetMapping(value = "/affidavit")
    public ResponseEntity<Resource> downloadAffidavitFile() throws AffidavitFileObtainFailException {
        DownloadAffidavitFile metaData = storageService.downloadAffidavitFile();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=affidavit.docx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(metaData.getResource());
    }

    @GetMapping(value = "/diseases-form")
    public ResponseEntity<Resource> downloadDiseasesFormFile() throws DiseasesFormFileObtainFailException {
        DownloadDiseasesFormFile metaData = storageService.downloadDiseasesFormFile();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=diseases_form.docx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(metaData.getResource());
    }
}
