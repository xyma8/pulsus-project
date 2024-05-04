package com.pulsus.pulsusbackend.service.impl;

import com.garmin.fit.*;
import com.pulsus.pulsusbackend.dto.FITFileDto;
import com.pulsus.pulsusbackend.dto.GPXFileDto;
import com.pulsus.pulsusbackend.entity.FileOnServer;
import com.pulsus.pulsusbackend.exception.InternalServerException;
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

    @Override
    public GPXFileDto readGPX(MultipartFile file) {
        /*
        GPXParser gpxParser = new GPXParser();
        FileInputStream in;
        GPX gpxFile;
        try {
            in = new FileInputStream("C:/Users/basce/Desktop/pulsus-project/frontend/pulsus-frontend/public/uploads/users/14/workouts/2024_04_25Ride.gpx");
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
            System.out.println(element.getExtensionData());
        }

         */
        return null;
    }

    @Override
    public FITFileDto readFIT(MultipartFile file) {
        InputStream isFile;
        FitDecoder fitDecoder = new FitDecoder();
        FitMessages fitMessages;

        try {
            isFile = file.getInputStream();
            fitMessages = fitDecoder.decode(isFile);
            //isFile.close();
        }catch (Exception e){
            System.out.println(e);
            throw new InternalServerException("Internal error");
        }

        if(fitMessages.getRecordMesgs().isEmpty()) {
            throw new InternalServerException(".fit file is empty");
        }

        List<SessionMesg> sessionMesgs = fitMessages.getSessionMesgs();
        List<RecordMesg> recordMesgs = fitMessages.getRecordMesgs();

        FITFileDto fitFileDto = new FITFileDto();
        List<FITSessionData> fitSessionDataList = new ArrayList<>();
        List<FITTrackData> fitTrackDataList = new ArrayList<>();

        for(SessionMesg elem : sessionMesgs) {
            FITSessionData fitSessionData = FITSessionDataMapper.mapToFITSessionData(elem);
            fitSessionDataList.add(fitSessionData);
        }

        for(RecordMesg elem : recordMesgs) {
            FITTrackData fitTrackData = FITTrackDataMapper.mapToFITTrackData(elem);
            fitTrackDataList.add(fitTrackData);
        }

        fitFileDto.setFitSessionData(fitSessionDataList);
        fitFileDto.setFitTrackData(fitTrackDataList);

        return fitFileDto;
    }

    @Override
    public String getTypeSport(MultipartFile file) {
        InputStream isFile;
        FitDecoder fitDecoder = new FitDecoder();
        FitMessages fitMessages;

        try {
            isFile = file.getInputStream();
            fitMessages = fitDecoder.decode(isFile);
        }catch (Exception e){
            System.out.println(e);
            throw new InternalServerException("Internal error");
        }

        if(fitMessages.getRecordMesgs().isEmpty()) {
            throw new InternalServerException(".fit file is empty");
        }

        String typeSport = fitMessages.getSessionMesgs().get(0).getSport().toString();

        return typeSport;
    }

    private void checkExtensionTrackFile(String extension) {
        if(!extension.equals("fit") &&
            !extension.equals("gpx")) {
            throw new InternalServerException("Incorrect file extension");
        }
    }
}
