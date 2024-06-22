package com.example.demo.User.Util;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidEmail {
     // Patrón regex para validar el formato de un correo electrónico
     private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    
     private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
 
     // Método para validar el correo electrónico
     public static boolean isValidEmail(String email) {
         if (email == null) {
             return false;
         }
         Matcher matcher = EMAIL_PATTERN.matcher(email);
         return matcher.matches();
     
    }
}
