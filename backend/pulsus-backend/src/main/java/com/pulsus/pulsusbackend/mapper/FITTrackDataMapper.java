package com.pulsus.pulsusbackend.mapper;

import com.garmin.fit.RecordMesg;
import com.pulsus.pulsusbackend.model.FITTrackData;
import com.pulsus.pulsusbackend.util.NormalizeTrackData;

public class FITTrackDataMapper {

    public static FITTrackData mapToFITTrackData(RecordMesg recordMesg) {
        FITTrackData fitTrackData = new FITTrackData();
        fitTrackData.setTimeStamp(recordMesg.getTimestamp());
        fitTrackData.setPositionLat(NormalizeTrackData.normalizeLat(recordMesg.getPositionLat()));
        fitTrackData.setPositionLong(NormalizeTrackData.normalizeLong(recordMesg.getPositionLong()));
        fitTrackData.setDistance(recordMesg.getDistance());
        fitTrackData.setGrade(recordMesg.getGrade());
        fitTrackData.setTemperature(recordMesg.getTemperature());
        fitTrackData.setEnhancedSpeed(NormalizeTrackData.speedMStoKPH(recordMesg.getEnhancedSpeed()));
        fitTrackData.setEnhancedAltitude(recordMesg.getEnhancedAltitude());
        fitTrackData.setCadence(recordMesg.getCadence());
        fitTrackData.setPower(recordMesg.getPower());
        fitTrackData.setHeartRate(recordMesg.getHeartRate());

        return fitTrackData;
    }
}
