package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.FITFileDto;
import com.pulsus.pulsusbackend.dto.GPXFileDto;
import com.pulsus.pulsusbackend.dto.TrackSummaryDto;
import com.pulsus.pulsusbackend.entity.FileOnServer;
import org.springframework.web.multipart.MultipartFile;

public interface FileOnServerService {

    FileOnServer addTrackFile(MultipartFile multipartFile, Long userId);

    //GPXFileDto readTrackGPX(MultipartFile multipartFile);

    //FITFileDto readTrackFIT(String filePath);

    String getTypeSport(MultipartFile multipartFile);

    FITFileDto readTrack(FileOnServer fileOnServer);

    TrackSummaryDto readTrackSummary(FileOnServer fileOnServer);
}
