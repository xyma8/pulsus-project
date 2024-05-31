package com.pulsus.pulsusbackend.repository;

import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutSummaryRepository extends JpaRepository<WorkoutSummary, Long> {
}
