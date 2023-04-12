/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

/**
 *
 * @author MBSL2523
 */
import com.mbsl.Properties;
import com.mbsl.velocity.as400.environment.AS400Libraries;
import com.mbsl.velocity.dal.as400.AS400DBConnection;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;

public class GetSignatureUSers {

    @Autowired
    Properties properties;

    EnvChange obj = new EnvChange();
    String envr = obj.envirenment();

    public String setSignatureUser(String adUser, String fullName) {

        AS400Libraries libs = new AS400Libraries();
        AS400DBConnection.setLibraries(libs.getLibraries(envr));
        int st2 = 0;
        System.out.println("AD USer----------" + adUser + "        " + fullName);

        String queryAs400Add = new StringBuffer("INSERT INTO MADMAP00 (MADMBKN ,MADMSID, MADMAID, MADMANM ) values (")
                .append(1)
                .append(",'").append(adUser.trim())
                .append("','").append(adUser.trim())
                .append("','").append(fullName.trim())
                .append("')")
                .toString();

        st2 = AS400DBConnection.changeData(queryAs400Add);

        return "Success";

    }

    public String getSignatureUser(String adUser) {

        AS400Libraries libs = new AS400Libraries();
        AS400DBConnection.setLibraries(libs.getLibraries(envr));
        String sigUser = "";

        StringBuffer query = new StringBuffer().append("SELECT MADMSID FROM MADMAP00 WHERE MADMAID='" + adUser + "'");

        try (ResultSet rs = AS400DBConnection.search(query.toString())) {

            if (rs.next()) {

                sigUser = rs.getString(1);
                System.out.println("----------------------------------------------"+sigUser);

            }

        } catch (Exception e) {
        }

        return sigUser;
    }

}
