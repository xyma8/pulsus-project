package com.pulsus.pulsusbackend.repository;

import com.pulsus.pulsusbackend.entity.Subscription;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.entity.WorkoutLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutLikeRepository extends JpaRepository<WorkoutLike, Long> {

    WorkoutLike findByUserAndWorkout(User user, Workout workout);

    List<WorkoutLike> findByWorkout(Workout workout);

    Long countByWorkout(Workout workout);
}
