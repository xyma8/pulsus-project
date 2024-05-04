package com.pulsus.pulsusbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "types_sports")
public class TypeSport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    //@OneToMany(mappedBy = "typeSports", cascade = CascadeType.ALL)
    //private Collection<Workout> workouts = new ArrayList<>();

    public TypeSport(Integer id, String description) {
        this.id = id;
        this.description = description;
    }
}
