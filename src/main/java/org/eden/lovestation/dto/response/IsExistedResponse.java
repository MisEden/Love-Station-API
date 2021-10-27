package org.eden.lovestation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IsExistedResponse {

    private boolean isExisted;

    private String message;
}
