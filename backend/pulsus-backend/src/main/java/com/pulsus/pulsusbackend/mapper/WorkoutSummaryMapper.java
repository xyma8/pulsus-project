package com.pulsus.pulsusbackend.mapper;

import com.pulsus.pulsusbackend.dto.TrackSummaryDto;
import com.pulsus.pulsusbackend.dto.WorkoutSummaryDto;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;
import com.pulsus.pulsusbackend.util.NormalizeTrackData;

public class WorkoutSummaryMapper {

    public static WorkoutSummaryDto mapToWorkoutSummaryDto(WorkoutSummary workoutSummary) {
        return new WorkoutSummaryDto(
                workoutSummary.getId(),
                NormalizeTrackData.roundFloat(NormalizeTrackData.meterToKm(workoutSummary.getTotalDistance()),2),
                NormalizeTrackData.toUsualTimeString(workoutSummary.getTotalEllapsedTime()),
                NormalizeTrackData.toUsualTimeString(workoutSummary.getTotalTimerTime()),
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
                trackSummaryDto.getFitSessionData().get(0).getStartTime()
        );
    }
}
