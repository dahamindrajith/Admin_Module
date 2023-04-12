/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import com.mbsl.velocity.dal.as400.AS400DBConnection;
import java.sql.ResultSet;

/**
 *
 * @author MBSL2523
 */
public class Payment {

    public String transferPayment(String epf, String newBranchDept, String newCC) {
        String rtn = "0";
        int st2 = 0;

        int epfLenth = epf.length();
        String subEpf = epf.substring(4, epfLenth);
        System.out.println("---------------"+subEpf);

        String query = new StringBuffer().append("SELECT MPFUSCC FROM MMDPF00 WHERE MPFEPF = '" + subEpf + "'").toString();
        try (ResultSet rs = AS400DBConnection.search(query)) {

            if (rs.next()) {

                String queryAs400Add = new StringBuffer("UPDATE MMDPF00 SET MPFUSCC=").append(newCC)
                        .append(",MPFCDES='").append(newBranchDept)
                        .append("' WHERE MPFEPF='").append(subEpf)
                        .append("'").toString();

                st2 = AS400DBConnection.changeData(queryAs400Add);

                if (st2 > 0) {
                    rtn = "1";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }
}
