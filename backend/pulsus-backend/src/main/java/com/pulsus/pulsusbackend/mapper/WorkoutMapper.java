package com.pulsus.pulsusbackend.mapper;

import com.pulsus.pulsusbackend.dto.WorkoutDto;
import com.pulsus.pulsusbackend.dto.WorkoutSummaryDto;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;

public class WorkoutMapper {

    public static WorkoutDto mapToWorkoutDto(Workout workout) {
        return new WorkoutDto(
                workout.getId(),
                workout.getName(),
                workout.getDescription(),
                workout.getTypeSports(),
                workout.getAccessType(),
                workout.getTimestamp()
        );
    }

    public static WorkoutSummaryDto mapToWorkoutSummaryDto(WorkoutSummary workoutSummary) {
        return new WorkoutSummaryDto(
                workoutSummary.getId(),
                workoutSummary.getTotalDistance(),
                workoutSummary.getTotalEllapsedTime(),
                workoutSummary.getTotalTimerTime(),
                workoutSummary.getTotalAscent(),
                workoutSummary.getStartTime()
        );
    }
}
