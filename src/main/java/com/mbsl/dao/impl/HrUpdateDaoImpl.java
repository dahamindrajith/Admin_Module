/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.DbConn;
import com.mbsl.dao.HrUpdateDao;
import com.mbsl.model.Profile;
import com.mbsl.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class HrUpdateDaoImpl implements HrUpdateDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Override
    public String createHrList(User approveList) {
        System.out.println(approveList.getEpfNo() + "Done");

        String availability = checkAvailability(approveList.getEpfNo());

        try {
            if (availability.equals("1")) {
                String sql = "INSERT INTO admin_hr_approved_table(user_id, new_user_name, profile, approved_profile, status, branch_or_dept) VALUES (?, ?, ?, ?, ?, ?)";

                int result = jdbcTemplate.update(sql, approveList.getEpfNo(), approveList.getFullName(), approveList.getProfile(), approveList.getNewProfile().trim(), "A", approveList.getBranchorDept());

                if (result > 0) {
                    System.out.println("A sql row has been inserted.");
                }

                System.out.println(result);
                LOGGER.info("sucess");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        if (availability.equals("1")) {
            return messageSource.getMessage("user.hrlist.success", null, Locale.US);
        } else if (availability.equals("2")) {
            return messageSource.getMessage("user.hrlist.already", null, Locale.US);
        } else {
            return messageSource.getMessage("user.hrlist.notavailable", null, Locale.US);
        }
    }

    private String checkAvailability(String epf) {
        DbConn conn = new DbConn();
        String setType = "'" + epf + "'";
        String value = "0";

        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT user_id, prev_br_dep, branch_or_dept FROM admin_main_table  where progres_stus_code!=25 and user_id=" + setType);
            System.out.println("Qrydone ");
            if (rs.next()) {

                value = "1";

                ResultSet rs1 = connection.createStatement().executeQuery("SELECT user_id FROM admin_hr_approved_table  where user_id=" + setType + " AND status='A'");
                if (rs1.next()) {

                    value = "2";

                }
            }
            LOGGER.info("sucess");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        System.out.println("return value--" + value);
        return value;

    }

    @Override
    public List<User> getList() {

        DbConn conn = new DbConn();
        List<User> userList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT user_id, new_user_name, profile, approved_profile, branch_or_dept from admin_hr_approved_table WHERE status='A'");
            while (rs.next()) {

                User user = new User();
                user.setEpfNo(rs.getString(1));
                user.setFullName(rs.getString(2));
                user.setProfile(rs.getString(3));
                user.setNewProfile(rs.getString(4));
                user.setBranchorDept(rs.getString(5));

                userList.add(user);

                System.out.println("bbbbbbbbbbbbbbbbbbbbbbb" + userList.get(0).getEpfNo());

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
    public String deleteList(User approveList) {
        System.out.println("334----" + approveList.getEpfNo());

        try {
            String sql2 = "update admin_hr_approved_table set status=? where user_id=? AND status=?";

            int result2 = jdbcTemplate.update(sql2, "I", approveList.getEpfNo(), "A");

            if (result2 > 0) {
                System.out.println("A new row has been inserted.");
            }

            LOGGER.info("sucess");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        return messageSource.getMessage("user.hrlistdlt.success", null, Locale.US);
    }

    @Override
    public User getListHrById(User approveList) {
        System.out.println("getListHrById Done" + approveList.getEpfNo());
        List<User> userList = new ArrayList();
        User hruser = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();

            String sql = "SELECT admin_user_creation_det.new_user_name, admin_main_table.user_id, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till, admin_main_table.prev_br_dep FROM admin_main_table INNER JOIN admin_user_creation_det ON admin_main_table.user_id=admin_user_creation_det.user_id where admin_main_table.user_id=? AND admin_main_table.progres_stus=? AND admin_user_creation_det.status=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, approveList.getEpfNo());
            ps.setString(2, "Approved");
            ps.setString(3, "A");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                hruser.setFullName(rs.getString(1));
                hruser.setEpfNo(approveList.getEpfNo());
                hruser.setBranchorDept(rs.getString(3));
                hruser.setProfile(rs.getString(4));
                hruser.setDesignation(rs.getString(5));
                hruser.setCurrentTill(rs.getString(6));
                hruser.setBranchorDept(rs.getString(7));

                userList.add(hruser);

                System.out.println("bbbbbbbbbbb" + userList.get(0).getEpfNo());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return hruser;
    }

    @Override
    public List<Profile> getBProfile() {

        DbConn conn = new DbConn();
        List<Profile> profList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT profile FROM admin_config_profile WHERE status='A' AND branch='2'");
            while (rs.next()) {

                Profile pfoList = new Profile();
                pfoList.setProfileName(rs.getString(1));

                profList.add(pfoList);

                System.out.println("bbbbbbbbbbbbbbbbbbbbbbb");

            }
            LOGGER.info("sucess");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        //outPut.add(ja);
        return profList;
    }

}
