/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.dao;

import com.google.gson.JsonArray;
import java.sql.SQLException;

/**
 *
 * @author MBSL2395
 */
public interface CommonDao {

    public JsonArray getMyBranch(String userName, String bnk) throws SQLException;
}
