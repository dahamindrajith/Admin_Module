/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 *
 * @author MBSL2523
 */
public class AD {

    private static String domain, host, port, secAuthentication, contextFactory;

    private String username;
    private String pword;
    private Hashtable Hashtable;

    public boolean isLDAPConnected(String uname, String pass) {
        LdapContext ctx = null;
        boolean ad = false;
        try {
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, getContextFactory());
            env.put(Context.SECURITY_AUTHENTICATION, getSecurityAuthentication());
            env.put(Context.SECURITY_PRINCIPAL, uname + "@" + getDomain());
            env.put(Context.SECURITY_CREDENTIALS, pass);
            env.put(Context.PROVIDER_URL, "ldap://" + getHostAddress() + ":" + getPort());
            ctx = new InitialLdapContext(env, null);

            //Connection Successful
            ad = true;
            Hashtable = env;
            this.pword = pass;
            this.username = uname;
        } catch (NamingException nex) {
            ad = false;
            //LDAP Connection: FAILED
            nex.printStackTrace();
            System.out.println("MEssage : " + nex.getMessage());
        }
        return ad;
    }

    public Hashtable getLdapHashTable() {
        Hashtable env = null;
        try {
            env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, getContextFactory());
            env.put(Context.SECURITY_AUTHENTICATION, getSecurityAuthentication());
            env.put(Context.SECURITY_PRINCIPAL, this.username + "@" + getDomain());
            env.put(Context.SECURITY_CREDENTIALS, this.pword);
            env.put(Context.PROVIDER_URL, "ldap://" + getHostAddress() + ":" + getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return env;
    }

    public LdapContext getLDAPC() {
        LdapContext ctx = null;

        try {
            ctx = new InitialLdapContext(getLdapHashTable(), null);
            //Connection Successful
        } catch (NamingException nex) {
            //LDAP Connection: FAILED
            nex.printStackTrace();
        }
        return ctx;
    }

    private static String getDomain() {
        domain = getValue("AD_DOMAIN");
        return domain;
    }

    private static String getHostAddress() {
        host = getValue("AD_HOST");
        return host;
    }

    private static String getPort() {
        port = getValue("AD_PORT");
        return port;
    }

    private static String getContextFactory() {
        contextFactory = getValue("AD_INITIAL_CONTEXT_FACTORY");
        return contextFactory;
    }

    private static String getSecurityAuthentication() {
        secAuthentication = getValue("AD_SECURITY_AUTHENTICATION");
        return secAuthentication;
    }

    private static String getValue(String option) {
        String value = null;
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("C:\\Velocity\\test\\ad.properties"));
            value = prop.getProperty(option);
            System.out.println(option + " : " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
