package com.pulsus.pulsusbackend.repository;

import com.pulsus.pulsusbackend.entity.Subscription;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.entity.Workout;
import com.pulsus.pulsusbackend.entity.WorkoutSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutSummaryRepository extends JpaRepository<WorkoutSummary, Long> {



}
