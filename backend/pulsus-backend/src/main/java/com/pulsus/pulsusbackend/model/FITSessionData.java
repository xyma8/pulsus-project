package com.pulsus.pulsusbackend.model;

import com.garmin.fit.DateTime;
import com.garmin.fit.Sport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FITSessionData {

    private DateTime timestamp;
    private DateTime startTime;
    private Sport sport;
    private Float totalEllapsedTime;
    private Float totalTimerTime;
    private Float totalDistance;
    private Integer totalCalories;
    private Short avgHeartRate;
    private Short maxHeartRate;
    private Short avgCadence;
    private Short maxCadence;
    private Integer avgPower;
    private Integer maxPower;
    private Integer totalAscent;
    private Byte avgTemperature;
    private Byte maxTemperature;
    private Float enhancedAvgSpeed;
    private Float enhancedMaxSpeed;
    private Float avgAltitude;

}
