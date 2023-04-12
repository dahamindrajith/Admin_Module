/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.dao;

import com.mbsl.model.User;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MBSL2523
 */
public interface ApproveReqstDao {

    public List<User> getApproveRqsts(HttpSession session);

    public User getListByRqstId(User approveRqst);

//public User test(String RqstID, String RqstType);
    String approvedMessage(User approveRqst, HttpSession session);

    String rejecctMessage(User approveRqst, HttpSession session);

}
