package com.pulsus.pulsusbackend.dto;

import com.pulsus.pulsusbackend.model.FITSessionData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TrackSummaryDto {
    private List<FITSessionData> fitSessionData;
}
