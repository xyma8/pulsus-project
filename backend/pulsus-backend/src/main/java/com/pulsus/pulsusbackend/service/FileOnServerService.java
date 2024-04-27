package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.FITFileDto;
import com.pulsus.pulsusbackend.dto.GPXFileDto;
import com.pulsus.pulsusbackend.entity.FilesOnServer;
import org.springframework.web.multipart.MultipartFile;

public interface FileOnServerService {

    FilesOnServer addFile(MultipartFile multipartFile, String path);

    GPXFileDto readGPX(MultipartFile multipartFile);

    FITFileDto readFIT(MultipartFile multipartFile);
}
