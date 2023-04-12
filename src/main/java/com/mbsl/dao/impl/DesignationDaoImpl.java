/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.DbConn;
import com.mbsl.dao.DesignationDao;
import com.mbsl.model.Designation;
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
public class DesignationDaoImpl implements DesignationDao {

    private static final Logger LOGGER = LogManager.getLogger(DesignationDaoImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Override
    public String createDesig(Designation createDesignation) {
        System.out.println("Test Done" + createDesignation.getDesigType());

        String availability = checkAvailability(createDesignation.getDesigType());

        try {
            if (availability.equals("0")) {
                String sql = "INSERT INTO admin_config_desig(designation, Status) VALUES (?,?)";
                int result = jdbcTemplate.update(sql, createDesignation.getDesigType(), "A");

                if (result > 0) {
                    System.out.println("A sql row has been inserted.");
                }

                LOGGER.info("sucess");
            }
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }
        if (availability.equals("0")) {
            return messageSource.getMessage("designation.create.success", null, Locale.US);
        } else {
            return messageSource.getMessage("designation.create.error", null, Locale.US);
        }
    }

    private String checkAvailability(String designation) {
        DbConn conn = new DbConn();
        String setType = "'" + designation + "'";
        System.out.println("setType---" + setType);
        String value = "0";

        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT designation FROM admin_config_desig  where status='A' and designation=" + setType);
            System.out.println("Qrydone ");
            if (rs.next()) {

                value = "1";

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
    public List<Designation> getAllDesignations() {

        DbConn conn = new DbConn();
        List<Designation> userList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("select designation FROM admin_config_desig where Status='A' ORDER BY designation");
            while (rs.next()) {

                Designation designation = new Designation();
                designation.setDesigType(rs.getString(1));

                userList.add(designation);

                System.out.println("bbbbbbbbbbbbbbbbbbbbbbb" + userList.get(0).getDesigType());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        //outPut.add(ja);
        return userList;
    }

    @Override
    public JsonArray getAllDesignationsTest() {

        DbConn conn = new DbConn();
        List<Designation> userList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        JsonArray outPut = new JsonArray();
        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("select designation FROM admin_config_desig where Status='A'");
            while (rs.next()) {

                Designation designation = new Designation();
                ja.add(rs.getString(1));

//                userList.add(designation);
                System.out.println("bbbbbbbbbbbbbbbbbbbbbbb" + ja.toString());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
//        outPut.add(ja);
//       System.out.println("Out "+outPut);
        return ja;
    }

    @Override
    public String deleteDesignation(Designation createDesignation) {
        System.out.println("22222222222222222222222222222222" + createDesignation.getDesigType());

        try {
            String sql2 = "update admin_config_desig set Status=? where designation=?";

            int result2 = jdbcTemplate.update(sql2, "I", createDesignation.getDesigType());

            if (result2 > 0) {
                System.out.println("A new row has been inserted.");
            }

            LOGGER.info("sucess");
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }

        return messageSource.getMessage("designation.create.delete", null, Locale.US);
    }

}
