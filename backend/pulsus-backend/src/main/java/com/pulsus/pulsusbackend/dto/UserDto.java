package com.pulsus.pulsusbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String login;

    private String password;

    private Long profile_picture;

    private int role;
}
