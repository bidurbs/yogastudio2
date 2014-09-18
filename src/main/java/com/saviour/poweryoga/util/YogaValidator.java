/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.util;

import java.util.regex.Pattern;

/**
 *
 * @author Md Mojahidul Islam
 * @version 0.0.1
 */
public class YogaValidator {

    /**
     * This method will validate email
     *
     * @param email
     * @return true/false based on the given email is valid or not
     */
    public static Boolean emailValidator(String email) {
        try {
            String pattern = "\\A^(?=.{0,64}$)([a-z0-9_\\.-]+)@([\\da-z\\.-]+)([\\da-z]+)\\.([a-z]+)\\z";
            return Pattern.compile(pattern).matcher(email).matches();
        } catch (Exception e) {
            return false;
        }
    }
}
