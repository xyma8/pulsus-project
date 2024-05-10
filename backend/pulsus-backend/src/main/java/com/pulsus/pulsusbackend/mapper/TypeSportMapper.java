package com.pulsus.pulsusbackend.mapper;

import com.pulsus.pulsusbackend.dto.TypeSportDto;
import com.pulsus.pulsusbackend.entity.TypeSport;

public class TypeSportMapper {

    public static TypeSportDto mapToTypeSportDto(TypeSport typeSport) {
        return new TypeSportDto(
                typeSport.getId(),
                typeSport.getName()
        );
    }
}
