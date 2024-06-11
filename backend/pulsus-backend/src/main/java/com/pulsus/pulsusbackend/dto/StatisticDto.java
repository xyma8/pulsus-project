package com.pulsus.pulsusbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StatisticDto {

    private Long countWorkouts;

    private Float distance;

    private Integer ascent;

    private String time;

}
