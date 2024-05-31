package com.pulsus.pulsusbackend.service;

import com.garmin.fit.FitDecoder;
import com.garmin.fit.FitMessages;
import com.garmin.fit.RecordMesg;
import com.garmin.fit.SessionMesg;
import com.pulsus.pulsusbackend.dto.FITFileDto;
import com.pulsus.pulsusbackend.dto.TrackSummaryDto;
import com.pulsus.pulsusbackend.entity.FileOnServer;
import com.pulsus.pulsusbackend.exception.InternalServerException;
import com.pulsus.pulsusbackend.mapper.FITSessionDataMapper;
import com.pulsus.pulsusbackend.mapper.FITTrackDataMapper;
import com.pulsus.pulsusbackend.model.FITSessionData;
import com.pulsus.pulsusbackend.model.FITTrackData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrackFileService {

    public FITFileDto readTrack(FileOnServer fileOnServer) {
        FITFileDto fitFileDto;

        //if(fileOnServer.getExtension().equals("fit")) {
        FitMessages fitMessages = getFitMessages(fileOnServer.getPath());
        fitFileDto = readTrackFIT(fitMessages);
        //}
        //else{
            //fitFileDto = readTrackFIT(fileOnServer.getPath()); обрабатываем другие расширения
        //}

        return fitFileDto;
    }

    public TrackSummaryDto readTrackSummary(FileOnServer fileOnServer) {
        TrackSummaryDto trackSummaryDto;

        FitMessages fitMessages = getFitMessages(fileOnServer.getPath());
        trackSummaryDto = readTrackSummaryFIT(fitMessages);

        return trackSummaryDto;
    }

    public FITFileDto readTrack(MultipartFile file) {
        FITFileDto fitFileDto;

        FitMessages fitMessages = getFitMessages(file);
        fitFileDto = readTrackFIT(fitMessages);

        return fitFileDto;
    }

    public TrackSummaryDto readTrackSummary(MultipartFile file) {
        TrackSummaryDto trackSummaryDto;

        FitMessages fitMessages = getFitMessages(file);
        trackSummaryDto = readTrackSummaryFIT(fitMessages);

        return trackSummaryDto;
    }

    private FITFileDto readTrackFIT(FitMessages fitMessages) {

        if(fitMessages.getRecordMesgs().isEmpty()) {
            throw new InternalServerException(".fit file is empty");
        }

        FITFileDto fitFileDto = new FITFileDto();
        List<RecordMesg> recordMesgs = fitMessages.getRecordMesgs();
        List<FITTrackData> fitTrackDataList = new ArrayList<>();

        for(RecordMesg elem : recordMesgs) {
            Float dt = elem.getStanceTime();
            FITTrackData fitTrackData = FITTrackDataMapper.mapToFITTrackData(elem);
            fitTrackDataList.add(fitTrackData);
        }

        fitFileDto.setFitTrackData(fitTrackDataList);

        return fitFileDto;
    }

    private TrackSummaryDto readTrackSummaryFIT(FitMessages fitMessages) {

        if(fitMessages.getRecordMesgs().isEmpty()) {
            throw new InternalServerException(".fit file is empty");
        }

        TrackSummaryDto trackSummaryDto = new TrackSummaryDto();
        List<SessionMesg> sessionMesgs = fitMessages.getSessionMesgs();
        List<FITSessionData> fitSessionDataList = new ArrayList<>();

        for(SessionMesg elem : sessionMesgs) {
            FITSessionData fitSessionData = FITSessionDataMapper.mapToFITSessionData(elem);
            fitSessionDataList.add(fitSessionData);
        }

        trackSummaryDto.setFitSessionData(fitSessionDataList);

        return trackSummaryDto;
    }


    private FitMessages getFitMessages(MultipartFile file) {
        InputStream inputStream;
        FitDecoder fitDecoder = new FitDecoder();
        FitMessages fitMessages;

        try {
            inputStream = file.getInputStream();
            fitMessages = fitDecoder.decode(inputStream);
            inputStream.close();
        } catch (IOException e) {
            System.out.println(e);
            throw new InternalServerException("Internal error");
        }

        return fitMessages;
    }

    private FitMessages getFitMessages(String filePath) {
        InputStream inputStream;
        FitDecoder fitDecoder = new FitDecoder();
        FitMessages fitMessages;

        try {
            inputStream = new FileInputStream(filePath);
            fitMessages = fitDecoder.decode(inputStream);
            inputStream.close();
        } catch (IOException e) {
            System.out.println(e);
            throw new InternalServerException("Internal error");
        }

        return fitMessages;
    }

}
