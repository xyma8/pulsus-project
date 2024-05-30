package com.pulsus.pulsusbackend.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WorkoutSummaryDto {

    private Long id;

    private Float totalDistance;

    private Float totalEllapsedTime;

    private Float totalTimerTime;

    private Float totalAscent;

    private Date startTime;
}
