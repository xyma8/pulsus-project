package com.pulsus.pulsusbackend.service;
import com.pulsus.pulsusbackend.dto.WorkoutLikeDto;
import org.springframework.stereotype.Service;

@Service
public interface WorkoutLikeService {

    WorkoutLikeDto checkLike(Long userId, Long workoutId);

    WorkoutLikeDto changeLike(Long userId, Long workoutId);

    Long getLikesCount(Long userId, Long workoutId);
}
