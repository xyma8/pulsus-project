package com.pulsus.pulsusbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Data
@Table(name = "files_on_server")
public class FileOnServer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "filehash", nullable = false)
    private String filehash;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "path", nullable = false)
    private String path;

    @OneToOne(mappedBy = "fileWorkout")
    private Workout workout;

    @ManyToMany(mappedBy = "photos")
    private Collection<Workout> workouts = new ArrayList<>();

    public FileOnServer(Long id, String filehash, String extension, Long size, String path) {
        this.id = id;
        this.filehash = filehash;
        this.extension = extension;
        this.size = size;
        this.path = path;
    }
}

