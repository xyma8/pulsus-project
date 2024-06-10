package com.pulsus.pulsusbackend.mapper;

import com.pulsus.pulsusbackend.dto.*;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;
import com.pulsus.pulsusbackend.util.NormalizeTrackData;
import org.springframework.security.core.parameters.P;

public class PostMapper {

    /*
    public static PostDto mapToPostDto(UserDto userDto, WorkoutDto workoutDto, WorkoutSummaryDto workoutSummaryDto, WorkoutLikeDto workoutLikeDto) {
        return new PostDto(
                workoutDto.getId(),
                userDto.getId(),
                userDto.getName(),
                workoutDto.getName(),
                workoutDto.getTypeSport(),
                NormalizeTrackData.roundFloat(NormalizeTrackData.meterToKm(workoutSummaryDto.getTotalDistance()),2),
                NormalizeTrackData.toUsualTime(workoutSummaryDto.getTotalEllapsedTime()),
                workoutSummaryDto.getTotalAscent(),
                workoutSummaryDto.getStartTime(),
                new WorkoutLikeDto(workoutLikeDto.getIsLike(), workoutLikeDto.getCountLikes())
        );
    }
    */

    public static PostDto mapToPostDto(User user, Workout workout, WorkoutSummary workoutSummary, WorkoutLikeDto workoutLikeDto) {
        return new PostDto(
                workout.getId(),
                user.getId(),
                user.getName(),
                workout.getName(),
                workout.getTypeSports(),
                NormalizeTrackData.roundFloat(NormalizeTrackData.meterToKm(workoutSummary.getTotalDistance()),2),
                NormalizeTrackData.toUsualTime(workoutSummary.getTotalEllapsedTime()),
                workoutSummary.getTotalAscent(),
                workoutSummary.getStartTime(),
                new WorkoutLikeDto(workoutLikeDto.getIsLike(), workoutLikeDto.getCountLikes())
        );
    }
}
