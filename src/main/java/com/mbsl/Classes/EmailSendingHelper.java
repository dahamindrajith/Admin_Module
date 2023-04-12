/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author MBSL2523
 */
public class EmailSendingHelper {

    private static EmailSendingHelper instance = new EmailSendingHelper();

    private static String messagefrom = "velocity@mbslbank.com";
    private static String host = "10.1.1.29";//
    private static String result = "";

    //make the constructor private so that this class cannot be
    //instantiated
    private EmailSendingHelper() {
    }

    //Get the only object available
//    public static EmailSendingHelper getInstance() {
//        return instance;
//    }
    public static EmailSendingHelper getInstance() {
        if (instance == null) {
            instance = new EmailSendingHelper();
        }
        return instance;
    }

    public void assignRecieverstotheMail(String[] messageto, String[] bcc, MimeMessage message) {
        try {
            if (messageto != null) {
                InternetAddress[] mailAddress_TO = new InternetAddress[messageto.length];
                for (int i = 0; i < messageto.length; i++) {
                    mailAddress_TO[i] = new InternetAddress(messageto[i]);
                }
                message.addRecipients(Message.RecipientType.TO, mailAddress_TO);
            } else {
            }
            if (bcc != null) {
                InternetAddress[] mailAddress_BCC = new InternetAddress[bcc.length];
                for (int i = 0; i < bcc.length; i++) {
                    mailAddress_BCC[i] = new InternetAddress(bcc[i]);
                }
                message.addRecipients(Message.RecipientType.BCC, mailAddress_BCC);
            } else {
            }
        } catch (Exception ex) {
        }
    }

    public String sendEmail(String messageSubject, String messageBody, String[] messageto, String[] bcc, boolean isHTML) {
        try {
            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            Session mailSession = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(messagefrom));

            assignRecieverstotheMail(messageto, bcc, message);

            message.setSubject(messageSubject);

            //if using HTML body
            if (isHTML) {
                message.setContent(messageBody, "text/html; charset=utf-8");
            } else {
                message.setText(messageBody);
            }

            Transport.send(message);

            result = "success";
        } catch (Exception ex) {

            result = "Error";
        }
        return result;
    }

    public String sendEMailwithAttachment(String messageSubject, String messageBody, String[] messageto, String[] cc, String[] bcc, String fileSourse, String filename) {
        try {
// Create a default MimeMessage object.
            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            Session mailSession = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(messagefrom));
//// Set From: header field of the header.
// message.setFrom(new InternetAddress(from));
//// Set To: header field of the header.
// message.addRecipient(Message.RecipientType.TO,
// new InternetAddress(to));

            assignRecieverstotheMail(messageto, bcc, message);

// Set Subject: header field
            message.setSubject(messageSubject);
// Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
// Fill the message
            messageBodyPart.setText(messageBody);
// Create a multipar message
            Multipart multipart = new MimeMultipart();
// Set text message part
            multipart.addBodyPart(messageBodyPart);
// Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filenamep = "WhyMe.png";
            DataSource source = new FileDataSource(fileSourse);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
// Send the complete message parts
            message.setContent(multipart);
// Send message
            Transport.send(message);
            String title = "Send Email";
            result = "Sent message successfully....";
        } catch (Exception mex) {
            mex.printStackTrace();
            result = "Error: unable to send message....";
        }
        return result;
    }

}
