/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import com.google.gson.Gson;
import com.mbsl.model.SmsDao;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author MBSL2523
 */
public class SendSms {

public String  sendSms(String phoneNumber,  String msg){

StringBuilder sb = new StringBuilder();

 
//Please Enter 071-----------------
        String phoneNumbersub = "0";
        if (!phoneNumber.equals("")) {
            phoneNumbersub = phoneNumber.substring(1, 10);
            System.out.println("phoneNumbersub--------------"+phoneNumbersub);

        }

try {HttpURLConnection urlConn;
            URL url = null;
//            System.out.println("type- "+type);
           
//                url = new URL("http://localhost:8081/Main/smsAPI");
             url = new URL("http://10.1.1.188:8081/Main/smsAPI");
            
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoOutput(true);
            SmsDao sms = new SmsDao();
            sms.setMobileNo(phoneNumbersub);
            sms.setMessage(msg);
            Gson gson = new Gson();
            String json = gson.toJson(sms);
            System.out.println("json - " + json);
            urlConn.setRequestProperty("Content-Type", "application/json; charset=utf8");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.setRequestProperty("Method", "POST");
            if (json != null) {
                urlConn.setRequestProperty("Content-Length", Integer.toString(sms.hashCode()));
                urlConn.getOutputStream().write(json.getBytes("UTF8"));
            }
            System.out.println("Response - "+urlConn);
            System.out.println("Response Code - "+urlConn.getResponseCode());
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }
                in.close();
            }

 

        } catch (Exception e) {
            e.printStackTrace();
        }
return sb.toString();

}
    
}
