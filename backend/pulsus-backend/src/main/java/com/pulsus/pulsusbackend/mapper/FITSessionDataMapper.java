package com.pulsus.pulsusbackend.mapper;

import com.garmin.fit.SessionMesg;
import com.pulsus.pulsusbackend.model.FITSessionData;
import com.pulsus.pulsusbackend.util.NormalizeTrackData;
import org.hibernate.Session;

public class FITSessionDataMapper {

    public static FITSessionData mapToFITSessionData(SessionMesg sessionMesg) {
        FITSessionData fitSessionData = new FITSessionData();
        fitSessionData.setTimestamp(sessionMesg.getTimestamp().getDate());
        fitSessionData.setStartTime(sessionMesg.getStartTime().getDate());
        fitSessionData.setSport(sessionMesg.getSport());
        fitSessionData.setTotalEllapsedTime(sessionMesg.getTotalElapsedTime());
        fitSessionData.setTotalTimerTime(sessionMesg.getTotalTimerTime());
        fitSessionData.setTotalDistance(sessionMesg.getTotalDistance());
        fitSessionData.setTotalCalories(sessionMesg.getTotalCalories());
        fitSessionData.setAvgHeartRate(sessionMesg.getAvgHeartRate());
        fitSessionData.setMaxHeartRate(sessionMesg.getMaxHeartRate());
        fitSessionData.setAvgCadence(sessionMesg.getAvgCadence());
        fitSessionData.setMaxCadence(sessionMesg.getMaxCadence());
        fitSessionData.setAvgPower(sessionMesg.getAvgPower());
        fitSessionData.setMaxPower(sessionMesg.getMaxPower());
        fitSessionData.setTotalAscent(sessionMesg.getTotalAscent());
        fitSessionData.setAvgTemperature(sessionMesg.getAvgTemperature());
        fitSessionData.setMaxTemperature(sessionMesg.getMaxTemperature());
        fitSessionData.setEnhancedAvgSpeed(NormalizeTrackData.speedMStoKPH(sessionMesg.getEnhancedAvgSpeed()));
        fitSessionData.setEnhancedMaxSpeed(NormalizeTrackData.speedMStoKPH(sessionMesg.getEnhancedMaxSpeed()));
        fitSessionData.setAvgAltitude(sessionMesg.getAvgAltitude());

        return fitSessionData;
    }
}
