package itmo.blps.lab1.util;

import itmo.blps.lab1.dto.request.RegisterRequest;

import java.util.regex.Pattern;

public final class Validator {

    private Validator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.length() >= 5 && name.matches("^[a-zA-Zа-яА-Я ]+$");
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        return password.length() >= 8 &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[A-ZА-Я].*") &&
                password.matches(".*[a-zа-я].*");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        return phoneNumber.matches("^\\+?7\\d{10}$");
    }
    public static String isValidPerson(RegisterRequest registerRequest){
        if (!isValidEmail(registerRequest.getEmail())){
            return "Имеил должен соответствовать международному стандарту";
        }
        if(!isValidName(registerRequest.getName())){
            return "Имя должно быть адекватным";
        }
        if(!isValidPassword(registerRequest.getPassword())){
            return "Пароль должен быть адекватным";
        }
        if(!isValidPhoneNumber(registerRequest.getPhoneNumber())){
            return "введите номер в формате российского региона";
        }
        return "";
    }
}