/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.Classes.EnvChange;
import com.mbsl.DbConn;
import com.mbsl.Properties;
import com.mbsl.dao.ApprovePathDao;
import com.mbsl.model.ApprovalPath;
import com.mbsl.velocity.as400.environment.AS400Libraries;
import com.mbsl.velocity.dal.as400.AS400DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
public class ApprovePathDaoImpl implements ApprovePathDao {

    private static final Logger LOGGER = LogManager.getLogger(ApprovePathDaoImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

  @Autowired
    Properties properties;

    EnvChange obj = new EnvChange();
    String envr = obj.envirenment();

    @Override
    public String createApprovel(ApprovalPath approvePath) {
        System.out.println("userrrrrrrrrrrrrrrrrrrrr" + approvePath.getPathno());

        try {
            String sql = "INSERT INTO admin_con_app_path(path_no, req_type, branch, spec_cc, approval_officer, approval_leval, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            int result = jdbcTemplate.update(sql, approvePath.getPathno(), approvePath.getRqstType().trim(), approvePath.getClick().trim(), approvePath.getCc(), approvePath.getApprvlOffcer(), approvePath.getApprvlLevel(), "A");
            System.out.println(approvePath.getPathno());
            if (result > 0) {
                System.out.println("A sql row has been inserted.");
            }

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
            LOGGER.info("sucess");
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }
        return messageSource.getMessage("user.create.success", null, Locale.US);
    }

    @Override
    public List<ApprovalPath> getAllApprovelPaths(String value) {

        System.out.println("Get all approvel path:" + value);
        DbConn conn = new DbConn();
        List<ApprovalPath> userList = new ArrayList();
        String reqstType = "";
        if (value.equals("1")) {
            reqstType = "User Creation";
        } else if (value.equals("2")) {
            reqstType = "User Transfer";
        } else if (value.equals("3")) {
            reqstType = "User Inactivation";
        } else if (value.equals("4")) {
            reqstType = "Temperory Access";

        }
        String setType = "'" + reqstType + "'";
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + setType);
        JsonArray ja = new JsonArray();
        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT path_no, req_type, branch, spec_cc, approval_officer, approval_leval FROM admin_con_app_path  where status='A' and req_type=" + setType);
            while (rs.next()) {

                ApprovalPath user = new ApprovalPath();
                user.setPathno(rs.getString(1));
                user.setRqstType(rs.getString(2));
                user.setClick(rs.getString(3));
                user.setCc(rs.getString(4));
                user.setApprvlOffcer(rs.getString(5));
                user.setApprvlLevel(rs.getString(6));

                userList.add(user);

                System.out.println("bbbbbbbbbbbbbbbbbbbbbbb" + userList.get(0).getPathno());

            }
            LOGGER.info("sucess");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        //outPut.add(ja);
        return userList;
    }

    @Override
    public String deleteApprovelPaths(ApprovalPath approvePath, String pathNo, String approvedLvl, String rqstType) {

        try {
            String sql2 = "update admin_con_app_path set status=? where path_no=? and approval_leval=? and req_type=?";

            int result2 = jdbcTemplate.update(sql2, "I", pathNo, approvedLvl, rqstType);

            if (result2 > 0) {
                System.out.println("A new row has been inserted.");
            }

            LOGGER.info("sucess");
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }
        return messageSource.getMessage("user.create.success", null, Locale.US);
    }

 @Override
    public List<ApprovalPath> getCCList() {

        List<ApprovalPath> ccList = new ArrayList();
        AS400Libraries libs = new AS400Libraries();
        AS400DBConnection.setLibraries(libs.getLibraries(envr));

        StringBuffer query = new StringBuffer().append("select CFCNTR from CFP420 where CFSTAT!='C'");

        try (ResultSet rs = AS400DBConnection.search(query.toString())) {

            while (rs.next()) {
                ApprovalPath list = new ApprovalPath();

                list.setCc(rs.getString(1).trim());
                ccList.add(list);

            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);

        }
        System.out.println("@return--" + ccList.get(0));
        return ccList;
    }

}
