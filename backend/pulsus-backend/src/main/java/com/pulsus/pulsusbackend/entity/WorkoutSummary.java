package com.pulsus.pulsusbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "workouts_summary")
public class WorkoutSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_distance", nullable = false)
    private Float totalDistance;

    @Column(name = "total_ellapsed_time", nullable = false)
    private Float totalEllapsedTime;

    @Column(name = "total_timer_time", nullable = false)
    private Float totalTimerTime;

    @Column(name = "total_ascent", nullable = false)
    private Integer totalAscent;

    @Column(name = "start_time", nullable = false)
    private Date startTime;
}
