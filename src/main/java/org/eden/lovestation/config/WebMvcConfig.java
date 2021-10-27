package org.eden.lovestation.config;

import org.eden.lovestation.dto.enums.CheckinApplicationStageEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/storage/**").addResourceLocations("file:storage/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CheckinApplicationStageEnumConverter());
    }
}