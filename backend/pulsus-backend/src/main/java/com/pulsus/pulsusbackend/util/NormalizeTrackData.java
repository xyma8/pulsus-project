package com.pulsus.pulsusbackend.util;

public class NormalizeTrackData {

    public static Float normalizeLat(Integer posLat) {
        double positionLat = posLat * (180.0 / Math.pow(2, 31));
        Float normalLat = (float) positionLat;

        return normalLat;
    }

    public static Float normalizeLong(Integer posLong) {
        double positionLong = posLong * (180.0 / Math.pow(2, 31));
        Float normalLong = (float) positionLong;

        return normalLong;
    }

    public static Float speedMStoKPH(Float speedMS) {
        Float speedKPH = speedMS * 3.6f;

        return speedKPH;
    }
}
