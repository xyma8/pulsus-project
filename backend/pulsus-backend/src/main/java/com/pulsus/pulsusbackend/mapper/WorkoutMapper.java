package com.pulsus.pulsusbackend.mapper;

import com.pulsus.pulsusbackend.dto.WorkoutDto;
import com.pulsus.pulsusbackend.dto.WorkoutSummaryDto;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;

public class WorkoutMapper {

    public static WorkoutDto mapToWorkoutDto(Workout workout) {
        return new WorkoutDto(
                workout.getId(),
                workout.getUser().getId(),
                workout.getName(),
                workout.getDescription(),
                workout.getTypeSports(),
                workout.getAccessType(),
                workout.getTimestamp()
        );
    }

}
