package com.pulsus.pulsusbackend.model;

import com.garmin.fit.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FITTrackData {

    private DateTime timeStamp;
    private Float positionLat;
    private Float positionLong;
    private Float distance;
    private Float grade;
    private Byte temperature;
    private Float enhancedSpeed;
    private Float enhancedAltitude;
    private Short cadence;
    private Integer power;
    private Short heartRate;


}
