/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import com.mbsl.DbConn;
import com.mbsl.Properties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author MBSL2523
 */
public class BranchDeptCode {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Autowired
    Properties properties;

    public String getBrDeptCode(String bod, String bodName) throws SQLException {

        DbConn conn = new DbConn();
        String rtnCode = "";
        System.out.println("bod------------------" + bod + "    " + bodName);

        if (bod.equals("Branch")) {

            Connection connection = conn.getConn();
            String sql = "SELECT br_code FROM admin_branch WHERE br_name=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bodName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rtnCode = rs.getString(1);
            }
        } else if (bod.equals("Department")) {

            Connection connection = conn.getConn();
            String sql = "SELECT dept_code FROM admin_dept WHERE dept_name=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bodName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rtnCode = rs.getString(1);
            }
        } else {

            Connection connection = conn.getConn();
            String sql = "SELECT re_code FROM admin_region_office WHERE region_office_name=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bodName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rtnCode = rs.getString(1);
            }
        }

        return rtnCode;

    }

}
