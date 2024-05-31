package com.pulsus.pulsusbackend.util;

public class NormalizeTrackData {

    public static Float normalizeLat(Integer posLat) {
        if(posLat == null) return null;

        double positionLat = posLat * (180.0 / Math.pow(2, 31));
        Float normalLat = (float) positionLat;

        return normalLat;
    }

    public static Float normalizeLong(Integer posLong) {
        if(posLong == null) return null;

        double positionLong = posLong * (180.0 / Math.pow(2, 31));
        Float normalLong = (float) positionLong;

        return normalLong;
    }

    public static Float speedMStoKPH(Float speedMS) {
        Float speedKPH = speedMS * 3.6f;

        return speedKPH;
    }

    public static Float roundFloat(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (float) tmp / factor;
    }
}
