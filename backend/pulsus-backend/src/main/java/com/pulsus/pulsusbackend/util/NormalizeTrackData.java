package com.pulsus.pulsusbackend.util;

import com.pulsus.pulsusbackend.model.UsualTime;

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
        if(speedMS == null) return null;

        Float speedKPH = speedMS * 3.6f;

        return speedKPH;
    }

    public static Float roundFloat(Float value, int places) {
        if(value == null) return null;
        
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (float) tmp / factor;
    }

    public static UsualTime toUsualTime(Float totalSec) {
        if(totalSec == null) return null;

        Integer hours = (int)(totalSec / 3600);
        Integer minutes = (int)(totalSec % 3600) / 60;
        Integer seconds = (int)(totalSec % 3600) % 60;

        return new UsualTime(hours, minutes, seconds);
    }

    public static String toUsualTimeString(Float totalSec) {
        if(totalSec == null) return null;

        Integer hours = (int)(totalSec / 3600);
        Integer minutes = (int)(totalSec % 3600) / 60;
        Integer seconds = (int)(totalSec % 3600) % 60;


        String time = "";
        if(hours > 0) time = time + (hours>9 ? hours : "0" + hours) + ":";

        if(minutes > 0) time = time + (minutes>9 ? minutes : "0" + minutes) + ":";

        time = time + (seconds>9 ? seconds : "0" + seconds);

        return time;
    }

    public static Float meterToKm(Float meter) {
        if(meter == null) return null;

        Float km = meter / 1000;

        return km;
    }

}
