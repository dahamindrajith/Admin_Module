/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.Classes.EnvChange;
import com.mbsl.DbConn;
import com.mbsl.Properties;
import com.mbsl.dao.TellerDao;
import com.mbsl.model.Teller;
import com.mbsl.model.User;
import com.mbsl.velocity.as400.environment.AS400Libraries;
import com.mbsl.velocity.dal.as400.AS400DBConnection;
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
 * @author MBSL2491
 */
@Repository
public class TellerDaoImpl implements TellerDao {

    private static final Logger LOGGER = LogManager.getLogger(ProfileDaoImpl.class);

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
    public String createTeller(Teller tellerCreate) {
        System.out.println("2491");

        String bod = "";

        bod = tellerCreate.getBranch().trim();
        String[] parts = bod.split("-");
        String cCode = parts[0];

        String availability = checkAvailability(cCode, tellerCreate.getTill(), tellerCreate.getTillType(), 0);

        try {
            if (availability.equals("0")) {
                String sql = "INSERT INTO admin_config_teller(branch,till,till_type,status) VALUES (?,?,?,?)";
                int result = jdbcTemplate.update(sql, cCode.trim(), tellerCreate.getTill().trim(), tellerCreate.getTillType().trim(), "A");

                if (result > 0) {
                    System.out.println("A sql row has been inserted.");
                }

                LOGGER.info("sucess");
            }
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }
        if (availability.equals("0")) {
            return messageSource.getMessage("teller.create.success", null, Locale.US);
        } else {
            return messageSource.getMessage("teller.create.error", null, Locale.US);
        }

    }

    private String checkAvailability(String bod, String till, String type, int updateOrCreate) {
        DbConn conn = new DbConn();
        String setType = "'" + bod.trim() + "'";
        String setType1 = "'" + till.trim() + "'";
        String setType2 = "";
        String value = "0";

        if (updateOrCreate == 1) {
            setType2 = " AND till_type='"+ type.trim() + "'";
        }

        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT branch FROM admin_config_teller  where status='A' and branch=" + setType + " AND till=" + setType1+setType2);
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
    public List<Teller> getAllTellers() {

        DbConn conn = new DbConn();
        List<Teller> userList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("select branch, till, till_type FROM admin_config_teller where status='A'");
            while (rs.next()) {

                Teller user = new Teller();
                user.setBranch(rs.getString(1));
                user.setTill(rs.getString(2));
                user.setTillType(rs.getString(3));

                userList.add(user);

                System.out.println("bbbbbbbbbbbbbbbbbbbbbbb" + userList.get(0).getBranch());

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
    public String updateTellers(Teller tellerCreate) {
        System.out.println(tellerCreate.getBranch() + "Done");
        System.out.println(tellerCreate.getTill() + "Done");

        String availability = checkAvailability(tellerCreate.getBranch(), tellerCreate.getTill(), tellerCreate.getTillType(), 1);
        System.out.println("Available---------" + availability + tellerCreate.getBranch() + "     " + tellerCreate.getTill() + "     " + tellerCreate.getTillType() + availability);
        try {
            if (availability.equals("0")) {
                String sql = "update admin_config_teller set till_type=?  where status=? and till=? and branch=?";

                int result = jdbcTemplate.update(sql, tellerCreate.getTillType().trim(), "A", tellerCreate.getTill(), tellerCreate.getBranch());

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
        if (availability.equals("0")) {
            return messageSource.getMessage("teller.update.success", null, Locale.US);
        } else {
            return messageSource.getMessage("teller.update.error", null, Locale.US);
        }
    }

    @Override
    public String deleteTellers(Teller tellerCreate) {
        System.out.println("222" + tellerCreate.getBranch() + " " + tellerCreate.getTill());

        try {
            String sql2 = "update admin_config_teller set status=? where branch=? and till=? AND status=?";

            int result2 = jdbcTemplate.update(sql2, "I", tellerCreate.getBranch(), tellerCreate.getTill(), "A");

            if (result2 > 0) {
                System.out.println("A new row has been inserted.");
            }

            LOGGER.info("sucess");
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }

        return messageSource.getMessage("teller.delete.success", null, Locale.US);
    }

    @Override
    public List<Teller> getAllBranchOrDept() {

        List<Teller> branchordept = new ArrayList();
        AS400Libraries libs = new AS400Libraries();
        AS400DBConnection.setLibraries(libs.getLibraries(envr));

        StringBuffer query = new StringBuffer().append("select CFCNTR, CFTNME from CFP420 where CFCNTR < 2000 and CFCNTR>99 and CFCNTR!=999 ORDER BY CFCNTR ASC");

        try (ResultSet rs = AS400DBConnection.search(query.toString())) {

            while (rs.next()) {
                Teller array = new Teller();

                String cCode = rs.getString(1);
                String bod = rs.getString(2);

                array.setBranch(cCode + "-" + "          " + bod);
                branchordept.add(array);

            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);

        }
        System.out.println("@return--" + branchordept.get(0));
        return branchordept;
    }

    @Override
    public List<Teller> getTill(Teller tellerCreate) {
        System.out.println("Branch---" + tellerCreate.getBranch());
        List<Teller> tillList = new ArrayList();
        AS400Libraries libs = new AS400Libraries();
        AS400DBConnection.setLibraries(libs.getLibraries(envr));
        String[] parts = tellerCreate.getBranch().trim().split("-");
        String setType = "'" + parts[0] + "'";
        System.out.println("setType-----" + setType);

        StringBuffer query = new StringBuffer().append("select CLTTIL from CFP75701 where CLTBRN=" + setType);

        try (ResultSet rs = AS400DBConnection.search(query.toString())) {

            while (rs.next()) {
                System.out.println("Befotr  while---" + query);
                Teller array = new Teller();

                array.setTill(rs.getString(1).trim());
                tillList.add(array);

            }

            LOGGER.info("sucess");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        //outPut.add(ja);
        return tillList;
    }

    @Override
    public Teller getTellerByBranch(Teller tellerCreate) {
        List<Teller> tellerDetail = new ArrayList();
        Teller array = new Teller();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT branch, till, till_type, status from admin_config_teller where branch=? and till=? and status='A'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tellerCreate.getBranch());
            ps.setString(2, tellerCreate.getTill());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                array.setBranch(rs.getString(1));
                array.setTill(rs.getString(2));
                array.setTillType(rs.getString(3));

                tellerDetail.add(array);

                System.out.println("bbbbbbbbbbb" + tellerDetail.get(0).getBranch());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        //outPut.add(ja);
        return array;
    }

    @Override
    public List<Teller> getTellerByUBranch(Teller tellerCreate) {

        System.out.println("-----------" + tellerCreate.getBranch());
        List<Teller> tellerDetail = new ArrayList();

        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();

            String sql1 = "";
            System.out.println("-----------" + tellerCreate.getTillType());
            if (tellerCreate.getTillType().equals("A")) {
                sql1 = " AND admin_config_teller.till_type IN ('BTL','BML')";
            } else {
                sql1 = "";
            }
            String sql = "SELECT admin_config_teller.till from admin_config_teller INNER JOIN admin_branch ON admin_branch.br_code=admin_config_teller.branch WHERE admin_branch.br_name=? AND admin_config_teller.status='A'" + sql1;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tellerCreate.getBranch().trim());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Teller array = new Teller();
                array.setBranch(rs.getString(1));

                String check = checkPrvTill(tellerCreate.getBranch().trim(), rs.getString(1), connection);

                if (check.equals("N")) {

                    tellerDetail.add(array);

                }

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return tellerDetail;
    }

    private String checkPrvTill(String brnach, String tillNum, Connection con) {

        String results = "N";
        String sql = "SELECT * FROM admin_main_table WHERE new_br=? AND new_till=? AND progres_stus_code!=25 AND status=?";

        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, brnach);
            ps.setString(2, tillNum);
            ps.setString(3, "A");
            System.out.println("Query " + ps);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                results = "Y";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;

    }

}
