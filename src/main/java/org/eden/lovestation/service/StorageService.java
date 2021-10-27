package org.eden.lovestation.service;

import org.eden.lovestation.dto.response.DownloadAffidavitFile;
import org.eden.lovestation.dto.response.DownloadCheckinApplicationFile;
import org.eden.lovestation.dto.response.DownloadDiseasesFormFile;
import org.eden.lovestation.dto.response.DownloadPersonalAgreementInfoFile;
import org.eden.lovestation.exception.business.AffidavitFileObtainFailException;
import org.eden.lovestation.exception.business.CheckinApplicationFileObtainFailException;
import org.eden.lovestation.exception.business.DiseasesFormFileObtainFailException;
import org.eden.lovestation.exception.business.PersonalAgreementInfoFileObtainFailException;

public interface StorageService {
    DownloadPersonalAgreementInfoFile downloadPersonalAgreementInfoFile() throws PersonalAgreementInfoFileObtainFailException;

    DownloadCheckinApplicationFile downloadCheckinApplicationFile() throws CheckinApplicationFileObtainFailException;

    DownloadAffidavitFile downloadAffidavitFile() throws AffidavitFileObtainFailException;

    DownloadDiseasesFormFile downloadDiseasesFormFile() throws DiseasesFormFileObtainFailException;
}
