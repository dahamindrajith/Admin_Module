/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import com.mbsl.DbConn;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author MBSL2523
 */
public class RequestIdGenerator {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

  
    public String generateRequestId(String rqstType) {
        System.out.println("---------------------------");
        DbConn conn = new DbConn();

        if (rqstType.equals("1")) {
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
                        System.out.println("Done" + value + "---" + rqstType);
                        return obj.requestGeneratorr(numericVal, rqstType);

                    }
                } else {

                    System.out.println(" else ----");
                    String numericVal = "0";
                    requestGenerator obj = new requestGenerator();
                    System.out.println("Done" + numericVal + "---" + rqstType);
                    return obj.requestGeneratorr(numericVal, rqstType);

                }
               
            } catch (Exception e) {
                e.printStackTrace();
             
            }
        } else if (rqstType.equals("2")) {

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
                        System.out.println("Done" + value + "---" + rqstType);
                        return obj.requestGeneratorr(numericVal, rqstType);

                    }
                } else {

                    System.out.println(" else ----");
                    String numericVal = "0";
                    requestGenerator obj = new requestGenerator();
                    System.out.println("Done" + numericVal + "---" + rqstType);
                    return obj.requestGeneratorr(numericVal, rqstType);

                }
               
            } catch (Exception e) {
                e.printStackTrace();
   
            }

        } else if (rqstType.equals("3")) {

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
                        System.out.println("Done" + value + "---" + rqstType);
                        return obj.requestGeneratorr(numericVal, rqstType);

                    }
                } else {

                    System.out.println(" else ----");
                    String numericVal = "0";
                    requestGenerator obj = new requestGenerator();
                    System.out.println("Done" + numericVal + "---" + rqstType);
                    return obj.requestGeneratorr(numericVal, rqstType);

                }
               
            } catch (Exception e) {
                e.printStackTrace();
          
            }

        } else if (rqstType.equals("4")) {

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
                        System.out.println("Done" + value + "---" + rqstType);
                        return obj.requestGeneratorr(numericVal, rqstType);

                    }
                } else {

                    System.out.println(" else ----");
                    String numericVal = "0";
                    requestGenerator obj = new requestGenerator();
                    System.out.println("Done" + numericVal + "---" + rqstType);
                    return obj.requestGeneratorr(numericVal, rqstType);

                }
       
            } catch (Exception e) {
                e.printStackTrace();
     
            }

        }

        //outPut.add(ja);
        return null;

    }
    
}
