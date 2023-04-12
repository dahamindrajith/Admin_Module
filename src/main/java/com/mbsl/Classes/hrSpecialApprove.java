/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import com.mbsl.DbConn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author MBSL2523
 */
public class hrSpecialApprove {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    EnvChange obj = new EnvChange();
    String envr = obj.envirenment();

    public String hrApprovel(String bod, String branchOrDept, String profile, String Epf, String fullName, String reqstType, String rqstId, String oldBranchODept, String desigN, String till) throws SQLException {

        System.out.println("bod--------" + bod);
        System.out.println("branchOrDept--------" + branchOrDept);

        DbConn conn = new DbConn();
        Connection connection = conn.getConn();
        String msg = "";

        if (bod.equals("Branch")) {

            System.out.println("Inside AS400----------------------" + profile);
            ResultSet rs7 = connection.createStatement().executeQuery("SELECT br_code FROM admin_branch WHERE br_name='" + branchOrDept + "'");

            if (rs7.next()) {

                System.out.println("rs7----------------------");
                String brCode = rs7.getString(1);

                String mkt = "N";

                if (profile.equals("Marketing Officer")) {
                    mkt = "Y";
                }

                ResultSet rs8 = connection.createStatement().executeQuery("SELECT velocity_group FROM admin_config_profile WHERE profile='" + profile + "' AND status='A'");

                if (rs8.next()) {

                    String programeSigPorf = rs8.getString(1);

/////////////////////////////////////////SIGNATURE USER////////////////////////////////                                               
                    GetSignatureUSers sigProf = new GetSignatureUSers();
                    String signatureProfile = sigProf.getSignatureUser(Epf);
                    System.out.println("signatureProfile--------" + signatureProfile);
//////////////////////////////////////////////////////////////////////////////////////////////    

                    if (!signatureProfile.equals("")) {

                        AS400Programme prog = new AS400Programme();
                        msg = prog.programmeRun(envr, "User Transfer", signatureProfile, programeSigPorf, "", fullName, brCode, "", "", "");
                        System.out.println("programe done----" + msg);

                        if (msg.equals("1")) {
                            try {

                                ResultSet rs1 = connection.createStatement().executeQuery("SELECT access_code FROM admin_branch_ass_acc WHERE profile='" + profile + "'");

                                if (rs1.next()) {

                                    String codeList = rs1.getString(1);

                                    AccessAssign obj1 = new AccessAssign();
                                    boolean accessDone = obj1.accessAssignFinal(reqstType, rqstId, Epf, codeList, "A", brCode, fullName, branchOrDept, "1");
                                    System.out.println("Final ok br----------------------" + accessDone + "       " + Epf);

                                    if (accessDone == true) {

                                        SignatureApprove sigp = new SignatureApprove();
                                        boolean rslt = sigp.signatureApprovel(profile, "Branch", signatureProfile, brCode);

                                        try {

                                            String sql = "UPDATE admin_temp_access SET progress_stat=?, progress_code=? WHERE user_id=? AND progress_code!=25";

                                            PreparedStatement preparedStmt = connection.prepareStatement(sql);

                                            preparedStmt.setString(1, "Approved");
                                            preparedStmt.setString(2, "4400");
                                            preparedStmt.setString(3, Epf);

                                            long reslts = preparedStmt.executeUpdate();
//                                            int result = jdbcTemplate.update(sql, "Approved", "4400", Epf);
                                            System.out.println("                 " + reslts);
                                            if (reslts == 1) {

                                                String sql1 = "UPDATE admin_main_table SET new_br=?, new_prof=?, new_desig=?, new_till=?  WHERE user_id=? AND progres_stus_code!=25";

                                                PreparedStatement preparedStmt1 = connection.prepareStatement(sql1);

                                                preparedStmt1.setString(1, branchOrDept);
                                                preparedStmt1.setString(2, profile);
                                                preparedStmt1.setString(3, desigN);
                                                preparedStmt1.setString(4, till);
                                                preparedStmt1.setString(5, Epf);

                                                long reslts1 = preparedStmt1.executeUpdate();
//                                                int result1 = jdbcTemplate.update(sql1, branchOrDept, profile, desigN, till, Epf);
                                                if (reslts1 == 1) {

                                                    ResultSet rs2 = connection.createStatement().executeQuery("SELECT ad_branch,ou FROM admin_branch WHERE br_name='" + branchOrDept + "'");
                                                    if (rs2.next()) {

                                                        String adBranch = rs2.getString(1);
                                                        String ou = rs2.getString(2);

                                                        ResultSet rs3 = connection.createStatement().executeQuery("SELECT ad_branch,ou FROM admin_branch WHERE br_name='" + oldBranchODept + "'");
                                                        if (rs3.next()) {

                                                            String oldAdBranch = rs3.getString(1);
                                                            String oldOu = rs3.getString(2);

                                                            AdCreation tst = new AdCreation(Epf, oldOu);
                                                            boolean b = tst.moveUser(ou);
                                                        }
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }
                }
            }
        } else if (bod.equals("Department")) {

            String mkt = "N";
            String brCode = "99999";
            System.out.println("-------" + profile + "   " + brCode + "  " + branchOrDept);

            ResultSet rs7 = connection.createStatement().executeQuery("SELECT dept_cc FROM admin_dept WHERE dept_name='" + branchOrDept + "'");
            if (rs7.next()) {

                String bCode = rs7.getString(1);

                ResultSet rs8 = connection.createStatement().executeQuery("SELECT velocity_group FROM admin_config_profile WHERE profile='" + profile + "' AND cc='" + bCode + "' AND status='A'");

                if (rs8.next()) {

                    String programeSigPorf = rs8.getString(1);
                    System.out.println("programeSigPorf" + programeSigPorf);

/////////////////////////////////////////SIGNATURE USER////////////////////////////////                                               
                    GetSignatureUSers sigProf = new GetSignatureUSers();
                    String signatureProfile = sigProf.getSignatureUser(Epf);
                    System.out.println("signatureProfile--------" + signatureProfile);
//////////////////////////////////////////////////////////////////////////////////////////////    

                    if (!signatureProfile.equals("")) {

                        AS400Programme prog = new AS400Programme();
                        msg = prog.programmeRun(envr, "User Transfer", signatureProfile, programeSigPorf, "", fullName, brCode, "", "", "");
                        System.out.println("programe done----" + msg);

                        if (msg.equals("1")) {
                            try {

                                ResultSet rs10 = connection.createStatement().executeQuery("SELECT dept_cc FROM admin_dept WHERE dept_name='" + branchOrDept + "'");

                                if (rs10.next()) {

                                    String cc = rs10.getString(1);
                                    System.out.println("cc--------------------" + cc);
                                    try {

                                        ResultSet rs3 = connection.createStatement().executeQuery("SELECT access_code FROM admin_department_ass_acc WHERE cc='" + cc + "' AND profile='" + profile + "'");

                                        if (rs3.next()) {
                                            String codeList = rs3.getString(1);
                                            System.out.println("Code list" + codeList);
                                            AccessAssign obj1 = new AccessAssign();
                                            boolean accessDone = obj1.accessAssignFinal(reqstType, rqstId, Epf, codeList, "A", cc, fullName, branchOrDept, "1");
                                            System.out.println("Final ok de----------------------");

                                            if (accessDone == true) {

                                                SignatureApprove sigp = new SignatureApprove();
                                                boolean rslt = sigp.signatureApprovel(profile, "Department", signatureProfile, cc);

                                                try {

                                                    String sql = "UPDATE admin_user_transfers SET progress_stat=?, progress_code=? WHERE user_id=? AND progress_code!=25";

                                                    int result = jdbcTemplate.update(sql, "Approved", "4100", Epf);

                                                    if (result > 0) {

                                                        String sql1 = "UPDATE admin_main_table SET new_br=?, new_prof=?, new_desig=?, new_till=?  WHERE user_id=? AND progres_stus_code!=25";

                                                        int result1 = jdbcTemplate.update(sql1, branchOrDept, profile, desigN, till, Epf);

                                                        if (result1 > 0) {

                                                            ResultSet rs2 = connection.createStatement().executeQuery("SELECT ad_dept,ou FROM admin_dept WHERE dept_name='" + branchOrDept + "'");
                                                            if (rs2.next()) {

                                                                String adDept = rs2.getString(1);
                                                                String ou = rs2.getString(2);

                                                                ResultSet rs4 = connection.createStatement().executeQuery("SELECT ad_dept,ou FROM admin_dept WHERE dept_name='" + oldBranchODept + "'");
                                                                if (rs4.next()) {

                                                                    String oldAdDept = rs4.getString(1);
                                                                    String oldOu = rs4.getString(2);

                                                                    AdCreation tst = new AdCreation(Epf, oldOu);
                                                                    boolean b = tst.moveUser(ou);
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }

                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }
                }
            }
        }

        return "";
    }

}
