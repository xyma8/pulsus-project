package com.pulsus.pulsusbackend.mapper;

import com.pulsus.pulsusbackend.dto.PostDto;
import com.pulsus.pulsusbackend.dto.UserDto;
import com.pulsus.pulsusbackend.dto.WorkoutDto;
import com.pulsus.pulsusbackend.dto.WorkoutSummaryDto;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;
import com.pulsus.pulsusbackend.util.NormalizeTrackData;
import org.springframework.security.core.parameters.P;

public class PostMapper {

    public static PostDto mapToPostDto(UserDto userDto, WorkoutDto workoutDto, WorkoutSummaryDto workoutSummaryDto) {
        return new PostDto(
                workoutDto.getId(),
                userDto.getId(),
                userDto.getName(),
                workoutDto.getName(),
                workoutDto.getTypeSport(),
                NormalizeTrackData.roundFloat(NormalizeTrackData.meterToKm(workoutSummaryDto.getTotalDistance()),2),
                NormalizeTrackData.toUsualTime(workoutSummaryDto.getTotalEllapsedTime()),
                workoutSummaryDto.getTotalAscent(),
                workoutSummaryDto.getStartTime()
        );
    }

    public static PostDto mapToPostDto(User user, Workout workout, WorkoutSummary workoutSummary) {
        return new PostDto(
                workout.getId(),
                user.getId(),
                user.getName(),
                workout.getName(),
                workout.getTypeSports(),
                NormalizeTrackData.roundFloat(NormalizeTrackData.meterToKm(workoutSummary.getTotalDistance()),2),
                NormalizeTrackData.toUsualTime(workoutSummary.getTotalEllapsedTime()),
                workoutSummary.getTotalAscent(),
                workoutSummary.getStartTime()
        );
    }
}
