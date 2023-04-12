/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.util;

/**
 *
 * @author MBSL2523
 */
public class SpecialCharactorManager {

public static String manageSingleQuote(String text) {
        if (null != text) {
            text = text.replace("'", "\\'");
        }
        return text;
    }

    public static String removeSingleQuote(String text) {
        if (null != text) {
            text = text.replace("'", " ");
        }
        return text;
    }
    /* remove all special charachters */
    public static String removeAllSpeciallChr(String text) {
        if (null != text) {
            text = text.replaceAll("[^a-zA-Z0-9]", " ");
        }
        return text;
    } 
    
}
