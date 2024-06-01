package com.pulsus.pulsusbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostDto {

    private String name;

    private String typeSport;

    private Float totalDistance;

    private Float totalEllapsedTime;

    private Float totalTimerTime;

    private Integer totalAscent;

    private Date startTime;
}
