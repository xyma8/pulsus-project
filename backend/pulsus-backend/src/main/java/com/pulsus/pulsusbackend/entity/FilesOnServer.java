package com.pulsus.pulsusbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "files_on_server")
public class FilesOnServer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name = "size", nullable = false)
    private Float size;

    @Column(name = "path", nullable = false)
    private String path;
}
