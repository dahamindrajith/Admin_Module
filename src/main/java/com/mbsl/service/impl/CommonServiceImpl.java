/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.google.gson.JsonArray;
import com.mbsl.dao.CommonDao;
import java.sql.SQLException;
import org.springframework.stereotype.Service;
import com.mbsl.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author MBSL2395
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonDao commonDao;

    @Override
    public JsonArray getMyBranch(String userName, String bnk) throws SQLException {
        return commonDao.getMyBranch(userName, bnk);
    }
}
