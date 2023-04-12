/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.DbConn;
import com.mbsl.dao.AccessCofigDao;
import com.mbsl.model.Access;
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
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MBSL2523
 */
@Repository
public class AccessCofigDaoImpl implements AccessCofigDao {

    private static final Logger LOGGER = LogManager.getLogger(AccessCofigDaoImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Override
    public String accessConfig(Access userAccess) {

        System.out.println("radio----" + userAccess.getRadio());
        String availability = checkAvailability(userAccess.getRadio(), userAccess.getProfile(), userAccess.getBranchOrDept());
//Branch
        if (availability.equals("0")) {
            System.out.println("Availability-----------" + availability);
            if (userAccess.getRadio().equals("branch")) {
                try {

                    String sql = "INSERT INTO admin_branch_ass_acc(profile, access_code) VALUES (?, ?)";
                    int result = jdbcTemplate.update(sql, userAccess.getProfile().trim(), userAccess.getAccessCode());

                    if (result > 0) {
                        System.out.println("A sql row has been inserted.");
                    }

                    LOGGER.info("sucess");
                } catch (Exception e) {
                    LOGGER.error("error : ", e);
                }
//Departments
            } else if (userAccess.getRadio().equals("department")) {
                try {

                    String sql = "INSERT INTO admin_department_ass_acc(cc, profile, access_code) VALUES (?, ?, ?)";
                    int result = jdbcTemplate.update(sql, userAccess.getBranchOrDept().trim(), userAccess.getProfile().trim(), userAccess.getAccessCode());

                    if (result > 0) {
                        System.out.println("A sql row has been inserted.");
                    }

                    LOGGER.info("sucess");
                } catch (Exception e) {
                    LOGGER.error("error : ", e);
                }
            } else if (userAccess.getRadio().equals("region")) {
                try {

                    String sql = "INSERT INTO admin_region_office_access( profile, access_code) VALUES ( ?, ?)";
                    int result = jdbcTemplate.update(sql, userAccess.getProfile().trim(), userAccess.getAccessCode());

                    if (result > 0) {
                        System.out.println("A sql row has been inserted.");
                    }

                    LOGGER.info("sucess");
                } catch (Exception e) {
                    LOGGER.error("error : ", e);
                }
            }
        }
        if (availability.equals("0")) {
            return messageSource.getMessage("access.create.success", null, Locale.US);
        } else {
            return messageSource.getMessage("access.create.error", null, Locale.US);
        }
    }

    private String checkAvailability(String bod, String profile, String cc) {
        DbConn conn = new DbConn();
        Connection connection = conn.getConn();

        String setType2 = "";
        String setType = "'" + bod + "'";
        String setType1 = "'" + profile.trim() + "'";
        setType2 = "'" + cc.trim() + "'";
        System.out.println("setType---" + setType);
        System.out.println("Profile---" + setType1);
        System.out.println("CC----" + cc);
        String value = "0";

        if (bod.equals("branch")) {

            try {

                ResultSet rs = connection.createStatement().executeQuery("SELECT profile FROM admin_branch_ass_acc  where profile=" + setType1);
                System.out.println("Qrydone " + rs);
                if (rs.next()) {

                    value = "1";

                }
                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (bod.equals("department")) {

            try {

                ResultSet rs = connection.createStatement().executeQuery("SELECT cc, profile FROM admin_department_ass_acc  where cc=" + setType2 + " and profile=" + setType1);
                System.out.println("Qrydone " + rs);
                if (rs.next()) {

                    value = "1";

                }
                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        }
        JdbcUtils.closeConnection(connection);
        return value;

    }

    @Override
    public Access getAccessListByID(Access userAccess) {
        List<Access> arrayList = new ArrayList();
        Access array = new Access();
        String accessModel;
        JsonArray ja = new JsonArray();
        String setType = "'" + userAccess.getProfile().trim() + "'";
        String setType2 = "'" + userAccess.getDesignation() + "'";

        System.out.println("Bod--------" + userAccess.getBranchOrDept());
        System.out.println("Selected one----------" + setType);
        System.out.println("CCode------" + setType2);
        if (userAccess.getBranchOrDept().equals("Branch")) {
            System.out.println("Done 1");
            try {
                Connection connection = dataSource.getConnection();
//                String sql = "SELECT profile, access_code from admin_pro_ass_acc where profile=?";
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.setString(1, setType);
//                ResultSet rs = ps.executeQuery();

                ResultSet rs = connection.createStatement().executeQuery("SELECT profile, access_code from admin_branch_ass_acc where profile=" + setType);

                if (rs.next()) {

                    array.setProfile(rs.getString(1));
                    array.setAccessCode(rs.getString(2));
                    array.setRadio("Branch");

                    arrayList.add(array);



                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (userAccess.getBranchOrDept().trim().equals("Department")) {
            System.out.println("Done 2");
            try {
                Connection connection = dataSource.getConnection();
//                String sql = "SELECT designation, access_code from admin_desig_ass_acc where designation=?";
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.setString(1, setType);
//                ResultSet rs = ps.executeQuery();

                ResultSet rs = connection.createStatement().executeQuery("SELECT cc, profile, access_code from admin_department_ass_acc where cc=" + setType2 + " and profile=" + setType);

                if (rs.next()) {

                    array.setBranchOrDept(rs.getString(1));
                    array.setProfile(rs.getString(2));
                    array.setAccessCode(rs.getString(3));
                    array.setRadio("Department");

                    arrayList.add(array);

            

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        } else {

            try {
                Connection connection = dataSource.getConnection();
//                String sql = "SELECT profile, access_code from admin_pro_ass_acc where profile=?";
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.setString(1, setType);
//                ResultSet rs = ps.executeQuery();

                ResultSet rs = connection.createStatement().executeQuery("SELECT profile, access_code from admin_region_office_access where profile=" + setType);

                if (rs.next()) {

                    array.setProfile(rs.getString(1));
                    array.setAccessCode(rs.getString(2));
                    array.setRadio("Region");

                    arrayList.add(array);

          

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        }
        return array;
    }

    @Override
    public List<Access> getAccessLiset(Access userAccess) {

        DbConn conn = new DbConn();
        Connection connection = conn.getConn();
        List<Access> acessList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        System.out.println("Value------" + userAccess.getRadio());

        if (userAccess.getRadio().equals("branch")) {

            try {

                ResultSet rs = connection.createStatement().executeQuery("SELECT profile, access_code from admin_branch_ass_acc");
                while (rs.next()) {

                    Access access = new Access();
                    access.setProfile(rs.getString(1));
                    access.setAccessCode(rs.getString(2));

                    acessList.add(access);

                 

                }
                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        } else if (userAccess.getRadio().equals("department")) {

            try {

                ResultSet rs = connection.createStatement().executeQuery("SELECT cc ,profile, access_code from admin_department_ass_acc");
                while (rs.next()) {

                    Access access = new Access();
                    access.setBranchOrDept(rs.getString(1));
                    access.setProfile(rs.getString(2));
                    access.setAccessCode(rs.getString(3));

                    acessList.add(access);

           

                }
                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (userAccess.getRadio().equals("region")) {

            try {

                ResultSet rs = connection.createStatement().executeQuery("SELECT profile FROM admin_region_office_access");
                while (rs.next()) {

                    Access access = new Access();

                    access.setProfile(rs.getString(1));

                    acessList.add(access);

                    

                }
                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        }
        JdbcUtils.closeConnection(connection);
        return acessList;
    }

    @Override
    public String updateaccessConfig(Access userAccess) {
        System.out.println(userAccess.getBranchOrDept() + "Done");
        System.out.println(userAccess.getProfile() + "Done");
        System.out.println(userAccess.getDesignation() + "Done");

        if (userAccess.getBranchOrDept().equals("Branch")) {

            try {
                String sql = "update admin_branch_ass_acc set access_code=? where profile=?";

                int result = jdbcTemplate.update(sql, userAccess.getAccessCode(), userAccess.getProfile());

                if (result > 0) {
                    System.out.println("A sql row has been inserted.");

                    String sql1 = "UPDATE admin_access_assign INNER JOIN admin_main_table ON admin_access_assign.user_id=admin_main_table.user_id SET admin_access_assign.access_code=? WHERE admin_main_table.new_prof=? AND admin_main_table.progres_stus_code!=?";

                    int result1 = jdbcTemplate.update(sql1, userAccess.getAccessCode(), userAccess.getProfile(), "25");
                }

                System.out.println(result);
                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        } else if (userAccess.getBranchOrDept().equals("Department")) {

            DbConn conn = new DbConn();
            Connection connection = conn.getConn();

            try {
                String sql = "update admin_department_ass_acc set access_code=? where cc=? and profile=?";

                int result = jdbcTemplate.update(sql, userAccess.getAccessCode(), userAccess.getDesignation(), userAccess.getProfile());

                if (result > 0) {
                    System.out.println("A sql row has been inserted."+userAccess.getDesignation());

                    ResultSet rs = connection.createStatement().executeQuery("SELECT dept_name FROM admin_dept WHERE dept_cc=" + userAccess.getDesignation());
                    if (rs.next()) {
                        System.out.println(rs);
                        String branchDept = rs.getString(1);

                        String sql2 = "UPDATE admin_access_assign INNER JOIN admin_main_table ON admin_access_assign.user_id=admin_main_table.user_id SET admin_access_assign.access_code=? WHERE admin_main_table.new_prof=? AND admin_main_table.new_br=? AND admin_main_table.progres_stus_code!=?";

                        int result2 = jdbcTemplate.update(sql2, userAccess.getAccessCode(), userAccess.getProfile(), branchDept, "25");
                    }
                }
                System.out.println(result);
                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        } else if (userAccess.getBranchOrDept().equals("Region")) {

            try {
                String sql = "update admin_region_office_access set access_code=? where profile=?";

                int result = jdbcTemplate.update(sql, userAccess.getAccessCode(), userAccess.getProfile());

                if (result > 0) {
                    System.out.println("A sql row has been inserted.");
                }

                System.out.println(result);
                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        }

        return messageSource.getMessage("access.update.success", null, Locale.US);
    }

    @Override
    public List<Access> getAllDepartment() {

        DbConn conn = new DbConn();
        List<Access> acessList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        System.out.println("Value------");

        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT DISTINCT admin_config_profile.cc FROM admin_config_profile INNER JOIN admin_dept ON admin_config_profile .cc=admin_dept.dept_cc where admin_config_profile.status='A' ORDER BY admin_config_profile.cc ASC");
            while (rs.next()) {

                Access user = new Access();
                user.setBranchOrDept(rs.getString(1));

                acessList.add(user);


            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return acessList;
    }

    @Override
    public List<Access> getAllRegion() {

        DbConn conn = new DbConn();
        List<Access> acessList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        System.out.println("Value------");

        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT DISTINCT admin_region_office.region_office_name FROM admin_region_office INNER JOIN admin_config_profile ON admin_config_profile .cc=admin_region_office.region_cc where admin_config_profile.status='A' ORDER BY admin_config_profile.cc ASC");
            while (rs.next()) {

                Access user = new Access();
                user.setBranchOrDept(rs.getString(1));

                acessList.add(user);

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return acessList;
    }

}
