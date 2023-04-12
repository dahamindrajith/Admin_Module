/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.dao;

import com.google.gson.JsonArray;
import com.mbsl.model.Designation;
import com.mbsl.model.User;
import java.util.List;

/**
 *
 * @author MBSL2523
 */
public interface DesignationDao {

    String createDesig(Designation createDesignation);

    public List<Designation> getAllDesignations();

    public JsonArray getAllDesignationsTest();

    String deleteDesignation(Designation createDesignation);

}
