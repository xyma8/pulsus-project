package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.FITFileDto;
import com.pulsus.pulsusbackend.dto.TypeSportDto;
import com.pulsus.pulsusbackend.dto.WorkoutDto;
import com.pulsus.pulsusbackend.entity.TypeSport;
import com.pulsus.pulsusbackend.entity.Workout;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface WorkoutService {
    WorkoutDto createWorkout(MultipartFile file, Long userId);

    WorkoutDto getInfoWorkout(Long userId, Long workoutId);

    WorkoutDto editInfoWorkout(Long userId, Long workoutId, WorkoutDto editedData);

    List<TypeSportDto> getTypesSport();

    FITFileDto getTrackWorkout(Long userId, Long workoutId);

    //void getTrackWorkout(Long userId, Long workoutId);
}
