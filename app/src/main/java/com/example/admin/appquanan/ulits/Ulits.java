package com.example.admin.appquanan.ulits;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 10/19/2017.
 */

public class Ulits {
    public static boolean checkUsername(String username){
        if(username == null) return false;
        String regex = "^[a-zA-Z0-9]{5,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        if(!matcher.matches()){
            return false;
        } else
            return true;
    }

    public static boolean checkPassword(String password){
        if(password == null) return false;
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()){
            return false;
        } else
            return true;
    }
}
