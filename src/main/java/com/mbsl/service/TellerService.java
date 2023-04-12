/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.service;

import com.mbsl.model.Teller;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2491
 */
@Service
public interface TellerService {

    String createTeller(Teller tellerCreate);

    List<Teller> getAllTellers();

    String updateTellers(Teller tellerCreate);

    String deleteTellers(Teller tellerCreate);

    List<Teller> getAllBranchOrDept();

    List<Teller> getTill(Teller tellerCreate);

    Teller getTellerByBranch(Teller tellerCreate);

    List<Teller> getTellerByUBranch(Teller tellerCreate);

}
