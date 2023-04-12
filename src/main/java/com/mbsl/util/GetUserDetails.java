/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.util;

import com.google.gson.JsonArray;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MBSL2523
 */
public class GetUserDetails {

    public static JsonArray getUserNameAndBranch(HttpSession session) {
        JsonArray array = new JsonArray();
        array.add(session.getAttribute("user").toString());
        array.add(session.getAttribute("branch").toString());
        array.add(session.getAttribute("bank").toString());
        return array;
    }
    
}
