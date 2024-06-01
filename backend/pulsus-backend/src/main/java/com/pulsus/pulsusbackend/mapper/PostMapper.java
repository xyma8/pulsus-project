package com.pulsus.pulsusbackend.mapper;

import com.pulsus.pulsusbackend.dto.PostDto;
import com.pulsus.pulsusbackend.dto.WorkoutDto;
import com.pulsus.pulsusbackend.dto.WorkoutSummaryDto;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;

public class PostMapper {

    public static PostDto mapToPostDto(WorkoutDto workoutDto, WorkoutSummaryDto workoutSummaryDto) {
        return new PostDto(
                workoutDto.getName(),
                workoutDto.getTypeSport(),
                workoutSummaryDto.getTotalDistance(),
                workoutSummaryDto.getTotalEllapsedTime(),
                workoutSummaryDto.getTotalTimerTime(),
                workoutSummaryDto.getTotalAscent(),
                workoutSummaryDto.getStartTime()
        );
    }

    public static PostDto mapToPostDto(Workout workout, WorkoutSummary workoutSummary) {
        return new PostDto(
                workout.getName(),
                workout.getTypeSports(),
                workoutSummary.getTotalDistance(),
                workoutSummary.getTotalEllapsedTime(),
                workoutSummary.getTotalTimerTime(),
                workoutSummary.getTotalAscent(),
                workoutSummary.getStartTime()
        );
    }
}
