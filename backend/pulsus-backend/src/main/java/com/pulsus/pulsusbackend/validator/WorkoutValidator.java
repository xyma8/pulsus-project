package com.pulsus.pulsusbackend.validator;

import com.pulsus.pulsusbackend.entity.TypeSport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkoutValidator {

    private static final Pattern pattern = Pattern.compile("^[^<>\\/]*$");

    public static boolean isValidName(String name) {
        Matcher matcher = pattern.matcher(name);

        if(!matcher.matches()) return false;

        if(name.length() < 2) return false;

        if(name.length() > 30) return false;

        return true;
    }

    public static boolean isValidDescription(String description) {
        Matcher matcher = pattern.matcher(description);

        if(!matcher.matches()) return false;

        if(description.length() > 250) return false;

        return true;
    }

    public static boolean isValidAccessType(Integer accessType) {
        if(accessType<0 || accessType>2) return false;

        return true;
    }

    public static boolean isValidTypeSport(String typeSport, List<TypeSport> typeSportList) {
        for(TypeSport type: typeSportList) {
            if(typeSport.equals(type.getName())) return true;
        }

        return false;
    }
}
