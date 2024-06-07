package com.pulsus.pulsusbackend.dto;

import com.pulsus.pulsusbackend.model.UsualTime;
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

    private Long workoutId;

    private Long userId;

    private String username;

    private String name;

    private String typeSport;

    private Float totalDistance;

    private UsualTime totalTime;

    private Integer totalAscent;

    private Date startTime;

    private WorkoutLikeDto likes;
}
