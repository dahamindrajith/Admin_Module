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
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author MBSL2523
 */
public class ConfigEmail {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Autowired
    Properties properties;

    public String configMail(String EmailAddres, String fullName, String uName, String Marcode) {

        String rtn = "Fail";
        try {
            EmailSendingHelper emailHelpernew = EmailSendingHelper.getInstance();

            String[] messageto = {EmailAddres};
            String[] bcc = {};

            //String[] cc = {emaillistcc.getEmailgroup()};
            //String[] cc = {emailgroucc[0], emailgroucc[1]};
            // String ccc = emaillistcc.getEmailgroup();
            System.out.println("Email address-------------" + EmailAddres);
            String subject = " New User Loggings for Signature/Velocity - " + uName;
            String message = "<p style='font-size: 15px;'>Dear Sir/Madam" + "<br>";
            message += "<br>";

            message += "<b style='color: red;'>" + fullName + " " + uName + "</b>" + "  user’s Signature and Velocity credentials were created.";

            message += "<p style='text-decoration: underline;'><b>User Credentials for Signature: </b>" + "</p>";

            message += "Initial credentials for the Signature System, Velocity and Active Directory as below. Onetime password and user will be forced to change the password at the first login attempt. Please contact Nuwan (Ext:1506) or Ravindra (Ext:1502) for any inquiries related to the Marketing Code creation.";
            message += "<br>";
            message += "<br>";
            message += "User Name : " + "<b style='color: red;'>" + uName + "</b>" + " Password :" + "<b style='color: red;'>" + " Will be sent to the user’s mobile number" + "</b>";
            message += "<br>";
            message += "<br>";
            message += "1. Minimum Password Length     : " + "8 Characters";
            message += "<br>";
            message += "2. Maximum Password Length     : " + "10 Characters";
            message += "<br>";
            message += "3. Maximum invalid login attempts     : " + "3  invalid attempts";
            message += "<br>";
            message += "4. Password expiration     : " + "30 days";
            message += "<br>";
            message += "5. Password history     : " + "12 passwords";
            message += "<br>";
            message += "6. Password Complexity should be defined as follows: ";
            message += "<br>";
            message += "Must use at least a numeric character (A Number)     (ex: m2b3sl@1, a2b3c4@5 -Any number 0 - 9) ";
            message += "<br>";
            message += "<b style='color: red;'>" + "Must" + "</b>" + " use one of a special character from below ";
            message += "<br>";
            message += " @ , # , $ , _ ";
            message += "<br>";
            message += "<b style='color: red;'>" + "NOTE:" + "</b>" + " Any special characters other than “ @ , # , $ , _ “ are not accepted by the Signature System. ";
            message += "<br>";
            message += " Start with a numeric character (A Number) " + "<b style='color: red;'>" + "is not allowed" + "</b>" + "      " + "(ex:" + "<b style='color: red;'>" + "5" + "</b>" + "mbsl@sl," + "<b style='color: red;'>" + "2" + "</b>" + "@slmbsl –" + "<b style='color: red;'>" + "Not allowed)" + "</b>";
            message += "<br>";
            message += " Adjoining numeric characters (adjacent numbers) " + "<b style='color: red;'>" + "are not allowed" + "</b>" + "      " + "(ex: mbsl5@" + "<b style='color: red;'>" + "25," + "</b>" + "<b style='color: red;'>" + "-Not allowed)" + "</b>";
            message += "<br>";
            message += " Adjoining characters (adjacent characters) " + "<b style='color: red;'>" + "are not allowed" + "</b>" + "       " + "(ex:" + "<b style='color: red;'>" + "aa" + "</b>" + "bcde@5,mbsl#8" + "<b style='color: red;'>" + "bb" + "</b>" + "<b style='color: red;'>" + "-Not allowed)" + "</b>";
            message += "<br>";
            message += " Adjoining special characters (adjacent special characters) " + "      " + "(ex: mbsl5" + "<b style='color: red;'>" + "@#" + "</b>" + "9" + "<b style='color: red;'>" + "-Not allowed)" + "</b>";
            message += "<br>";
            message += "<br>";

            message += "<b style='color: black;'> Signature Password Examples : " + "</b>" + "<b style='color: red;'> dfws@ry3, a5y2t7#2, mbslit@5 " + "</b>";
            message += "<br>";
            message += "<br>";
            message += "URL: http://10.1.1.8:9080/B1_Signature_90/entry";
            message += "<br>";
            message += "<br>";
            message += "<b>Active Directory Password Policy as follows.</b>";
            message += "<br>";
            message += "1. Minimum Password Length     : " + "8 Characters";
            message += "<br>";
            message += "2. Maximum invalid login attempts     : " + "3 invalid attempts";
            message += "<br>";
            message += "3. Password expiration     : " + "12 passwords";
            message += "<br>";
            message += "4. Not contain the user's account name/parts of the user's full name that exceed two consecutive characters";
            message += "<br>";
            message += "5. Must use at least one numeric character     : (A Number)(ex: 0 - 7)";
            message += "<br>";
            message += "6. Must use at least English uppercase characters   : (ex: A through Z)";
            message += "<br>";
            message += "7. Must use at least English lowercase characters   : (ex: a through z)";
            message += "<br>";
            message += "8. Must use a special character  : (ex: @, $, #, &)";
            message
                    += "<br>";

            if (!Marcode.equalsIgnoreCase(
                    "   ")) {
                message += "<p style='text-decoration: underline;'><b>Marketing Officer Details </b>" + "</p>";
                message += "<br>";
                message += "Officer Code: " + "<b style='color: red;'>" + Marcode + "</b>";
                message += "<br>";
            }
            message += "<p style='text-decoration: underline;'><b>User Credentials for Velocity </b>" + "</p>";
            message += "<br>";
            message += "Please note that Velocity Username and Password is same as the Windows Login details. Use Google Chrome Browser for Velocity system. If you need any technical support pls contact Mr.Sampath through Ext:1514.";
            message += "<br>";
            message += "<br>";
            message += "URL: http://10.1.1.116:8080";
            message += "<br>";
            message += "<br>";
            message += "<p  style='text-decoration: underline;'><b>Communication Channels </b>" + "</p>";
            message += "<br>";
            message += "For System Support";
            message += "<br>";

            String Table = "<table border='1' cellpadding='1' cellspacing='0' style='width:620px'>";
            Table += "<thead>";
            Table += "<tr>";
            Table += "<th scope='col'style='font-size:15px;'>Signature System</th>";
            Table += "<th scope='col' style='font-size:15px;'>T4S System (Teller)</th>";
            Table += "<th scope='col'style='font-size:15px;'>Appraisal System (SKALE)</th>";
            Table += "<th scope='col' style='font-size:15px;'>Windows/Email</th>";
            Table += "</tr>";
            Table += "</thead>";
            Table += "<tbody>";

            Table += "<tr>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + "Malik – Ext:1352" + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Nadeeraka – Ext:1350 " + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Heshana – Ext:1503" + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Charith - Ext:1508" + "</td>";

            Table += "</tr>";
            Table += "<tr>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + "Iresha – Ext: 1353" + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Ravindra – Ext: 1502 " + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Kingsly – Ext:1775" + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Randika – Ext:1507" + "</td>";

            Table += "</tr>";
            Table += "<tr>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + "Eshan – Ext: 1351" + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "  " + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Dilupa – Ext:1517" + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Priyantha – Ext:1518" + "</td>";

            Table += "</tr>";

            Table += "</tbody>";
            Table += "</table>";

            message += Table;

            message += "<br>";
            message += "<br>";
            message += "<b> Velocity System Support </b>";
            message += "<br>";
            message += "<br>";
            message += "Courier, Slips, Bill Payment, Financial Reports – Dilupa(Ext:1517)";
            message += "<br>";
            message += "Bulk Uploader, Correspondence, Merging Trading, AS400 Spool Reports – Sampath/Dilupa (Ext:1514/1517)";
            message += "<br>";
            message += "Petty Cash, Staff Pay – Sampath (Ext:1514)";
            message += "<br>";
            message += "Risk Profiling- Dilupa (Ext:1517)";
            message += "<br>";
            message += "<br>";
            message += "<b>For Password Reset </b>";

            String Table2 = "<table border='1' cellpadding='1' cellspacing='0' style='width:600px'>";
            Table2 += "<thead>";
            Table2 += "<tr>";
            Table2 += "<th scope='col'style='font-size:15px;'>Signature/ T4S</th>";
            Table2 += "<th scope='col' style='font-size:15px;'>Windows/Email/Velocity</th>";
            Table2 += "</tr>";
            Table2 += "</thead>";
            Table2 += "<tbody>";

            Table2 += "<tr>";
            Table2 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + "Nuwan – Ext:1506" + "</td>";
            Table2 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Charith-Ext:1508 " + "</td>";
            Table2 += "</tr>";

            Table2 += "<tr>";
            Table2 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + "Ravindra – Ext: 1502" + "</td>";
            Table2 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Priyantha – Ext:1518 " + "</td>";
            Table2 += "</tr>";

            Table2 += "<tr>";
            Table2 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + "Charith - Ext:1508" + "</td>";
            Table2 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Randika – Ext:1507 " + "</td>";
            Table2 += "</tr>";

            Table2 += "<tr>";
            Table2 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + "Priyantha – Ext:1518" + "</td>";
            Table2 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Ravindra – Ext: 1502 " + "</td>";
            Table2 += "</tr>";

            Table2 += "</tbody>";
            Table2 += "</table>";

            message += Table2;

            message += "<br>";
            message += "<br>";
            message += "<b>For System Login Access Inquiries</b>";

            String Table3 = "<table border='1' cellpadding='1' cellspacing='0' style='width:600px'>";
            Table3 += "<thead>";
            Table3 += "<tr>";
            Table3 += "<th scope='col'style='font-size:15px;'>Signature System</th>";
            Table3 += "<th scope='col' style='font-size:15px;'>T4S System (Teller)</th>";
            Table3 += "<th scope='col'style='font-size:15px;'>Appraisal System (SKALE)</th>";
            Table3 += "<th scope='col' style='font-size:15px;'>Windows/Email</th>";
            Table3 += "</tr>";
            Table3 += "</thead>";
            Table3 += "<tbody>";

            Table3 += "<tr>";
            Table3 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + "Chathuni – Ext:1774" + "</td>";
            Table3 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Nadeeraka – Ext:1350 " + "</td>";
            Table3 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Heshana – Ext:1503" + "</td>";
            Table3 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Charith-Ext:1508" + "</td>";

            Table3 += "</tr>";
            Table3 += "<tr>";
            Table3 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + "Nuwan – Ext:1506" + "</td>";
            Table3 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Ravindra – Ext: 1502 " + "</td>";
            Table3 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Sampath – Ext:1514" + "</td>";
            Table3 += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + "Randika – Ext:15070" + "</td>";

            Table3 += "</tbody>";
            Table3 += "</table>";

            message += Table3;
            message += "<br><p>";

            message += "Thank you!<br>";
            message += "<br><p>";
            message += "<h4 style='color: red;'> NOTE: THIS IS AN AUTO GENERATED EMAIL. PLEASE DO NOT REPLY.</h4><br>";
            message += "<br>";

            String statesnew = "Blank";

            rtn = "Success";

            if (messageto.length > 0) {
                statesnew = emailHelpernew.sendEmail(subject, message, messageto, bcc, true);
                DbConn conn = new DbConn();
                Connection connection = conn.getConn();
                try {

                    String sql = "UPDATE admin_user_creation_det SET email_send_stat=? WHERE user_id=? AND status=?";

                    PreparedStatement updateTable = connection.prepareStatement(sql);
                    updateTable.setString(1, "Sent");
                    updateTable.setString(2, uName);
                    updateTable.setString(3, "A");
                    int results = updateTable.executeUpdate();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return rtn;
    }

    public String tforS(String emailAddres, String rqstID, String userID, String branch, String fullName, String tillNo, String reqstType, String bod) {
        System.out.println("-------------------------------1");
        String rtn = "Fail";
        String tillType = "";
        try {
            DbConn conn = new DbConn();
            Connection connection = conn.getConn();

            if (!tillNo.equals("")) {
                if (bod.equals("Branch") && !tillNo.equals("-")) {

                    String sql = "SELECT admin_config_teller.till_type FROM admin_config_teller INNER JOIN admin_branch ON admin_branch.br_code=admin_config_teller.branch WHERE admin_branch.br_name=? AND admin_config_teller.till=? AND admin_config_teller.status=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, branch);
                    ps.setString(2, tillNo);
                    ps.setString(3, "A");

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        tillType = rs.getString(1);
                    }

                } else if (bod.equals("Department") && !tillNo.equals("-")) {

                    String sql = "SELECT admin_config_teller.till_type FROM admin_config_teller INNER JOIN admin_dept ON admin_dept.dept_cc=admin_config_teller.branch WHERE admin_dept.dept_name=? AND admin_config_teller.till=? AND admin_config_teller.status=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, branch);
                    ps.setString(2, tillNo);
                    ps.setString(3, "A");

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        tillType = rs.getString(1);
                    }

                }
                System.out.println("-------------------------------1");
                EmailSendingHelper emailHelpernew = EmailSendingHelper.getInstance();
                emailAddres = "sanjeewau@mbslbank.com";
//                emailAddres = "dahami@mbslbank.com";
                String[] messageto = {emailAddres};
                String[] bcc = {};

                //String[] cc = {emaillistcc.getEmailgroup()};
                //String[] cc = {emailgroucc[0], emailgroucc[1]};
                // String ccc = emaillistcc.getEmailgroup();
                System.out.println("Email address-------------" + emailAddres);
                String subject = " Teller System Access For " + reqstType + " - " + userID;
                String message = "<p style='font-size: 15px;'>New " + reqstType + "<br>";
                message += "<br>";

                String Table = "<table border='1' cellpadding='1' cellspacing='0' style='width:620px'>";
                Table += "<thead>";
                Table += "<tr>";
                Table += "<th scope='col'style='font-size:15px;'>Request ID</th>";
                Table += "<th scope='col' style='font-size:15px;'>User ID</th>";
                Table += "<th scope='col'style='font-size:15px;'>Branch Or Department</th>";
                Table += "<th scope='col' style='font-size:15px;'>Full Name</th>";
                Table += "<th scope='col' style='font-size:15px;'>Till Number</th>";
                Table += "<th scope='col' style='font-size:15px;'>Till Type</th>";
                Table += "</tr>";
                Table += "</thead>";
                Table += "<tbody>";

                Table += "<tr>";
                Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + rqstID + "</td>";
                Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + userID + "</td>";
                Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + branch + "</td>";
                Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + fullName + "</td>";
                Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + tillNo + "</td>";
                Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + tillType + "</td>";

                Table += "</tbody>";
                Table += "</table>";

                message += Table;

                String statesnew = "Blank";

                rtn = "Success";

                if (messageto.length > 0) {
                    statesnew = emailHelpernew.sendEmail(subject, message, messageto, bcc, true);
                    System.out.println("Done Kaputu kaak");

                }
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return rtn;
    }
//Temp Access Grant

    public String tforStempAcc(String emailAddres, String rqstID, String userID, String branch, String fullName, String tillNo, String reqstType, String bod) {
        System.out.println("-------------------------------1");
        String rtn = "Fail";
        String tillType = "";
        String oldTill = "";
        String oldBranchOrDept = "";
        try {
            DbConn conn = new DbConn();
            Connection connection = conn.getConn();

            if (bod.equals("Branch")) {

                String sql = "SELECT admin_config_teller.till_type FROM admin_config_teller INNER JOIN admin_branch ON admin_branch.br_code=admin_config_teller.branch WHERE admin_branch.br_name=? AND admin_config_teller.till=? AND admin_config_teller.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, branch);
                ps.setString(2, tillNo);
                ps.setString(3, "A");

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    tillType = rs.getString(1);

                }

            } else if (bod.equals("Department")) {

                String sql = "SELECT admin_config_teller.till_type FROM admin_config_teller INNER JOIN admin_dept ON admin_dept.dept_cc=admin_config_teller.branch WHERE admin_dept.dept_name=? AND admin_config_teller.till=? AND admin_config_teller.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, branch);
                ps.setString(2, tillNo);
                ps.setString(3, "A");

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    tillType = rs.getString(1);

                }

            }

            try {

                String sq1 = "SELECT prev_till,prev_br_dep FROM admin_main_table WHERE user_id=? AND progres_stus_code!=25 AND status=?";
                PreparedStatement ps1 = connection.prepareStatement(sq1);
                ps1.setString(1, userID);
                ps1.setString(2, "A");

                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    oldTill = rs1.getString(1);
                    oldBranchOrDept = rs1.getString(2);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("-------------------------------1" + "     " + oldTill);
            EmailSendingHelper emailHelpernew = EmailSendingHelper.getInstance();
//            emailAddres = "sanjeewau@mbslbank.com";
            emailAddres = "sanjeewau@mbslbank.com";
//                emailAddres = "dahami@mbslbank.com";
            String[] messageto = {emailAddres};
            String[] bcc = {};

            //String[] cc = {emaillistcc.getEmailgroup()};
            //String[] cc = {emailgroucc[0], emailgroucc[1]};
            // String ccc = emaillistcc.getEmailgroup();
            System.out.println("Email address-------------" + emailAddres);
            String subject = " Teller System Access For " + reqstType + " - " + userID;
            String message = "<p style='font-size: 15px;'>New " + reqstType + "<br>";
            message += "<br>";

            String Table = "<table border='1' cellpadding='1' cellspacing='0' style='width:620px'>";
            Table += "<thead>";
            Table += "<tr>";
            Table += "<th scope='col'style='font-size:15px;'>Request ID</th>";
            Table += "<th scope='col' style='font-size:15px;'>User ID</th>";
            Table += "<th scope='col'style='font-size:15px;'>Old Branch Or Department</th>";
            Table += "<th scope='col'style='font-size:15px;'>New Branch Or Department</th>";
            Table += "<th scope='col' style='font-size:15px;'>Full Name</th>";
            Table += "<th scope='col' style='font-size:15px;'>Old Till Number</th>";
            Table += "<th scope='col' style='font-size:15px;'>New Till Number</th>";
            Table += "<th scope='col' style='font-size:15px;'>Till Type</th>";
            Table += "</tr>";
            Table += "</thead>";
            Table += "<tbody>";

            Table += "<tr>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + rqstID + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + userID + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + oldBranchOrDept + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + branch + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + fullName + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + oldTill + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + tillNo + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + tillType + "</td>";

            Table += "</tbody>";
            Table += "</table>";

            message += Table;

            String statesnew = "Blank";

            rtn = "Success";

            if (messageto.length > 0) {
                statesnew = emailHelpernew.sendEmail(subject, message, messageto, bcc, true);
                System.out.println("Done Kaputu kaak");

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return rtn;
    }
//User Inact

    public String tforSInactAcc(String emailAddres, String rqstID, String userID, String branch, String fullName, String tillNo, String reqstType, String bod) {
        System.out.println("-------------------------------1");
        String rtn = "Fail";
        String tillType = "";
        String oldTill = "";
        String branchOrDept = "";
        try {
            DbConn conn = new DbConn();
            Connection connection = conn.getConn();

            if (bod.equals("Branch")) {

                String sql = "SELECT admin_config_teller.till_type FROM admin_config_teller INNER JOIN admin_branch ON admin_branch.br_code=admin_config_teller.branch WHERE admin_branch.br_name=? AND admin_config_teller.till=? AND admin_config_teller.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, branch);
                ps.setString(2, tillNo);
                ps.setString(3, "A");

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    tillType = rs.getString(1);

                }

            } else if (bod.equals("Department")) {

                String sql = "SELECT admin_config_teller.till_type FROM admin_config_teller INNER JOIN admin_dept ON admin_dept.dept_cc=admin_config_teller.branch WHERE admin_dept.dept_name=? AND admin_config_teller.till=? AND admin_config_teller.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, branch);
                ps.setString(2, tillNo);
                ps.setString(3, "A");

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    tillType = rs.getString(1);

                }

            }

            try {

                String sq1 = "SELECT prev_till,prev_br_dep FROM admin_main_table WHERE user_id=? AND progres_stus_code!=25 AND status=?";
                PreparedStatement ps1 = connection.prepareStatement(sq1);
                ps1.setString(1, userID);
                ps1.setString(2, "A");

                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    oldTill = rs1.getString(1);
                    branchOrDept = rs1.getString(1);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("-------------------------------1" + "     " + oldTill);
            EmailSendingHelper emailHelpernew = EmailSendingHelper.getInstance();
//            emailAddres = "sanjeewau@mbslbank.com";
            emailAddres = "sanjeewau@mbslbank.com";
//                emailAddres = "dahami@mbslbank.com";
            String[] messageto = {emailAddres};
            String[] bcc = {};

            //String[] cc = {emaillistcc.getEmailgroup()};
            //String[] cc = {emailgroucc[0], emailgroucc[1]};
            // String ccc = emaillistcc.getEmailgroup();
            System.out.println("Email address-------------" + emailAddres);
            String subject = " Teller System Access For " + reqstType + " - " + userID;
            String message = "<p style='font-size: 15px;'>New " + reqstType + "<br>";
            message += "<br>";

            String Table = "<table border='1' cellpadding='1' cellspacing='0' style='width:620px'>";
            Table += "<thead>";
            Table += "<tr>";
            Table += "<th scope='col'style='font-size:15px;'>Request ID</th>";
            Table += "<th scope='col' style='font-size:15px;'>User ID</th>";
            Table += "<th scope='col'style='font-size:15px;'>Branch Or Department</th>";
            Table += "<th scope='col' style='font-size:15px;'>Full Name</th>";
            Table += "<th scope='col' style='font-size:15px;'>Till Number</th>";

            Table += "</tr>";
            Table += "</thead>";
            Table += "<tbody>";

            Table += "<tr>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + rqstID + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + userID + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + branchOrDept + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + fullName + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + oldTill + "</td>";

            Table += "</tbody>";
            Table += "</table>";

            message += Table;

            String statesnew = "Blank";

            rtn = "Success";

            if (messageto.length > 0) {
                statesnew = emailHelpernew.sendEmail(subject, message, messageto, bcc, true);
                System.out.println("Done Kaputu kaak");

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return rtn;
    }

//Transfer User
    public String tforSTransAcc(String emailAddres, String rqstID, String userID, String branchOld, String fullName, String tillNoold, String reqstType, String bod) {
        System.out.println("-------------------------------1");
        String rtn = "Fail";
        String tillType = "";
        String newTill = "";
        String newBranchOrDept = "";
        try {
            DbConn conn = new DbConn();
            Connection connection = conn.getConn();

            try {

                String sq1 = "SELECT prev_till,prev_br_dep FROM admin_main_table WHERE user_id=? AND progres_stus_code!=25 AND status=?";
                PreparedStatement ps1 = connection.prepareStatement(sq1);
                ps1.setString(1, userID);
                ps1.setString(2, "A");

                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    newTill = rs1.getString(1);
                    newBranchOrDept = rs1.getString(2);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (bod.equals("Branch")) {

                String sql = "SELECT admin_config_teller.till_type FROM admin_config_teller INNER JOIN admin_branch ON admin_branch.br_code=admin_config_teller.branch WHERE admin_branch.br_name=? AND admin_config_teller.till=? AND admin_config_teller.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, newBranchOrDept);
                ps.setString(2, newTill);
                ps.setString(3, "A");

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    tillType = rs.getString(1);

                }

            } else if (bod.equals("Department")) {

                String sql = "SELECT admin_config_teller.till_type FROM admin_config_teller INNER JOIN admin_dept ON admin_dept.dept_cc=admin_config_teller.branch WHERE admin_dept.dept_name=? AND admin_config_teller.till=? AND admin_config_teller.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, newBranchOrDept);
                ps.setString(2, newTill);
                ps.setString(3, "A");

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    tillType = rs.getString(1);

                }

            }

            System.out.println("-------------------------------1" + "    new  " + newTill + "   old " + tillNoold);
            EmailSendingHelper emailHelpernew = EmailSendingHelper.getInstance();
//            emailAddres = "sanjeewau@mbslbank.com";
            emailAddres = "sanjeewau@mbslbank.com";
//                emailAddres = "dahami@mbslbank.com";
            String[] messageto = {emailAddres};
            String[] bcc = {};

            //String[] cc = {emaillistcc.getEmailgroup()};
            //String[] cc = {emailgroucc[0], emailgroucc[1]};
            // String ccc = emaillistcc.getEmailgroup();
            System.out.println("Email address-------------" + emailAddres);
            String subject = " Teller System Access For " + reqstType + " - " + userID;
            String message = "<p style='font-size: 15px;'>New " + reqstType + "<br>";
            message += "<br>";

            String Table = "<table border='1' cellpadding='1' cellspacing='0' style='width:620px'>";
            Table += "<thead>";
            Table += "<tr>";
            Table += "<th scope='col'style='font-size:15px;'>Request ID</th>";
            Table += "<th scope='col' style='font-size:15px;'>User ID</th>";
            Table += "<th scope='col'style='font-size:15px;'>Old Branch Or Department</th>";
            Table += "<th scope='col'style='font-size:15px;'>New Branch Or Department</th>";
            Table += "<th scope='col' style='font-size:15px;'>Full Name</th>";
            Table += "<th scope='col' style='font-size:15px;'>Old Till Number</th>";
            Table += "<th scope='col' style='font-size:15px;'>New Till Number</th>";
            Table += "<th scope='col' style='font-size:15px;'>Till Type</th>";
            Table += "</tr>";
            Table += "</thead>";
            Table += "<tbody>";

            Table += "<tr>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + rqstID + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + userID + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + branchOld + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + newBranchOrDept + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + fullName + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + tillNoold + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + newTill + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + tillType + "</td>";

            Table += "</tbody>";
            Table += "</table>";

            message += Table;

            String statesnew = "Blank";

            rtn = "Success";

            if (messageto.length > 0) {
                statesnew = emailHelpernew.sendEmail(subject, message, messageto, bcc, true);
                System.out.println("Done Kaputu kaak");

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return rtn;
    }

    public String tforSTempAccRemv(String emailAddres, String rqstID, String userID, String newBranch, String oldBranch, String fullName, String newTill, String oldTill, String reqstType, String bod) {
        System.out.println("-------------------------------1");
        String rtn = "Fail";
        String tillType = "";

        String newBranchOrDept = "";
        try {
            DbConn conn = new DbConn();
            Connection connection = conn.getConn();

//            try {
//
//                String sq1 = "SELECT prev_till,prev_br_dep FROM admin_main_table WHERE user_id=? AND progres_stus_code!=25 AND status=?";
//                PreparedStatement ps1 = connection.prepareStatement(sq1);
//                ps1.setString(1, userID);
//                ps1.setString(2, "A");
//
//                ResultSet rs1 = ps1.executeQuery();
//                if (rs1.next()) {
//                    newTill = rs1.getString(1);
//                    newBranchOrDept = rs1.getString(2);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            if (bod.equals("Branch")) {

                String sql = "SELECT admin_config_teller.till_type FROM admin_config_teller INNER JOIN admin_branch ON admin_branch.br_code=admin_config_teller.branch WHERE admin_branch.br_name=? AND admin_config_teller.till=? AND admin_config_teller.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, newBranch);
                ps.setString(2, newTill);
                ps.setString(3, "A");

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    tillType = rs.getString(1);

                }

            } else if (bod.equals("Department")) {

                String sql = "SELECT admin_config_teller.till_type FROM admin_config_teller INNER JOIN admin_dept ON admin_dept.dept_cc=admin_config_teller.branch WHERE admin_dept.dept_name=? AND admin_config_teller.till=? AND admin_config_teller.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, newBranch);
                ps.setString(2, newTill);
                ps.setString(3, "A");

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    tillType = rs.getString(1);

                }

            }

            System.out.println("-------------------------------1" + "    new  " + newTill + "   old " + oldTill);
            EmailSendingHelper emailHelpernew = EmailSendingHelper.getInstance();
//            emailAddres = "sanjeewau@mbslbank.com";
            emailAddres = "sanjeewau@mbslbank.com";
//                emailAddres = "dahami@mbslbank.com";
            String[] messageto = {emailAddres};
            String[] bcc = {};

            //String[] cc = {emaillistcc.getEmailgroup()};
            //String[] cc = {emailgroucc[0], emailgroucc[1]};
            // String ccc = emaillistcc.getEmailgroup();
            System.out.println("Email address-------------" + emailAddres);
            String subject = " Teller System Access For " + reqstType + " - " + userID;
            String message = "<p style='font-size: 15px;'>New " + reqstType + "<br>";
            message += "<br>";

            String Table = "<table border='1' cellpadding='1' cellspacing='0' style='width:620px'>";
            Table += "<thead>";
            Table += "<tr>";
            Table += "<th scope='col'style='font-size:15px;'>Request ID</th>";
            Table += "<th scope='col' style='font-size:15px;'>User ID</th>";
            Table += "<th scope='col'style='font-size:15px;'>Old Branch Or Department</th>";
            Table += "<th scope='col'style='font-size:15px;'>New Branch Or Department</th>";
            Table += "<th scope='col' style='font-size:15px;'>Full Name</th>";
            Table += "<th scope='col' style='font-size:15px;'>Old Till Number</th>";
            Table += "<th scope='col' style='font-size:15px;'>New Till Number</th>";
            Table += "<th scope='col' style='font-size:15px;'>Till Type</th>";
            Table += "</tr>";
            Table += "</thead>";
            Table += "<tbody>";

            Table += "<tr>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;' >" + rqstID + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + userID + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + oldBranch + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + newBranch + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + fullName + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + oldTill + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + newTill + "</td>";
            Table += "<td style='text-align:left;vertical-align:Bottom;font-size:12px;'>" + tillType + "</td>";

            Table += "</tbody>";
            Table += "</table>";

            message += Table;

            String statesnew = "Blank";

            rtn = "Success";

            if (messageto.length > 0) {
                statesnew = emailHelpernew.sendEmail(subject, message, messageto, bcc, true);
                System.out.println("Done Kaputu kaak");

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return rtn;
    }

    public String tforSTempUserSend(String emailAddres, String rqstID, String userID, String newBranch, String fullName, String newTill, String reqstType, String bod, String fromDate, String toDate, String profile) {
        System.out.println("-------------------------------1");
        String rtn = "Fail";
        String tillType = "";

        String newBranchOrDept = "";
        try {
            DbConn conn = new DbConn();
            Connection connection = conn.getConn();


            if (bod.equals("Branch")) {

                String sql = "SELECT admin_config_teller.till_type FROM admin_config_teller INNER JOIN admin_branch ON admin_branch.br_code=admin_config_teller.branch WHERE admin_branch.br_name=? AND admin_config_teller.till=? AND admin_config_teller.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, newBranch);
                ps.setString(2, newTill);
                ps.setString(3, "A");

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    tillType = rs.getString(1);

                }

            } else if (bod.equals("Department")) {

                String sql = "SELECT admin_config_teller.till_type FROM admin_config_teller INNER JOIN admin_dept ON admin_dept.dept_cc=admin_config_teller.branch WHERE admin_dept.dept_name=? AND admin_config_teller.till=? AND admin_config_teller.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, newBranch);
                ps.setString(2, newTill);
                ps.setString(3, "A");

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    tillType = rs.getString(1);

                }

            }

            System.out.println("-------------------------------1" + "    new  " + newTill + "   old " + fromDate+"  "+toDate+" "+profile);
            EmailSendingHelper emailHelpernew = EmailSendingHelper.getInstance();
//            emailAddres = "sanjeewau@mbslbank.com";

//                emailAddres = "dahami@mbslbank.com";
            String[] messageto = {emailAddres};
            String[] bcc = {};

            //String[] cc = {emaillistcc.getEmailgroup()};
            //String[] cc = {emailgroucc[0], emailgroucc[1]};
            // String ccc = emaillistcc.getEmailgroup();
            System.out.println("Email address-------------" + emailAddres);
            String subject = reqstType + " - " + userID;
            String message = "<p style='font-size: 15px;'>"+ profile+" "+ reqstType+" Granted From "+fromDate+" To "+toDate+ "<br>";
            message += "<br>";

            String statesnew = "Blank";

            rtn = "Success";

            if (messageto.length > 0) {
                statesnew = emailHelpernew.sendEmail(subject, message, messageto, bcc, true);
                System.out.println("Done Kaputu kaak");

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return rtn;
    }

    public String comonMail(String rqstId, String reqstType, String status, String bod, String branchOrDEpartment) {

        System.out.println("------------------"+bod+"  "+reqstType+"  "+status);

        String rtn = "Fail";
        String tillType = "";
        String sqlSA = "";
        try {
            DbConn conn = new DbConn();
            Connection connection = conn.getConn();

            String sql = "SELECT profile_or_design , bod_name FROM admin_approal_users WHERE branch_or_dept=? AND rqst_type=? AND show_info_stts=?";
            PreparedStatement ps3 = connection.prepareStatement(sql);
            ps3.setString(1, bod);
            ps3.setString(2, reqstType);
            ps3.setString(3, status);

            ResultSet rs3 = ps3.executeQuery();

            if (rs3.next()) {

                String profileOrDesig = rs3.getString(1);
                String branchOrDept = rs3.getString(2);

                if (branchOrDept.equals("AB") && profileOrDesig.equals("Branch Manager")) {
                    branchOrDept = branchOrDEpartment;
                } else if (branchOrDept.equals("AB") && profileOrDesig.equals("Region Manager")) {

                    String sql2 = "SELECT admin_region_office.region_office_name FROM admin_region_office INNER JOIN admin_branch ON admin_branch.region_unit=admin_region_office.region_office WHERE admin_branch.br_name=?";
                    PreparedStatement ps4 = connection.prepareStatement(sql2);
                    ps4.setString(1, branchOrDEpartment);

                    ResultSet rs4 = ps4.executeQuery();
                    if (rs4.next()) {
                        branchOrDept = rs4.getString(1);
                    }

                } else if (branchOrDept.equals("AB") && profileOrDesig.equals("General manager(HOD)")) {
                    branchOrDept = branchOrDEpartment;
                }
                System.out.println("--------------------------------------" + branchOrDept);
                if (profileOrDesig.equals("Staff Assistant")) {

                    sqlSA = " AND admin_main_table.user_id='MBSL00579'";
                } else if (profileOrDesig.equals("Excutive")) {

                    sqlSA = " AND admin_main_table.user_id='MBSL00579'";
                }

                String sql1 = "SELECT email_add FROM admin_user_creation_det INNER JOIN admin_main_table ON admin_main_table.user_id=admin_user_creation_det.user_id WHERE admin_main_table.new_prof=? AND admin_main_table.new_br=? AND admin_main_table.status=?" + sqlSA;
                PreparedStatement ps4 = connection.prepareStatement(sql1);
                ps4.setString(1, profileOrDesig);
                ps4.setString(2, branchOrDept);
                ps4.setString(3, "A");

                ResultSet rs4 = ps4.executeQuery();
                if (rs4.next()) {

                    EmailSendingHelper emailHelpernew = EmailSendingHelper.getInstance();
                    String emailAddres = rs4.getString(1);

                    String[] messageto = {emailAddres};
                    String[] bcc = {};

                    //String[] cc = {emaillistcc.getEmailgroup()};
                    //String[] cc = {emailgroucc[0], emailgroucc[1]};
                    // String ccc = emaillistcc.getEmailgroup();
                    System.out.println("Email address-------------" + emailAddres);
                    String subject = " Velocity new Notification new " + reqstType;
                    String message = "<p style='font-size: 15px;'>Requset ID -" + rqstId + "<br>";
                    message += "<br>";

                    String statesnew = "Blank";

                    rtn = "Success";

                    if (messageto.length > 0) {
                        statesnew = emailHelpernew.sendEmail(subject, message, messageto, bcc, true);
                        System.out.println("Done Kaputu kaak");

                    }
                }
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return rtn;
    }

}
