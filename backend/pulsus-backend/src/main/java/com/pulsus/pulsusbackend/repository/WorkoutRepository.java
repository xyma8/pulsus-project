package com.pulsus.pulsusbackend.repository;

import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Long countByUser(User userId);

    @Procedure("get_workouts_for_posts")
    List<Long> getWorkoutsForPosts(String userIds, Integer page, Integer size);

    List<Workout> findByUser(User user);
}
