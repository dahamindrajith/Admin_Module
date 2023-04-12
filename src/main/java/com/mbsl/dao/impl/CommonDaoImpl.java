/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.dao.CommonDao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MBSL2395
 */
@Repository
public class CommonDaoImpl implements CommonDao {

    private static final Logger LOGGER = LogManager.getLogger(CommonDaoImpl.class);

    @Autowired
    DataSource dataSource;

    @Override
    public JsonArray getMyBranch(String userName, String bnk) {
        System.out.println("Bank ----------- " + bnk);
        JsonArray outPut = new JsonArray();
        String accessModel;
        String bod;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();
//            ResultSet rs = connection.createStatement().executeQuery("select IFNULL(dept_branch,'ERROR'),access_modal,first_name,last_name,designation,display_name,br_dep_des FROM uac_users WHERE bank_code = '" + bnk + "' AND user_name = '" + userName + "'");
            ResultSet rs = connection.createStatement().executeQuery("SELECT admin_main_table.new_br, admin_access_assign.access_code, admin_user_creation_det.new_user_name, admin_main_table.new_desig, admin_main_table.new_br, admin_main_table.branch_or_dept FROM admin_main_table INNER JOIN admin_user_creation_det ON admin_user_creation_det.user_id=admin_main_table.user_id INNER JOIN admin_access_assign ON admin_access_assign.user_id= admin_main_table.user_id WHERE admin_main_table.progres_stus_code!=25 AND admin_main_table.progres_stus='Approved' AND admin_access_assign.status='A' AND admin_user_creation_det.status='A' AND admin_main_table.user_id='" + userName + "'");
            if (rs.next()) {

//                outPut.add(rs.getString(1));
//                accessModel = rs.getString(2);
//                outPut.add(rs.getString(3) + " " + rs.getString(4));
//                outPut.add(rs.getString(5));
//                outPut.add(rs.getString(6));
//                outPut.add(rs.getString(7));
                String name = rs.getString(3);
                bod = rs.getString(6);

                String[] splitName = name.split(" ");

                if (bod.equals("Department")) {

                    outPut.add(rs.getString(1));
                    accessModel = rs.getString(2);
                    outPut.add(rs.getString(3));
                    outPut.add(rs.getString(4));
                    outPut.add(splitName[0]);
                    outPut.add(rs.getString(5));

                    String[] access = accessModel.split(",");

                    for (String acces : access) {
                        ResultSet rs1 = connection.createStatement().executeQuery("select access_url FROM uac_model_access WHERE app_code = 'AdminM' AND model_code  ='" + acces + "' and bank_code = '" + bnk + "' ");
                        while (rs1.next()) {
                            ja.add(rs1.getString(1));
                        }

                    }

                } else if (bod.equals("Branch")) {
                    ResultSet rs3 = connection.createStatement().executeQuery("SELECT admin_branch.br_code FROM admin_branch INNER JOIN admin_main_table ON admin_main_table.new_br=admin_branch.br_name WHERE admin_main_table.progres_stus_code!=25 AND admin_main_table.progres_stus='Approved' AND admin_main_table.user_id='" + userName + "'");
                    if (rs3.next()) {

                        outPut.add(rs3.getString(1));
                        accessModel = rs.getString(2);
                        outPut.add(rs.getString(3));
                        outPut.add(rs.getString(4));
                        outPut.add(splitName[0]);
                        outPut.add(rs.getString(5));

                        String[] access = accessModel.split(",");

                        for (String acces : access) {
                            ResultSet rs1 = connection.createStatement().executeQuery("select access_url FROM uac_model_access WHERE app_code = 'AdminM' AND model_code  ='" + acces + "' and bank_code = '" + bnk + "' ");
                            while (rs1.next()) {
                                ja.add(rs1.getString(1));
                            }

                        }

                    }
                } else {

                    ResultSet rs3 = connection.createStatement().executeQuery("SELECT admin_region_office.region_office_name FROM admin_region_office INNER JOIN admin_main_table ON admin_main_table.new_br=admin_region_office.region_office_name WHERE admin_main_table.progres_stus_code!=25 AND admin_main_table.progres_stus='Approved' AND admin_main_table.user_id='" + userName + "'");
                    if (rs3.next()) {

                        outPut.add(rs3.getString(1));
                        accessModel = rs.getString(2);
                        outPut.add(rs.getString(3));
                        outPut.add(rs.getString(4));
                        outPut.add(splitName[0]);
                        outPut.add(rs.getString(5));

                        String[] access = accessModel.split(",");

                        for (String acces : access) {
                            ResultSet rs1 = connection.createStatement().executeQuery("select access_url FROM uac_model_access WHERE app_code = 'AdminM' AND model_code  ='" + acces + "' and bank_code = '" + bnk + "' ");
                            while (rs1.next()) {
                                ja.add(rs1.getString(1));
                            }

                        }

                    }

                }

            }
            LOGGER.info("sucess");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        outPut.add(ja);
        return outPut;
    }
}
