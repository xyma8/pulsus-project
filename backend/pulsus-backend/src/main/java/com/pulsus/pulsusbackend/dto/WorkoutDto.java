package com.pulsus.pulsusbackend.dto;

import com.pulsus.pulsusbackend.entity.FileOnServer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WorkoutDto {

    private Long id;

    private Long userId;

    private String name;

    private String description;

    private String typeSport;

    private Integer accessType;

    private Date timestamp;

}
