package com.pulsus.pulsusbackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class FileStorageConfig {

    @Value("${upload.path}")
    private String path;

    public String getPath() {
        return path;
    }

}
