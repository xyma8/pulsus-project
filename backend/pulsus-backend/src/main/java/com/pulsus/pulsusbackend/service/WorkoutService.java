package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.WorkoutDto;
import com.pulsus.pulsusbackend.entity.Workout;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface WorkoutService {
    WorkoutDto createWorkout(MultipartFile file, Long userId);

    WorkoutDto getInfoWorkout(Long userId, Long workoutId);

    //void getTrackWorkout(Long userId, Long workoutId);
}
