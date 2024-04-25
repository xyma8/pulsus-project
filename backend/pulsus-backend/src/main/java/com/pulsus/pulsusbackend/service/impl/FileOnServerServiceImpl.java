package com.pulsus.pulsusbackend.service.impl;

import com.pulsus.pulsusbackend.dto.GPXInfoDto;
import com.pulsus.pulsusbackend.entity.FilesOnServer;
import com.pulsus.pulsusbackend.exception.InternalServerException;
import com.pulsus.pulsusbackend.repository.UserRepository;
import com.pulsus.pulsusbackend.service.FIleService;
import com.pulsus.pulsusbackend.service.FileOnServerService;
import lombok.RequiredArgsConstructor;
import org.alternativevision.gpx.GPXParser;
import org.alternativevision.gpx.beans.GPX;
import org.alternativevision.gpx.beans.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class FileOnServerServiceImpl implements FileOnServerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FIleService fileService;


    @Override
    public FilesOnServer addFile(MultipartFile file, String path) {
        FilesOnServer fileOnServer = new FilesOnServer();
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        fileOnServer.setExtension(extension);
        fileOnServer.setSize(file.getSize());
        fileOnServer.setPath(path);

        return fileOnServer;
    }

    @Override
    public GPXInfoDto ReadGPX(MultipartFile file) {
        GPXParser gpxParser = new GPXParser();
        FileInputStream in;
        GPX gpxFile;
        try {
            in = new FileInputStream("C:/Users/basce/Desktop/pulsus-project/frontend/pulsus-frontend/public/uploads/users/14/activities/2024_04_25Ride.gpx");
            gpxFile = gpxParser.parseGPX(in);
        }catch (Exception e) {
            System.out.println(e);
            throw new InternalServerException("Internal error ");
        }

        // System.out.println(gpxFile.getCreator());//null
       // System.out.println(gpxFile.getWaypoints());//null
       // System.out.println(gpxFile.getRoutes());//null
        System.out.println(gpxFile.getTracks());
        HashSet<Track> tracks = gpxFile.getTracks();
        for (Track element : tracks) {
            System.out.println(element.getTrackPoints());

        }
       // System.out.println(gpxFile.getExtensionData());//null
        return null;
    }
}
