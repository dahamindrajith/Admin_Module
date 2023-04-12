/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import com.mbsl.DbConn;
import com.mbsl.velocity.dal.as400.AS400DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MBSL2523
 */
public class SignatureApprove {

    public boolean signatureApprovel(String velocityProf, String bod, String uidSig, String bodCode) {

        DbConn conn = new DbConn();
        List<String> sigList = new ArrayList<>();
        int st2 = 0;
        boolean rtnStts = false;
        String sql = "";

        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT signature_profile FROM admin_signature_profile WHERE velocity_profile='" + velocityProf + "'");

            if (rs.next()) {

                String sigProf = rs.getString(1);

                if (bod.equals("Branch")) {
                    System.out.println("Branch------------------");
                    String query = new StringBuffer().append("SELECT MRUSER FROM MUSRRBT01 WHERE MRBRCH=" + bodCode + " AND MRUSDES='" + sigProf + "'").toString();
                    try (ResultSet rsc1 = AS400DBConnection.search(query)) {

                        String queryAs400Add = new StringBuffer("UPDATE MUSRRBT01 SET MRUSER='").append(uidSig)
                                .append("' WHERE MRBRCH=").append(bodCode)
                                .append(" AND MRUSDES='").append(sigProf)
                                .append("'").toString();

                        st2 = AS400DBConnection.changeData(queryAs400Add);
                        rtnStts = true;
                    }
                } else if (bod.equals("Department")) {
                    System.out.println("Department------------------");
                    String query = new StringBuffer().append("SELECT MRUSER FROM MUSRRBT01 WHERE MRBRCH=" + bodCode + " AND MRUSDES='" + sigProf + "'").toString();
                    try (ResultSet rsc1 = AS400DBConnection.search(query)) {

                        String queryAs400Add = new StringBuffer("UPDATE MUSRRBT01 SET MRUSER='").append(uidSig)
                                .append("' WHERE MRBRCH=").append(bodCode)
                                .append(" AND MRUSDES='").append(sigProf)
                                .append("'").toString();

                        st2 = AS400DBConnection.changeData(queryAs400Add);
                        rtnStts = true;
                    }
                } else {
                    System.out.println("Region------------------");
                    ResultSet rs1 = connection.createStatement().executeQuery("SELECT region_code FROM admin_region_office WHERE region_cc='" + bodCode + "'");

                    if (rs1.next()) {

                        String regCode = rs1.getString(1);
                        String query = new StringBuffer().append("SELECT MRUSER FROM MUSRRBT01 WHERE MRUVAL=" + regCode + " AND MRUSDES='" + sigProf + "'").toString();
                        try (ResultSet rsc1 = AS400DBConnection.search(query)) {

                            String queryAs400Add = new StringBuffer("UPDATE MUSRRBT01 SET MRUSER='").append(uidSig)
                                    .append("' WHERE MRUSDES='").append(sigProf)
                                    .append("' AND MRUVAL=").append(regCode)
                                    .append("").toString();

                            st2 = AS400DBConnection.changeData(queryAs400Add);
                            rtnStts = true;
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rtnStts;
    }

}
