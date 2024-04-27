package com.pulsus.pulsusbackend.dto;

import com.garmin.fit.RecordMesg;
import com.garmin.fit.SessionMesg;
import com.garmin.fit.Sport;
import com.pulsus.pulsusbackend.model.FITSessionData;
import com.pulsus.pulsusbackend.model.FITTrackData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FITFileDto {

    private List<FITSessionData> fitSessionData;
    private List<FITTrackData> fitTrackData;

    //private List<SessionMesg> sessionMesgs;
}
