package org.eden.lovestation.service;

import org.eden.lovestation.dto.projection.FirmName;
import org.eden.lovestation.exception.business.*;

import java.util.List;

public interface FirmService {

    List<FirmName> getFirmNames() throws FirmNotFoundException;
}
