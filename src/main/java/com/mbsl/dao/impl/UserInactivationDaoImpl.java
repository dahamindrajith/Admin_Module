/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.Classes.RequestIdGenerator;
import com.mbsl.DbConn;
import com.mbsl.dao.UserInactivationDao;
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
public class UserInactivationDaoImpl implements UserInactivationDao {

    private static final Logger LOGGER = LogManager.getLogger(UserInactivationDaoImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Override
    public String userInact(User userInactivation) {

        String bod = "";

        if (userInactivation.getClick().trim().equals("Department")) {
            bod = "3050";
        } else if (userInactivation.getClick().trim().equals("Branch")) {
            bod = "3350";
        } else if (userInactivation.getClick().trim().equals("Region")) {
            bod = "3750";
        }

        String availability = checkAvailability(userInactivation.getEpfNo());
        System.out.println("Availability----" + availability);

        try {
            if (availability.equals("2")) {

                RequestIdGenerator objRqst = new RequestIdGenerator();
                String RqstID = objRqst.generateRequestId("3");

                String sql2 = "INSERT INTO admin_user_inactivate(req_id, user_id, reason, req_type, progress_stat, progress_code) VALUES (?, ?, ?, ?, ?, ?)";

                int result2 = jdbcTemplate.update(sql2, RqstID, userInactivation.getEpfNo(), userInactivation.getReason().trim(), "User Inactivate", "Pending", bod);

                if (result2 > 0) {
                    System.out.println("A new row has been inserted.");
                }

                LOGGER.info("sucess");
            }
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }
        if (availability.equals("1")) {
            return messageSource.getMessage("user.inactivation.alreadyinactivated", null, Locale.US);
        } else if (availability.equals("2")) {
            return messageSource.getMessage("user.inactivation.success", null, Locale.US);
        } else {
            return messageSource.getMessage("user.inactivation.notavailable", null, Locale.US);
        }

    }

    private String checkAvailability(String epf) {
        DbConn conn = new DbConn();
        String setType = "'" + epf + "'";
        String value = "0";
        int idCount = 0;

        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT user_id FROM admin_main_table  where progres_stus_code IN ('401','901','1301') AND user_id=" + setType);
            System.out.println("Qrydone ");
            if (rs.next()) {

                ResultSet rs2 = connection.createStatement().executeQuery("SELECT user_id FROM admin_user_inactivate  where user_id=" + setType);
                ResultSet rs1 = connection.createStatement().executeQuery("SELECT user_id FROM admin_user_inactivate  where progress_code IN ('25','3300','3700','4000') AND user_id=" + setType);
                ResultSet rs3 = connection.createStatement().executeQuery("SELECT COUNT(user_id) AS c FROM admin_user_inactivate  WHERE progress_code IN ('3050','3350','3750') AND user_id=" + setType);
                if (rs3.next()) {
                    idCount = rs3.getInt(1);
                }

                if (rs1.next()) {

                    if (idCount > 0) {
                        value = "1";
                    } else {
                        value = "2";
                    }

                } else if (!rs2.next()) {
                    value = "2";
                } else {
                    value = "1";
                }

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        return value;

    }

    @Override
    public List<User> getInactUsers() {
        System.out.println("Done");
        DbConn conn = new DbConn();
        List<User> userList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT admin_user_creation_det.new_user_name, admin_user_inactivate.req_id, admin_user_inactivate.user_id, admin_user_inactivate.progress_stat, admin_user_inactivate.reason, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till, admin_main_table.branch_or_dept FROM admin_user_inactivate INNER JOIN admin_main_table ON admin_user_inactivate .user_id=admin_main_table.user_id INNER JOIN admin_user_creation_det ON admin_user_inactivate .user_id=admin_user_creation_det.user_id where admin_user_inactivate.progress_code!=25 and admin_user_inactivate.progress_code!=3300 and admin_user_inactivate.progress_code!=3700 and admin_user_inactivate.progress_code!=4000 and admin_user_creation_det.status='A'");
            while (rs.next()) {

                User user = new User();
                user.setFullName(rs.getString(1));
                user.setRqstId(rs.getString(2));
                user.setEpfNo(rs.getString(3));
                user.setStatus(rs.getString(4));
                user.setReason(rs.getString(5));
                user.setBranchorDept(rs.getString(6));
                user.setProfile(rs.getString(7));
                user.setDesignation(rs.getString(8));
                user.setCurrentTill(rs.getString(9));
                user.setClick(rs.getString(10));

                userList.add(user);

                System.out.println("oooooooooooo" + userList.get(0).getEpfNo());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return userList;
    }

    @Override
    public User getListUserIactById(User userInactivation) {
        System.out.println("getListTransferById Done");
        List<User> userList = new ArrayList();
        User user = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT admin_user_inactivate.req_id, admin_user_inactivate.reason, admin_user_creation_det.new_user_name, admin_main_table.user_id, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till, admin_main_table.branch_or_dept FROM admin_main_table INNER JOIN admin_user_creation_det ON admin_main_table .user_id=admin_user_creation_det.user_id INNER JOIN admin_user_inactivate ON admin_user_creation_det.user_id=admin_user_inactivate.user_id where admin_main_table.user_id=? AND admin_user_inactivate.progress_code!=25";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userInactivation.getEpfNo());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user.setRqstId(rs.getString(1));
                user.setReason(rs.getString(2));
                user.setFullName(rs.getString(3));
                user.setEpfNo(userInactivation.getEpfNo());
                user.setBranchorDept(rs.getString(5));
                user.setProfile(rs.getString(6));
                user.setDesignation(rs.getString(7));
                user.setCurrentTill(rs.getString(8));
                user.setClick(rs.getString(9));

                userList.add(user);

                System.out.println("getListUserIactById" + userList.get(0).getEpfNo());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        //outPut.add(ja);
        return user;
    }

    @Override
    public String updateUserIact(User userInactivation) {
        System.out.println(userInactivation.getEpfNo() + "Done");
        System.out.println(userInactivation.getEpfNo() + "Done");

        String bod = "";

        if (userInactivation.getClick().trim().equals("Department")) {
            bod = "3050";
        } else if (userInactivation.getClick().trim().equals("Branch")) {
            bod = "3350";
        } else if (userInactivation.getClick().trim().equals("Region Branch")) {
            bod = "3750";
        }

        try {
            String sql = "update admin_user_inactivate set reason=?, req_type=?, progress_stat=?, progress_code=? where progress_code!=? and user_id=?";

            int result = jdbcTemplate.update(sql, userInactivation.getReason().trim(), "User Inactivate", "Pending", bod, "25", userInactivation.getEpfNo());

            if (result > 0) {
                System.out.println("A sql row has been inserted.");
            }

            System.out.println(result);
            LOGGER.info("sucess");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return messageSource.getMessage("user.inactivationupdate.success", null, Locale.US);
    }

    @Override
    public String deleteInactUser(User userInactivation) {
        System.out.println("33" + userInactivation.getEpfNo());

        try {
            String sql2 = "update admin_user_inactivate set progress_code=? where user_id=?";

            int result2 = jdbcTemplate.update(sql2, "25", userInactivation.getEpfNo());

            if (result2 > 0) {
                System.out.println("A new row has been inserted.");
            }

            LOGGER.info("sucess");
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }

        return messageSource.getMessage("user.inactivationdelete.success", null, Locale.US);
    }

    @Override
    public User getToSelectButtonById(User userInactivation) {
        System.out.println("getToSelectButtonById Done");
        List<User> userList = new ArrayList();
        User user = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();

            String sql = "SELECT admin_user_creation_det.new_user_name, admin_main_table.user_id, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till, admin_main_table.branch_or_dept FROM admin_main_table INNER JOIN admin_user_creation_det ON admin_main_table.user_id=admin_user_creation_det.user_id where admin_main_table.user_id=? AND admin_main_table.progres_stus_code!=25 AND admin_main_table.status='A' AND admin_user_creation_det.status='A'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userInactivation.getEpfNo());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user.setFullName(rs.getString(1));
                user.setEpfNo(userInactivation.getEpfNo());
                user.setBranchorDept(rs.getString(3));
                user.setProfile(rs.getString(4));
                user.setDesignation(rs.getString(5));
                user.setCurrentTill(rs.getString(6));
                user.setClick(rs.getString(7));

                userList.add(user);

                System.out.println("bbbbbbbbbbb" + userList.get(0).getEpfNo());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return user;
    }

    @Override
    public User getAppInfoI(User userInactivation) {

        System.out.println("-------------" + userInactivation.getEpfNo() + "   " + userInactivation.getRqstType() + "   " + userInactivation.getStatus() + "    " + userInactivation.getNewBranchOrDept());

        User inactivate = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();
//            String sql = "SELECT branch_or_dept FROM admin_main_table WHERE user_id=? AND progres_stus_code!=25";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1, userInactivation.getEpfNo());
//
//            ResultSet rs = ps.executeQuery();

//            if (rs.next()) {
//                String bod = rs.getString(1);
            String bod = "";
            if (userInactivation.getNewBranchOrDept().contains("BRANCH")) {
                bod = "Branch";
            } else if (userInactivation.getNewBranchOrDept().contains("REGION")) {
                bod = "Region";
            } else {
                bod = "Department";
            }

            System.out.println("--------" + bod);
            String sql2 = "SELECT profile_or_design, bod_name FROM admin_approal_users WHERE branch_or_dept=? AND rqst_type=? AND show_info_stts=?";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setString(1, bod.trim());
            ps2.setString(2, userInactivation.getRqstType().trim());
            ps2.setString(3, userInactivation.getStatus().trim());
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {

                String profOrDesig = rs2.getString(1);
                String branchOrDept = rs2.getString(2);

                if (branchOrDept.equals("AB") && profOrDesig.equals("Region Manager")) {
                    String sql4 = "SELECT admin_region_office.region_office_name FROM admin_region_office INNER JOIN admin_branch ON admin_branch.region_unit=admin_region_office.region_office WHERE admin_branch.br_name=?";
                    PreparedStatement ps4 = connection.prepareStatement(sql4);
                    ps4.setString(1, userInactivation.getNewBranchOrDept());
                    ResultSet rs4 = ps4.executeQuery();

                    if (rs4.next()) {
                        branchOrDept = rs4.getString(1);
                    }

                } else if (branchOrDept.equals("AB")) {
                    branchOrDept = userInactivation.getNewBranchOrDept();
                }

                System.out.println("-----------" + profOrDesig + "         " + branchOrDept);
                String sql3 = "SELECT admin_user_creation_det.new_user_name FROM admin_user_creation_det INNER JOIN admin_main_table ON admin_user_creation_det.user_id=admin_main_table.user_id WHERE admin_main_table.new_br=? AND admin_main_table.new_prof=? AND admin_main_table.progres_stus_code!=25";
                PreparedStatement ps3 = connection.prepareStatement(sql3);
                ps3.setString(1, branchOrDept.trim());
                ps3.setString(2, profOrDesig.trim());

                ResultSet rs3 = ps3.executeQuery();

                if (rs3.next()) {

                    inactivate.setFullName(rs3.getString(1));
                    System.out.println("-------------");

                }
            }
//            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return inactivate;
    }

}
