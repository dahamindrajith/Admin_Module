/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.ApproveReqstDao;
import com.mbsl.model.User;
import com.mbsl.service.ApproveReqstService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class ApproveReqstServiceImpl implements ApproveReqstService {

    @Autowired
    private ApproveReqstDao approveReqstDao;

    @Override
    public List<User> getApproveRqsts(HttpSession session) {
        return approveReqstDao.getApproveRqsts(session);
    }

    @Override
    public User getListByRqstId(User approveRqst) {
        return approveReqstDao.getListByRqstId(approveRqst);
    }

//  @Override
//    public User test(String RqstID, String RqstType) {
//        return approveReqstDao.test(RqstID, RqstType);
//    }
    @Override
    public String approvedMessage(User approveRqst, HttpSession session) {
        return approveReqstDao.approvedMessage(approveRqst, session);
    }

    @Override
    public String rejecctMessage(User approveRqst, HttpSession session) {
        return approveReqstDao.rejecctMessage(approveRqst, session);
    }

}
