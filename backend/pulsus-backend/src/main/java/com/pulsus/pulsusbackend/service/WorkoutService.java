package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.entity.Workout;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface WorkoutService {
    Workout createWorkout(MultipartFile file, Long userId);
}
