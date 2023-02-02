package com.lap.roomplanningsystem.app;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Die Passwordklasse enthält Methoden zum krypted und entkrypted von Passwörten, sowie überprüft
 * ob das übernommene Passwort den gewünschten Anforderungen entspricht.
 **/

public class Password {

    public static String regex_pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,24}$";
    public static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public Password() {

    }

    /**
     * Methode validate(): übernimmt als PARAM einen String und überprüft, ob der String den Anforderungen entspricht.
     * Rückgabewert: boolscher Wert
     **/
    public static boolean validate(String password){

        Pattern pattern = Pattern.compile(regex_pattern);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    /**
     * Methode hash(): übernimmt als PARAM einen String und krypted den String mit der Klasse BCryptPAswordEncoder.
     * Rückgabewert: kryptische Zeichenkette
     **/
    public static String hash(String password) {
        return encoder.encode(password);
    }


    /**
     * Methode hash(): übernimmt als PARAM zwei Strings und entkrypted den String mit der Klasse BCryptPAswordEncoder.
     * Rückgabewert: boolscher Wert
     **/
    public static boolean verify(String fromDB, String password) {
        return encoder.matches(password, fromDB);

    }
}
