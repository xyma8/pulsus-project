package com.pulsus.pulsusbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedStoredProcedureQuery(name = "workouts.get_workouts_for_posts", procedureName = "get_workouts_for_posts", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_userIds", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_page", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_size", type = Integer.class)})
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "type_sport", nullable = false)
    private String typeSports; //надо переименовать на typeSport

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "summary", referencedColumnName = "id")
    private WorkoutSummary summary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_workout", referencedColumnName = "id")
    private FileOnServer fileWorkout;

    @Column(name = "access_type", nullable = false)
    private Integer accessType;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @ManyToMany
    @JoinTable(
            name = "workouts_photos",
            joinColumns = @JoinColumn(name = "workout"),
            inverseJoinColumns = @JoinColumn(name = "photo")
    )
    private Collection<FileOnServer> photos = new ArrayList<>();

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private Collection<WorkoutLike> likes = new ArrayList<>();
}
