package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.GPXInfoDto;
import com.pulsus.pulsusbackend.entity.FilesOnServer;
import org.springframework.web.multipart.MultipartFile;

public interface FileOnServerService {

    FilesOnServer addFile(MultipartFile multipartFile, String path);

    GPXInfoDto ReadGPX(MultipartFile multipartFile);
}
