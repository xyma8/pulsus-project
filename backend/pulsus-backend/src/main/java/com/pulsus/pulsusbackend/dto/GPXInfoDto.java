package com.pulsus.pulsusbackend.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

public class GPXInfoDto {

    private List<String> maps;

    private String name;
}
