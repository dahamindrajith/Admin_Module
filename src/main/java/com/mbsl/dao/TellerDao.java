/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.dao;

import com.mbsl.model.Teller;
import java.util.List;

/**
 *
 * @author MBSL2491
 */
public interface TellerDao {

    String createTeller(Teller tellerCreate);

    public List<Teller> getAllTellers();

    String updateTellers(Teller tellerCreate);

    String deleteTellers(Teller tellerCreate);

    public List<Teller> getAllBranchOrDept();

    public List<Teller> getTill(Teller tellerCreate);

    public Teller getTellerByBranch(Teller tellerCreate);

    public List<Teller> getTellerByUBranch(Teller tellerCreate);

}
