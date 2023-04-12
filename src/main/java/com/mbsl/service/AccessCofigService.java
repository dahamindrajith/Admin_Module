/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.service;

import com.mbsl.model.Access;
import java.util.List;

/**
 *
 * @author MBSL2523
 */
public interface AccessCofigService {

    String accessConfig(Access userAccess);

    Access getAccessListByID(Access userAccess);

    List<Access> getAccessLiset(Access userAccess);

    String updateaccessConfig(Access userAccess);

    List<Access> getAllDepartment();

    List<Access> getAllRegion();
}
