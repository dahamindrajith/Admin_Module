/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.mbsl.dao.ApprovelProcessDao;
import com.mbsl.model.ApprovelProcess;
import java.util.Locale;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MBSL2523
 */
@Repository
public class ApprovelProcessDaoImpl implements ApprovelProcessDao{

private static final Logger LOGGER = LogManager.getLogger(ApprovelProcessDaoImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;


@Override
    public String approveConfirm(ApprovelProcess approvelProcess) {


    System.out.println("approveConfirm"+approvelProcess);

//        System.out.println("userrrrrrrrrrrrrrrrrrrrr" + user.getBranchorDept());
//        System.out.println("Click" + user.getClick());
//        String bod = "";
//
//        if (user.getClick().trim().equals("Department")) {
//            bod = "50";
//        } else if (user.getClick().trim().equals("Branch")) {
//            bod = "450";
//        } else if (user.getClick().trim().equals("Region Branch")) {
//            bod = "950";
//        }
//
//        try {
//            String sql = "INSERT INTO admin_main_table(rqst_id, user_id, prev_br_dep, prev_prof, prev_desig, prev_till, branch_or_dept, req_type, status, progres_stus, progres_stus_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            int result = jdbcTemplate.update(sql, user.getRqstId(), "MBSL" + user.getEpfNo(), user.getBranchorDept().trim(), user.getProfile().trim(), user.getDesignation().trim(), user.getCurrentTill(), user.getClick().trim(), "User Grant", "A", "Pending", bod);
//            System.out.println(user.getBranchorDept());
//            if (result > 0) {
//                System.out.println("A sql row has been inserted.");
//            }
//
//            try {
//                String sql2 = "INSERT INTO admin_user_creation_det(user_id, bank_code, new_user_name, email_add, mobile_no) VALUES (?, ?, ?, ?, ?)";
//
//                int result2 = jdbcTemplate.update(sql2, "MBSL" + user.getEpfNo(), "MBSL", user.getFullName(), user.getEmailAddress(), user.getMobileNo());
//
//                if (result2 > 0) {
//                    System.out.println("A new row has been inserted.");
//                }
//
//                LOGGER.info("sucess");
//            } catch (Exception e) {
//                LOGGER.error("error : ", e);
//            }
//
//            LOGGER.info("sucess");
//        } catch (Exception e) {
//            LOGGER.error("error : ", e);
//        }
        return messageSource.getMessage("user.create.success", null, Locale.US);
    }
    
}
