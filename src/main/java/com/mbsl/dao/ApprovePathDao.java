/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.dao;

import com.mbsl.model.ApprovalPath;
import java.util.List;

/**
 *
 * @author MBSL2523
 */
public interface ApprovePathDao {

    String createApprovel(ApprovalPath approvePath);

    public List<ApprovalPath> getAllApprovelPaths(String value);

    String deleteApprovelPaths(ApprovalPath approvePath, String pathNo, String approvedLvl, String rqstType);

    public List<ApprovalPath> getCCList();

}
