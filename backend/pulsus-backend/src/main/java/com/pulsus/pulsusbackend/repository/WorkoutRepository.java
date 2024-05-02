package com.pulsus.pulsusbackend.repository;

import com.pulsus.pulsusbackend.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

}
