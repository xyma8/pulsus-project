package com.pulsus.pulsusbackend.mapper;

import com.pulsus.pulsusbackend.dto.WorkoutDto;
import com.pulsus.pulsusbackend.entity.Workout;

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
}
