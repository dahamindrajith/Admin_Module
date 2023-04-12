/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.dao;

import com.mbsl.model.Access;
import java.util.List;

/**
 *
 * @author MBSL2523
 */
public interface AccessCofigDao {

    String accessConfig(Access userAccess);

    public Access getAccessListByID(Access userAccess);

    public List<Access> getAccessLiset(Access userAccess);

    String updateaccessConfig(Access userAccess);

    public List<Access> getAllDepartment();

    public List<Access> getAllRegion();

}
