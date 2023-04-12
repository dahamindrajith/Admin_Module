/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import com.mbsl.DbConn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author MBSL2523
 */
public class AccessAssign {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean accessAssignFinal(String rqstType, String rqstId, String epf, String codeList, String status, String CC, String name, String ccName, String userStts) throws SQLException {

        DbConn conn = new DbConn();
        if (rqstType.equals("User Grant")) {

            AssignPetty obj = new AssignPetty();
            boolean pettyOk = obj.pettyAssign(epf, CC, name, ccName, userStts, "User Grant");
            System.out.println("PrttyOk----------" + pettyOk);
            if (pettyOk == true) {
                try {
                    Connection connection = conn.getConn();
                    System.out.println("accessAssignFinal-------- " + rqstType);
                    System.out.println("accessAssignFinal-------- " + rqstId);
                    System.out.println("accessAssignFinal-------- " + epf);
                    System.out.println("accessAssignFinal-------- " + codeList);
                    System.out.println("accessAssignFinal-------- " + status);
//                String sql = "INSERT INTO admin_access_assign(requset_id, user_id, access_code, status) VALUES (?, ?, ?, ?)";
//
//                int result = jdbcTemplate.update(sql, rqstId, epf, codeList, status);
//--------------------------------------------------------------------------------------------------------------------
                    String sql = "INSERT INTO admin_access_assign(requset_id, user_id, access_code, status) VALUES (?, ?, ?, ?)";
//                int result = jdbcTemplate.update(sql, rqstId, epf, codeList, status);
//--------------------------------------------------------------------------------------------------------------------

                    PreparedStatement preparedStmt = connection.prepareStatement(sql);

                    preparedStmt.setString(1, rqstId);
                    preparedStmt.setString(2, epf);
                    preparedStmt.setString(3, codeList);
                    preparedStmt.setString(4, status);

                    preparedStmt.executeUpdate();
                    System.out.println("Done---------------------!");

                    connection.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (rqstType.equals("User Inactivate")) {

            AssignPetty obj = new AssignPetty();
            boolean pettyOk = obj.pettyAssign(epf, CC, name, ccName, userStts, "User Inactivate");
            System.out.println("PrttyOk----------" + pettyOk);
            if (pettyOk == true) {
                try {
                    Connection connection = conn.getConn();
                    System.out.println("accessAssignFinal-------- " + rqstType);
                    System.out.println("accessAssignFinal-------- " + rqstId);
                    System.out.println("accessAssignFinal-------- " + epf);
                    System.out.println("accessAssignFinal-------- " + codeList);
                    System.out.println("accessAssignFinal-------- " + status);

                    String sql = "update admin_access_assign set requset_id=?, status=? where user_id=?";

//                int result = jdbcTemplate.update(sql, rqstId, codeList, status, epf);
                    PreparedStatement preparedStmt = connection.prepareStatement(sql);

                    preparedStmt.setString(1, rqstId);
                    preparedStmt.setString(2, status);
                    preparedStmt.setString(3, epf);

                    preparedStmt.executeUpdate();
                    System.out.println("Done updated---------------------!");

                    connection.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (rqstType.equals("User Transfer")) {
///////////////////// User Transfer

            AssignPetty obj = new AssignPetty();
            boolean pettyOk = obj.pettyAssign(epf, CC, name, ccName, userStts, "User Transfer");
            System.out.println("PrttyOk----------" + pettyOk);
            if (pettyOk == true) {
                try {
                    Connection connection = conn.getConn();
                    System.out.println("accessAssignFinal-------- " + rqstType);
                    System.out.println("accessAssignFinal-------- " + rqstId);
                    System.out.println("accessAssignFinal-------- " + epf);
                    System.out.println("accessAssignFinal-------- " + codeList);
                    System.out.println("accessAssignFinal-------- " + status);

                    String sql = "update admin_access_assign set requset_id=? , access_code=? , status=? where user_id=?";

                    PreparedStatement preparedStmt = connection.prepareStatement(sql);

                    preparedStmt.setString(1, rqstId);
                    preparedStmt.setString(2, codeList);
                    preparedStmt.setString(3, status);
                    preparedStmt.setString(4, epf);

                    preparedStmt.executeUpdate();
                    System.out.println("Done updated---------------------!");

                    connection.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
////////// temp accss
            AssignPetty obj = new AssignPetty();
            
                try {
                    Connection connection = conn.getConn();
                    System.out.println("accessAssignFinal-------- " + rqstType);
                    System.out.println("accessAssignFinal-------- " + rqstId);
                    System.out.println("accessAssignFinal-------- " + epf);
                    System.out.println("accessAssignFinal-------- " + codeList);
                    System.out.println("accessAssignFinal-------- " + status);

                    String sql = "update admin_access_assign set requset_id=? , access_code=? , status=? where user_id=?";

                    PreparedStatement preparedStmt = connection.prepareStatement(sql);

                    preparedStmt.setString(1, rqstId);
                    preparedStmt.setString(2, codeList);
                    preparedStmt.setString(3, status);
                    preparedStmt.setString(4, epf);

                    preparedStmt.executeUpdate();
                    System.out.println("Done updated---------------------!");

                    connection.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
          

        }
        return true;
    }

}
