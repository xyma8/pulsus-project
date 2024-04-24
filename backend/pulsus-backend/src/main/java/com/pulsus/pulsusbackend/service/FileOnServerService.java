package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.entity.FilesOnServer;
import org.springframework.web.multipart.MultipartFile;

public interface FileOnServerService {

    FilesOnServer createFile(MultipartFile multipartFile, String path);
}
