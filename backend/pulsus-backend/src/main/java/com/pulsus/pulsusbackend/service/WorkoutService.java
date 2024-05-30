package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.*;
import com.pulsus.pulsusbackend.entity.TypeSport;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface WorkoutService {
    WorkoutDto createWorkout(Long userId, MultipartFile file);

    WorkoutDto getInfoWorkout(Long userId, Long workoutId);

    WorkoutSummaryDto getSummaryWorkout(Long userId, Long workoutId);

    WorkoutDto editInfoWorkout(Long userId, Long workoutId, WorkoutDto editedData);

    List<TypeSportDto> getTypesSport();

    FITFileDto getTrackWorkout(Long userId, Long workoutId);

    TrackSummaryDto getTrackSummaryWorkout(Long userId, Long workoutId);

}
