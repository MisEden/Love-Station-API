package org.eden.lovestation.dto.enums;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;

public class CheckinApplicationStageEnumConverter implements Converter<String, CheckinApplicationStage> {

    @SneakyThrows
    @Override
    public CheckinApplicationStage convert(String source) {
        try {
            return CheckinApplicationStage.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CheckinApplicationStage.UNKNOWN;
        }
    }
}