/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.mbsl.Classes.requestGenerator;
import com.mbsl.DbConn;
import com.mbsl.dao.RequestIdGeneratorDao;
import com.mbsl.model.User;
import java.sql.Connection;
import java.sql.ResultSet;
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
public class RequestIdGeneratorDaoImpl implements RequestIdGeneratorDao {

    private static final Logger LOGGER = LogManager.getLogger(RequestIdGeneratorDaoImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Override
    public String generateRequestId(User user) {
        System.out.println("---------------------------");
        DbConn conn = new DbConn();

        if (user.getRqstType().equals("1")) {
            try {

                Connection connection = conn.getConn();
                ResultSet rs = connection.createStatement().executeQuery("SELECT rqst_id FROM admin_main_table where rqst_id!='' and progres_stus_code!='25' ORDER BY id DESC LIMIT 1");
                if (rs.next()) {

                    String value = rs.getString(1);
                    System.out.println("45" + value + "454545454");

                    if (!value.equals("")) {
                        int lenght = value.length();
                        String numericVal = value.substring(5, lenght);
                        System.out.println("--------------" + numericVal);
                        requestGenerator obj = new requestGenerator();
                        System.out.println("Done" + value + "---" + user.getRqstType());
                        return obj.requestGeneratorr(numericVal, user.getRqstType());

                    }
                } else {

                    System.out.println(" else ----");
                    String numericVal = "0";
                    requestGenerator obj = new requestGenerator();
                    System.out.println("Done" + numericVal + "---" + user.getRqstType());
                    return obj.requestGeneratorr(numericVal, user.getRqstType());

                }
                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (user.getRqstType().equals("2")) {

            try {

                Connection connection = conn.getConn();
                ResultSet rs = connection.createStatement().executeQuery("SELECT req_id FROM admin_user_transfers where req_id!='' and progress_code!='25' ORDER BY id DESC LIMIT 1");
                if (rs.next()) {

                    String value = rs.getString(1);
                    System.out.println("45" + value + "454545454");

                    if (!value.equals("")) {
                        int lenght = value.length();
                        String numericVal = value.substring(5, lenght);
                        System.out.println("--------------" + numericVal);
                        requestGenerator obj = new requestGenerator();
                        System.out.println("Done" + value + "---" + user.getRqstType());
                        return obj.requestGeneratorr(numericVal, user.getRqstType());

                    }
                } else {

                    System.out.println(" else ----");
                    String numericVal = "0";
                    requestGenerator obj = new requestGenerator();
                    System.out.println("Done" + numericVal + "---" + user.getRqstType());
                    return obj.requestGeneratorr(numericVal, user.getRqstType());

                }
                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        } else if (user.getRqstType().equals("3")) {

            try {

                Connection connection = conn.getConn();
                ResultSet rs = connection.createStatement().executeQuery("SELECT req_id FROM admin_user_inactivate where req_id!='' and progress_code!='25' ORDER BY id DESC LIMIT 1");
                if (rs.next()) {

                    String value = rs.getString(1);
                    System.out.println("45" + value + "454545454");

                    if (!value.equals("")) {
                        int lenght = value.length();
                        String numericVal = value.substring(5, lenght);
                        System.out.println("--------------" + numericVal);
                        requestGenerator obj = new requestGenerator();
                        System.out.println("Done" + value + "---" + user.getRqstType());
                        return obj.requestGeneratorr(numericVal, user.getRqstType());

                    }
                } else {

                    System.out.println(" else ----");
                    String numericVal = "0";
                    requestGenerator obj = new requestGenerator();
                    System.out.println("Done" + numericVal + "---" + user.getRqstType());
                    return obj.requestGeneratorr(numericVal, user.getRqstType());

                }
                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        } else if (user.getRqstType().equals("4")) {

            try {

                Connection connection = conn.getConn();
                ResultSet rs = connection.createStatement().executeQuery("SELECT rqst_id FROM admin_temp_access where rqst_id!='' and progress_code!='25' ORDER BY id DESC LIMIT 1");
                if (rs.next()) {

                    String value = rs.getString(1);
                    System.out.println("45" + value + "454545454");

                    if (!value.equals("")) {
                        int lenght = value.length();
                        String numericVal = value.substring(1, lenght);
                        System.out.println("--------------" + numericVal);
                        requestGenerator obj = new requestGenerator();
                        System.out.println("Done" + value + "---" + user.getRqstType());
                        return obj.requestGeneratorr(numericVal, user.getRqstType());

                    }
                } else {

                    System.out.println(" else ----");
                    String numericVal = "0";
                    requestGenerator obj = new requestGenerator();
                    System.out.println("Done" + numericVal + "---" + user.getRqstType());
                    return obj.requestGeneratorr(numericVal, user.getRqstType());

                }
                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        }

        //outPut.add(ja);
        return null;

    }

}
