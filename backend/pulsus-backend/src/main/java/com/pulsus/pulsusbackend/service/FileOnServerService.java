package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.FITFileDto;
import com.pulsus.pulsusbackend.dto.GPXFileDto;
import com.pulsus.pulsusbackend.entity.FileOnServer;
import org.springframework.web.multipart.MultipartFile;

public interface FileOnServerService {

    FileOnServer addTrackFile(MultipartFile multipartFile, Long userId);

    GPXFileDto readGPX(MultipartFile multipartFile);

    FITFileDto readFIT(MultipartFile multipartFile);
}
