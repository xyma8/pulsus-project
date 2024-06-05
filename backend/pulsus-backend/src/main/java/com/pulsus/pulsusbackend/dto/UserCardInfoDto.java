package com.pulsus.pulsusbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserCardInfoDto {

    private Long id;

    private String name;

    private String surname;

    private String login;

    private Long followingCount;

    private Long followersCount;

    private Long workoutsCount;
}
