package com.pulsus.pulsusbackend.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final Pattern namePattern = Pattern.compile("^[а-яА-ЯёЁ]+$");

    private static final Pattern surnamePattern = Pattern.compile("^[а-яА-ЯЁё]+(?:-[а-яА-ЯЁё]+)?$");

    private static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private static final Pattern loginPattern = Pattern.compile("^[a-zA-Z0-9_]+$");

    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+\\\\|\\[\\]{};:'\",./?]).{8,}$");


    public static boolean isValidName(String name) {
        Matcher matcher = namePattern.matcher(name);

        if(!matcher.matches()) return false;

        if(name.length() < 2) return false;

        if(name.length() > 20) return false;

        return true;
    }

    public static boolean isValidSurname(String surname) {
        Matcher matcher = surnamePattern.matcher(surname);

        if(!matcher.matches()) return false;

        if(surname.length() < 2) return false;

        if(surname.length() > 20) return false;

        return true;
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);

        if(!matcher.matches()) return false;

        if(email.length() > 254) return false;

        return true;
    }

    public static boolean isValidLogin(String login) {
        Matcher matcher = loginPattern.matcher(login);

        if(!matcher.matches()) return false;

        if(login.length() < 4) return false;

        if(login.length() > 24) return false;

        return true;
    }

    public static boolean isValidPassword(String password) {
        Matcher matcher = passwordPattern.matcher(password);

        if(!matcher.matches()) return false;

        if(password.length() < 8) return false;

        return true;
    }
}
