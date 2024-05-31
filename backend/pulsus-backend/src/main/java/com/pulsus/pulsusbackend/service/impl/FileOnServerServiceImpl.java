package com.pulsus.pulsusbackend.service.impl;

import com.garmin.fit.*;
import com.pulsus.pulsusbackend.dto.FITFileDto;
import com.pulsus.pulsusbackend.dto.GPXFileDto;
import com.pulsus.pulsusbackend.dto.TrackSummaryDto;
import com.pulsus.pulsusbackend.entity.FileOnServer;
import com.pulsus.pulsusbackend.exception.ConflictException;
import com.pulsus.pulsusbackend.exception.InternalServerException;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.mapper.FITSessionDataMapper;
import com.pulsus.pulsusbackend.mapper.FITTrackDataMapper;
import com.pulsus.pulsusbackend.model.FITSessionData;
import com.pulsus.pulsusbackend.model.FITTrackData;
import com.pulsus.pulsusbackend.repository.FileOnServerRepository;
import com.pulsus.pulsusbackend.service.FileService;
import com.pulsus.pulsusbackend.service.FileOnServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileOnServerServiceImpl implements FileOnServerService {

    @Autowired
    private FileOnServerRepository fileOnServerRepository;

    @Autowired
    private FileService fileService;


    @Override
    public FileOnServer addTrackFile(MultipartFile file, Long userId) {
        FileOnServer fileOnServer = new FileOnServer();
        String filehash;
        try{
            filehash = fileService.getSHA256(file);
        }catch (Exception e) {
            System.out.println(e);
            throw new InternalServerException("Internal error");
        }
        Optional<FileOnServer> checkHash = fileOnServerRepository.findByFilehash(filehash);
        if(checkHash.isPresent()){
            System.out.println("This track file already exists");
            throw new ConflictException("This track file already exists");
        };

        fileOnServer.setFilehash(filehash);
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        checkExtensionTrackFile(extension);
        fileOnServer.setExtension(extension);
        fileOnServer.setSize(file.getSize());
        String path;
        try {
            path = fileService.uploadTrackFile(userId, file);
        }catch (Exception e) {
            throw new InternalServerException("Internal error");
        }
        fileOnServer.setPath(path);

        fileOnServerRepository.save(fileOnServer);
        return fileOnServer;
    }

    private void checkExtensionTrackFile(String extension) {
        if(!extension.equals("fit") &&
            !extension.equals("gpx")) {
            throw new InternalServerException("Incorrect file extension");
        }
    }
}
