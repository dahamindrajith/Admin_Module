/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.service;

import com.mbsl.model.User;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MBSL2523
 */
public interface ApproveReqstService {

    List<User> getApproveRqsts(HttpSession session);

    User getListByRqstId(User approveRqst);

// User test(String RqstID, String RqstType);
    String approvedMessage(User approveRqst, HttpSession session);

    String rejecctMessage(User approveRqst, HttpSession session);

}
