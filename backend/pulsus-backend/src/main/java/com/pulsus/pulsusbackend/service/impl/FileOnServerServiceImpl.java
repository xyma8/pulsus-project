package com.pulsus.pulsusbackend.service.impl;

import com.pulsus.pulsusbackend.entity.FilesOnServer;
import com.pulsus.pulsusbackend.repository.UserRepository;
import com.pulsus.pulsusbackend.service.FIleService;
import com.pulsus.pulsusbackend.service.FileOnServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileOnServerServiceImpl implements FileOnServerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FIleService fileService;


    @Override
    public FilesOnServer createFile(MultipartFile file, String path) {
        FilesOnServer fileOnServer = new FilesOnServer();
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        fileOnServer.setExtension(extension);
        fileOnServer.setSize(file.getSize());
        fileOnServer.setPath(path);

        return fileOnServer;
    }
}
