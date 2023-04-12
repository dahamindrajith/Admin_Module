/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.service;

import com.google.gson.JsonArray;
import com.mbsl.model.Designation;
import com.mbsl.model.User;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public interface DesignationService {

    String createDesig(Designation createDesignation);

    List<Designation> getAllDesignations();

    public JsonArray getAllDesignationsTest();

    String deleteDesignation(Designation createDesignation);
}
