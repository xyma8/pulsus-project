package com.pulsus.pulsusbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "files_on_server")
public class FileOnServer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "path", nullable = false)
    private String path;

    @OneToOne(mappedBy = "filesOnServer")
    private Workout workout;

    public FileOnServer(Long id, String extension, Long size, String path) {
        this.id = id;
        this.extension = extension;
        this.size = size;
        this.path = path;
    }
}

