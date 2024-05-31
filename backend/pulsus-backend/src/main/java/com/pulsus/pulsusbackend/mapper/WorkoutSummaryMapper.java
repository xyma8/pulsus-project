package com.pulsus.pulsusbackend.mapper;

import com.pulsus.pulsusbackend.dto.TrackSummaryDto;
import com.pulsus.pulsusbackend.dto.WorkoutSummaryDto;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;

public class WorkoutSummaryMapper {

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

    public static WorkoutSummary mapToWorkoutSummary(TrackSummaryDto trackSummaryDto) {
        return new WorkoutSummary(
                null,
                trackSummaryDto.getFitSessionData().get(0).getTotalDistance(),
                trackSummaryDto.getFitSessionData().get(0).getTotalEllapsedTime(),
                trackSummaryDto.getFitSessionData().get(0).getTotalTimerTime(),
                trackSummaryDto.getFitSessionData().get(0).getTotalAscent(),
                trackSummaryDto.getFitSessionData().get(0).getStartTime().getDate()
        );
    }
}
