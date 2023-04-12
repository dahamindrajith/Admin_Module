/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.AS400Text;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;
import com.mbsl.velocity.dal.as400.AS400Connection;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MBSL2523
 */
public class AS400Programme {

    public String programmeRun(String env, String rqstType, String epf, String programeSigPorf, String pss, String fullName, String brCode, String mkt, String contNo, String email) {

        System.out.println("requset Type-----------" + rqstType);
        String rtn = "0";

        if (rqstType.equals("User Grant")) {
            try {
                AS400 as400 = AS400Connection.getAS400();
                String AS400Environment = env;
                String isWindow = "";

                String inEnvName = AS400Environment;
                String inMode = "A";
                String inUserName = epf;
                //String inGroupProfile = "BO"; // UacAccessAsingde.getAccestype();
                String inGroupProfile = programeSigPorf;
                String inPassword = pss;
                String inFullName = fullName;
                String inBranchCode = brCode;
                String inIsMarketting = mkt;
                String inMobileNumber = contNo;
                String outStatus = " ";
                String outMarkettingCode = " ";
                System.out.println("inGroupProfile---" + inGroupProfile);
                String programName1 = "/QSYS.LIB/MBCLPGM.LIB/MTF0010R.PGM";

                String programName = "/QSYS.LIB/MBCLPGM.LIB/MUT0530R.PGM";

                ProgramCall program = new ProgramCall(as400);
                ProgramCall program2 = new ProgramCall(as400);

                ProgramParameter[] parameterList1 = new ProgramParameter[1];

                AS400Text textData = new AS400Text(10, as400);
                parameterList1[0] = new ProgramParameter(textData.toBytes(AS400Environment));

                ProgramParameter[] parameterList = new ProgramParameter[12];

                textData = new AS400Text(10, as400);
                parameterList[0] = new ProgramParameter(textData.toBytes(inEnvName));

                textData = new AS400Text(1, as400);
                parameterList[1] = new ProgramParameter(textData.toBytes(inMode));

                textData = new AS400Text(10, as400);
                parameterList[2] = new ProgramParameter(textData.toBytes(inUserName));

                textData = new AS400Text(10, as400);
                parameterList[3] = new ProgramParameter(textData.toBytes(inGroupProfile));

                textData = new AS400Text(10, as400);
                parameterList[4] = new ProgramParameter(textData.toBytes(inPassword));

                textData = new AS400Text(50, as400);
                parameterList[5] = new ProgramParameter(textData.toBytes(inFullName));

                textData = new AS400Text(5, as400);
                parameterList[6] = new ProgramParameter(textData.toBytes(inBranchCode));

                textData = new AS400Text(1, as400);
                parameterList[7] = new ProgramParameter(textData.toBytes(inIsMarketting));

                textData = new AS400Text(1, as400);
                parameterList[8] = new ProgramParameter(textData.toBytes(isWindow));

                textData = new AS400Text(10, as400);
                parameterList[9] = new ProgramParameter(textData.toBytes(inMobileNumber));

                textData = new AS400Text(1, as400);
                parameterList[10] = new ProgramParameter(textData.toBytes(outStatus));
                parameterList[10].setOutputDataLength(1);

                textData = new AS400Text(3, as400);
                parameterList[11] = new ProgramParameter(textData.toBytes(outMarkettingCode));
                parameterList[11].setOutputDataLength(3);

                program.setProgram(programName1, parameterList1);

                System.out.println("inEnvName +" + inEnvName);
                System.out.println("inMode +" + inMode);
                System.out.println("inUserName+" + inUserName);
                System.out.println("inGroupProfile+" + inGroupProfile);
                System.out.println("inPassword +" + inPassword);
                System.out.println("inFullName+" + inFullName);
                System.out.println("inBranchCode +" + inBranchCode);
                System.out.println("inIsMarketting +" + inIsMarketting);
                System.out.println("inIsWindow +" + isWindow);
                System.out.println("inMobileNumber +" + inMobileNumber);
                System.out.println("outStatus +" + outStatus);
                System.out.println("outMarkettingCode ++" + outMarkettingCode);

                System.out.println("inIsMarketting " + inIsMarketting);

                if (program.run() != true) {
                    // Report failure.
                    System.out.println("Program1 failed!");
                    // Show the messages.
                    AS400Message[] messageList = program.getMessageList();
                    for (int cc = 0; cc < messageList.length; ++cc) {
                        // Show each message.
                        System.out.println(messageList[cc].getText());
                        // Load additional message information.
                        messageList[cc].load();
                        //Show help text.
                        System.out.println(messageList[cc].getHelp());
                    }
                } // Else no error, get output data.
                else {
                    System.out.println("Doneeeeeeee Program1" + programName);
                    System.out.println("--- " + parameterList);
                    program2.setProgram(programName, parameterList);
//                                    textData = new AS400Text(1, as400);
//                               String    message = (String) textData.toObject(parameterList1[1].getOutputData());

                    if (program2.run() != true) {
                        // Report failure.
                        System.out.println("Program2 failed!");
                        // Show the messages.
                        AS400Message[] messageList = program.getMessageList();
                        for (int cc = 0; cc < messageList.length; ++cc) {
                            // Show each message.
                            System.out.println(messageList[cc].getText());
                            // Load additional message information.
                            messageList[cc].load();
                            //Show help text.
                            System.out.println(messageList[cc].getHelp());
                        }
                    } // Else no error, get output data.
                    else {
                        System.out.println("Doneeeeeeee Program2");
                        System.out.println("Program 2 success!");
                        textData = new AS400Text(1, as400);
                        String out4 = (String) textData.toObject(parameterList[10].getOutputData());
                        System.out.println("sssssssssssssssssssssssssssssss  " + out4);
                        if (out4.equals("S")) {
                            System.out.println("out4 " + out4);
                            textData = new AS400Text(3, as400);
                            String Marcode = (String) textData.toObject(parameterList[11].getOutputData());
                            System.out.println("Succses AAAAAAAAAAAAAAAAAAAAAAAAAAA  " + Marcode);

                            ConfigEmail obj = new ConfigEmail();
                            String msg = obj.configMail(email, fullName, epf, Marcode);
                            System.out.println("Email msg----------" + msg);
                        }

                    }
                }
                rtn = "1";

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (rqstType.equals("User Transfer")) {

            try {
                AS400 as400 = AS400Connection.getAS400();
                String AS400Environment = env;
                String isWindow = "";

                String inEnvName = AS400Environment;
                String inMode = "C";
                String sigUerName = epf;
                //String inGroupProfile = "BO"; // UacAccessAsingde.getAccestype();
                String inGroupProfile = programeSigPorf;
                String inPassword = "";
                String inFullName = fullName;
                String inBranchCode = brCode;
                String inIsMarketting = "";
                String inMobileNumber = "";
                String outStatus = " ";
                String outMarkettingCode = " ";

                String programName1 = "/QSYS.LIB/MBCLPGM.LIB/MTF0010R.PGM";

                String programName = "/QSYS.LIB/MBCLPGM.LIB/MUT0530R.PGM";

                ProgramCall program = new ProgramCall(as400);
                ProgramCall program2 = new ProgramCall(as400);

                ProgramParameter[] parameterList1 = new ProgramParameter[1];

                AS400Text textData = new AS400Text(10, as400);
                parameterList1[0] = new ProgramParameter(textData.toBytes(AS400Environment));

                ProgramParameter[] parameterList = new ProgramParameter[12];

                textData = new AS400Text(10, as400);
                parameterList[0] = new ProgramParameter(textData.toBytes(inEnvName));

                textData = new AS400Text(1, as400);
                parameterList[1] = new ProgramParameter(textData.toBytes(inMode));

                textData = new AS400Text(10, as400);
                parameterList[2] = new ProgramParameter(textData.toBytes(sigUerName));

                textData = new AS400Text(10, as400);
                parameterList[3] = new ProgramParameter(textData.toBytes(inGroupProfile));

                textData = new AS400Text(10, as400);
                parameterList[4] = new ProgramParameter(textData.toBytes(inPassword));

                textData = new AS400Text(50, as400);
                parameterList[5] = new ProgramParameter(textData.toBytes(inFullName));

                textData = new AS400Text(5, as400);
                parameterList[6] = new ProgramParameter(textData.toBytes(inBranchCode));

                textData = new AS400Text(1, as400);
                parameterList[7] = new ProgramParameter(textData.toBytes(inIsMarketting));

                textData = new AS400Text(1, as400);
                parameterList[8] = new ProgramParameter(textData.toBytes(isWindow));

                textData = new AS400Text(10, as400);
                parameterList[9] = new ProgramParameter(textData.toBytes(inMobileNumber));

                textData = new AS400Text(1, as400);
                parameterList[10] = new ProgramParameter(textData.toBytes(outStatus));
                parameterList[10].setOutputDataLength(1);

                textData = new AS400Text(3, as400);
                parameterList[11] = new ProgramParameter(textData.toBytes(outMarkettingCode));
                parameterList[11].setOutputDataLength(3);

                program.setProgram(programName1, parameterList1);

                if (program.run() != true) {

                    AS400Message[] messageList = program.getMessageList();
                    for (int cc = 0; cc < messageList.length; ++cc) {

                        messageList[cc].load();

                    }
                } else {

                    program2.setProgram(programName, parameterList);

                    if (program2.run() != true) {

                        AS400Message[] messageList = program.getMessageList();
                        for (int cc = 0; cc < messageList.length; ++cc) {

                            messageList[cc].load();

                        }
                    } else {

                        textData = new AS400Text(1, as400);
                        String out4 = (String) textData.toObject(parameterList[10].getOutputData());

                        if (out4.equals("S")) {

                            textData = new AS400Text(3, as400);
                            String Marcode = (String) textData.toObject(parameterList[11].getOutputData());

                        }
                    }
                }
                rtn = "1";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (rqstType.equals("User Inactivate")) {

            try {

                AS400 as400 = AS400Connection.getAS400();
                String AS400Environment = env;
                String isWindow = "";

                String inEnvName = AS400Environment;
                String inMode = "D";
                String sigUerName = epf;
                //String inGroupProfile = "BO"; // UacAccessAsingde.getAccestype();
                String inGroupProfile = "";
                String inPassword = "";
                String inFullName = fullName;
                String inBranchCode = "";
                String inIsMarketting = "";
                String inMobileNumber = "";
                String outStatus = " ";
                String outMarkettingCode = " ";

//                System.out.println("inEnvName-" + inEnvName);
//                System.out.println("inMode-" + inMode);
//                System.out.println("inUserName-" + sigUerName);
//                System.out.println("inGroupProfile-" + inGroupProfile);
//                System.out.println("inPassword-" + inPassword);
//                System.out.println("inFullName-" + inFullName);
//                System.out.println("inBranchCode-" + inBranchCode);
//                System.out.println("inIsMarketting-" + inIsMarketting);
//                System.out.println("inMobileNumber-" + inMobileNumber);
//                System.out.println("outStatus-" + inMobileNumber);
//                System.out.println("outMarkettingCode-" + outMarkettingCode);
                String programName1 = "/QSYS.LIB/MBCLPGM.LIB/MTF0010R.PGM";

                String programName = "/QSYS.LIB/MBCLPGM.LIB/MUT0530R.PGM";

                ProgramCall program = new ProgramCall(as400);
                ProgramCall program2 = new ProgramCall(as400);

                ProgramParameter[] parameterList1 = new ProgramParameter[1];

                AS400Text textData = new AS400Text(10, as400);
                parameterList1[0] = new ProgramParameter(textData.toBytes(AS400Environment));

                ProgramParameter[] parameterList = new ProgramParameter[12];

                textData = new AS400Text(10, as400);
                parameterList[0] = new ProgramParameter(textData.toBytes(inEnvName));

                textData = new AS400Text(1, as400);
                parameterList[1] = new ProgramParameter(textData.toBytes(inMode));

                textData = new AS400Text(10, as400);
                parameterList[2] = new ProgramParameter(textData.toBytes(sigUerName));

                textData = new AS400Text(10, as400);
                parameterList[3] = new ProgramParameter(textData.toBytes(inGroupProfile));

                textData = new AS400Text(10, as400);
                parameterList[4] = new ProgramParameter(textData.toBytes(inPassword));

                textData = new AS400Text(50, as400);
                parameterList[5] = new ProgramParameter(textData.toBytes(inFullName));

                textData = new AS400Text(5, as400);
                parameterList[6] = new ProgramParameter(textData.toBytes(inBranchCode));

                textData = new AS400Text(1, as400);
                parameterList[7] = new ProgramParameter(textData.toBytes(inIsMarketting));

                textData = new AS400Text(1, as400);
                parameterList[8] = new ProgramParameter(textData.toBytes(isWindow));

                textData = new AS400Text(10, as400);
                parameterList[9] = new ProgramParameter(textData.toBytes(inMobileNumber));

                textData = new AS400Text(1, as400);
                parameterList[10] = new ProgramParameter(textData.toBytes(outStatus));
                parameterList[10].setOutputDataLength(1);

                textData = new AS400Text(3, as400);
                parameterList[11] = new ProgramParameter(textData.toBytes(outMarkettingCode));
                parameterList[11].setOutputDataLength(3);

                program.setProgram(programName1, parameterList1);

                if (program.run() != true) {
                    // Report failure.
                    //System.out.println("Program1 failed!");
                    // Show the messages.
                    AS400Message[] messageList = program.getMessageList();
                    for (int cc = 0; cc < messageList.length; ++cc) {
                        // Show each message.
                        //      System.out.println(messageList[cc].getText());
                        // Load additional message information.
                        messageList[cc].load();
                        //Show help text.
                        //      System.out.println(messageList[cc].getHelp());
                    }
                } // Else no error, get output data.
                else {
//                    System.out.println("Doneeeeeeee Program1" + programName);
//                    System.out.println("--- " + parameterList);
                    program2.setProgram(programName, parameterList);
//                                    textData = new AS400Text(1, as400);
//                               String    message = (String) textData.toObject(parameterList1[1].getOutputData());

                    if (program2.run() != true) {
                        // Report failure.
//                        System.out.println("Program2 failed!");
                        // Show the messages.
                        AS400Message[] messageList = program.getMessageList();
                        for (int cc = 0; cc < messageList.length; ++cc) {
                            // Show each message.
//                            System.out.println(messageList[cc].getText());
                            // Load additional message information.
                            messageList[cc].load();
                            //Show help text.
//                            System.out.println(messageList[cc].getHelp());
                        }
                    } // Else no error, get output data.
                    else {// System.out.println("Doneeeeeeee Program2");
//                        System.out.println("Program 2 success!");
                        textData = new AS400Text(1, as400);
                        String out4 = (String) textData.toObject(parameterList[10].getOutputData());
                    }
                }
                rtn = "1";
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            try {
                AS400 as400 = AS400Connection.getAS400();
                String AS400Environment = env;
                String isWindow = "";

                String inEnvName = AS400Environment;
                String inMode = "C";
                String sigUerName = epf;
                //String inGroupProfile = "BO"; // UacAccessAsingde.getAccestype();
                String inGroupProfile = programeSigPorf;
                String inPassword = "";
                String inFullName = fullName;
                String inBranchCode = brCode;
                String inIsMarketting = "";
                String inMobileNumber = "";
                String outStatus = " ";
                String outMarkettingCode = " ";

                String programName1 = "/QSYS.LIB/MBCLPGM.LIB/MTF0010R.PGM";

                String programName = "/QSYS.LIB/MBCLPGM.LIB/MUT0530R.PGM";

                ProgramCall program = new ProgramCall(as400);
                ProgramCall program2 = new ProgramCall(as400);

                ProgramParameter[] parameterList1 = new ProgramParameter[1];

                AS400Text textData = new AS400Text(10, as400);
                parameterList1[0] = new ProgramParameter(textData.toBytes(AS400Environment));

                ProgramParameter[] parameterList = new ProgramParameter[12];

                textData = new AS400Text(10, as400);
                parameterList[0] = new ProgramParameter(textData.toBytes(inEnvName));

                textData = new AS400Text(1, as400);
                parameterList[1] = new ProgramParameter(textData.toBytes(inMode));

                textData = new AS400Text(10, as400);
                parameterList[2] = new ProgramParameter(textData.toBytes(sigUerName));

                textData = new AS400Text(10, as400);
                parameterList[3] = new ProgramParameter(textData.toBytes(inGroupProfile));

                textData = new AS400Text(10, as400);
                parameterList[4] = new ProgramParameter(textData.toBytes(inPassword));

                textData = new AS400Text(50, as400);
                parameterList[5] = new ProgramParameter(textData.toBytes(inFullName));

                textData = new AS400Text(5, as400);
                parameterList[6] = new ProgramParameter(textData.toBytes(inBranchCode));

                textData = new AS400Text(1, as400);
                parameterList[7] = new ProgramParameter(textData.toBytes(inIsMarketting));

                textData = new AS400Text(1, as400);
                parameterList[8] = new ProgramParameter(textData.toBytes(isWindow));

                textData = new AS400Text(10, as400);
                parameterList[9] = new ProgramParameter(textData.toBytes(inMobileNumber));

                textData = new AS400Text(1, as400);
                parameterList[10] = new ProgramParameter(textData.toBytes(outStatus));
                parameterList[10].setOutputDataLength(1);

                textData = new AS400Text(3, as400);
                parameterList[11] = new ProgramParameter(textData.toBytes(outMarkettingCode));
                parameterList[11].setOutputDataLength(3);

                program.setProgram(programName1, parameterList1);

                if (program.run() != true) {

                    AS400Message[] messageList = program.getMessageList();
                    for (int cc = 0; cc < messageList.length; ++cc) {

                        messageList[cc].load();

                    }
                } else {

                    program2.setProgram(programName, parameterList);

                    if (program2.run() != true) {

                        AS400Message[] messageList = program.getMessageList();
                        for (int cc = 0; cc < messageList.length; ++cc) {

                            messageList[cc].load();

                        }
                    } else {

                        textData = new AS400Text(1, as400);
                        String out4 = (String) textData.toObject(parameterList[10].getOutputData());

                        if (out4.equals("S")) {

                            textData = new AS400Text(3, as400);
                            String Marcode = (String) textData.toObject(parameterList[11].getOutputData());

                        }
                    }
                }
                rtn = "1";
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return rtn;
    }

}
