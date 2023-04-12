/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import java.util.Hashtable;

import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 *
 * @author MBSL2523
 */
public class AdCreation {

    private static String secAuthentication, contextFactory;
    private static String DOMAIN_NAME, DOMAIN_ROOT, DOMAIN_URL, DOMAIN_PORT, ADMIN_NAME, ADMIN_PASS;

//    private static final String DOMAIN_NAME = "mbslbank.com";
//    //private static final String DOMAIN_ROOT = "OU=HR,OU=HO,OU=Users,OU=Merchant Bank,DC=TESTAD,DC=LOCAL";
//    private static final String DOMAIN_ROOT = "DC=TESTAD,DC=LOCAL";
//    private static final String DOMAIN_URL = "ldap://10.1.1.3:389";
//    private static final String ADMIN_NAME = "CN=Administrator,CN=Users,DC=mbslbank,DC=com";
//    private static final String ADMIN_PASS = "Mbsl@12345";
    private String userName, firstName, lastName, password, mail, organisationUnit, title;
    private LdapContext context;

    private Hashtable hashtable;

    public AdCreation(String userName, String organisationUnit) {
        this.userName = userName;
        this.organisationUnit = organisationUnit;
    }

    public AdCreation(String userName, String firstName, String lastName, String designation,
            String password, String mail, String organisationUnit) {

        String str = firstName;
        String[] splitStr = str.split(" ");

        System.out.println("split--------" + splitStr[1]);

        this.userName = userName;
        this.firstName = splitStr[0];
        this.lastName = splitStr[1];
        this.password = password;
        this.mail = mail;
        this.organisationUnit = organisationUnit;
        this.title = designation;

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, getContextFactory());
        env.put(Context.SECURITY_AUTHENTICATION, getSecurityAuthentication());
        env.put(Context.SECURITY_PRINCIPAL, getAdminUser() + "@" + getDomain());
        env.put(Context.SECURITY_CREDENTIALS, getAdminPassword());
        env.put(Context.PROVIDER_URL, "ldap://" + getHostAddress() + ":" + getPort());
        //env.put(Context.SECURITY_PROTOCOL, "ssl");

        // connect to my domain controller
        // env.put(Context.PROVIDER_URL, DOMAIN_URL);
        try {
            this.context = new InitialLdapContext(env, null);
            System.out.println("Connection Success " + context);
        } catch (NamingException e) {
            System.err.println("Problem creating object: ");
            e.printStackTrace();
        }
    }

    public boolean addUser() throws NamingException {
        System.out.println("AdCreation");
        Attributes container = new BasicAttributes();

        // Create the objectclass to add
        Attribute objClasses = new BasicAttribute("objectClass");
        objClasses.add("organizationalPerson");
        objClasses.add("person");
        objClasses.add("top");
        objClasses.add("user");

        //OU=Information Technology,OU=HO,OU=Users,OU=Merchant Bank,DC=mbslbank,DC=com
        // Assign the username, first name, and last name  
        //Attribute obj = new BasicAttribute("objectClass", "user");
        String cnValue = new StringBuffer(firstName).append(" ").append(lastName).toString();
        Attribute cn = new BasicAttribute("cn", cnValue);
        Attribute sAMAccountName = new BasicAttribute("sAMAccountName", userName);
        Attribute principalName = new BasicAttribute("userPrincipalName", userName + "@TESTAD.LOCAL");
        Attribute givenName = new BasicAttribute("givenName", firstName);
        Attribute sn = new BasicAttribute("sn", lastName);
        Attribute uid = new BasicAttribute("uid", userName);
        Attribute displayName = new BasicAttribute("displayName", cnValue);

        //User Account Options lmaccess.h
        int UF_ACCOUNTDISABLE = 0;
        int UF_PASSWD_NOTREQD = 0x0020;
        int UF_PASSWD_CANT_CHANGE = 0x0040;
        int UF_NORMAL_ACCOUNT = 0x0200;
        int UF_DONT_EXPIRE_PASSWD = 0x10000;
        int UF_PASSWORD_EXPIRED = 0x800000;
        int UF_TRUSTED_TO_AUTHENTICATE_FOR_DELEGATION = 0x1000000;

        Attribute accountOption = new BasicAttribute("userAccountControl", Integer.toString(UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD + UF_PASSWORD_EXPIRED + UF_TRUSTED_TO_AUTHENTICATE_FOR_DELEGATION));

        // Add password
        ModificationItem[] mods = new ModificationItem[2];
        // Add password
        //  "\"Password2000\"";
        String NewP = "\"" + password + "\"";
        byte[] newUnicodePassword = null;
        try {
            System.out.println("new p : " + NewP);
            newUnicodePassword = NewP.getBytes("UTF-16LE");
            System.out.println("newUnicodePassword " + newUnicodePassword);
        } catch (Exception e) {
            System.out.println("Inside unicode");
            e.printStackTrace();
        }
//        String quotedPassword = "\"" + password + "\"";
//        char unicodePwd[] = quotedPassword.toCharArray();
//        byte pwdArray[] = new byte[unicodePwd.length * 2];
//        for (int i = 0; i < unicodePwd.length; i++) {
//            pwdArray[i * 2 + 1] = (byte) (unicodePwd[i] >>> 8);
//            pwdArray[i * 2 + 0] = (byte) (unicodePwd[i] & 0xff);
//        }

        //Attribute userPassword = new BasicAttribute("unicodePwd", newUnicodePassword);
        Attribute userPassword = new BasicAttribute("userPassword", password);
        Attribute email = new BasicAttribute("mail", mail);
        Attribute designation = new BasicAttribute("title", title);

        Attribute userAccount = new BasicAttribute("userAccountControl", Integer.toString(UF_NORMAL_ACCOUNT
                + UF_PASSWD_NOTREQD + UF_PASSWORD_EXPIRED + UF_ACCOUNTDISABLE));

        // Add these to the container
        container.put(objClasses);
        container.put(sAMAccountName);
        container.put(principalName);
        container.put(cn);
        container.put(sn);
        container.put(givenName);
        container.put(uid);
        container.put(accountOption);
        container.put(userPassword);
        container.put(displayName);
        container.put(email);
        container.put(designation);
        container.put(userAccount);

        // Create the entry
        try {
            //2022-12-05 Commented---------------- 
            System.out.println("cnValue---" + cnValue + "    " + "organisationUnit" + organisationUnit);
            if (container.equals(null) || container == null) {
                System.out.println("Container null********************");
            } else {
//                System.out.println("-----Testing-- "+ getUserDN(cnValue, organisationUnit)+" -- "+ container);
                context.createSubcontext(getUserDN(cnValue, organisationUnit), container);
            }

            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute("unicodePwd", newUnicodePassword));
            mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute("userAccountControl", Integer
                            .toString(UF_NORMAL_ACCOUNT + UF_PASSWORD_EXPIRED)));

            context.modifyAttributes(getUserDN(cnValue, organisationUnit), mods);

            //2022-12-05 Commented---------------- 
//            Hashtable<String, String> env = new Hashtable<String, String>();
//
//            env.put(Context.INITIAL_CONTEXT_FACTORY, getContextFactory());
//            env.put(Context.SECURITY_AUTHENTICATION, getSecurityAuthentication());
//            env.put(Context.SECURITY_PRINCIPAL, getAdminUser() + "@" + getDomain());
//            env.put(Context.SECURITY_CREDENTIALS, getAdminPassword());
//            env.put(Context.PROVIDER_URL, "ldap://" + getHostAddress() + ":" + getPort());
//
////            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
////            env.put(Context.SECURITY_AUTHENTICATION, "Simple");
////            env.put(Context.SECURITY_PRINCIPAL, "velocity" + "@TESTAD.LOCAL");
////            env.put(Context.SECURITY_CREDENTIALS, "Mbsl1982@it");
////            env.put(Context.PROVIDER_URL, "ldap://10.0.8.1:389");
//            hashtable = env;z
//
//            String newQuotedPassword = "\"1qaz!QAZ\"";
//            byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
//
//            StartTlsResponse tls = (StartTlsResponse) context
//                    .extendedOperation(new StartTlsRequest());
//            tls.negotiate();
//
//            ModificationItem[] mods = new ModificationItem[2];
//            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
//                    new BasicAttribute("unicodePwd", newUnicodePassword));
//            mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
//                    new BasicAttribute("userAccountControl",
//                            Integer.toString(UF_NORMAL_ACCOUNT
//                                    + UF_PASSWORD_EXPIRED)));
//
//            //hashtable = env;
//            //ADfilter adf = new ADfilter();
//            //adf.setLdapTable(this.hashtable);
//            //String cn2 = adf.getuserAttr(userName, "cn");
//            //context.modifyAttributes(getUserDN(cn2, organisationUnit), mods);
//            context.modifyAttributes(getUserDN(cnValue, organisationUnit), mods);
//            System.out.println("Set password & updated userccountControl");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean disableAccount() {
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();

            env.put(Context.INITIAL_CONTEXT_FACTORY, getContextFactory());
            env.put(Context.SECURITY_AUTHENTICATION, getSecurityAuthentication());
            env.put(Context.SECURITY_PRINCIPAL, getAdminUser() + "@" + getDomain());
            env.put(Context.SECURITY_CREDENTIALS, getAdminPassword());
            env.put(Context.PROVIDER_URL, "ldap://" + getHostAddress() + ":" + getPort());

//            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//            env.put(Context.SECURITY_AUTHENTICATION, "Simple");
//            env.put(Context.SECURITY_PRINCIPAL, "velocity" + "@TESTAD.LOCAL");
//            env.put(Context.SECURITY_CREDENTIALS, "Mbsl1982@it");
//            env.put(Context.PROVIDER_URL, "ldap://10.0.8.1:389");
            hashtable = env;
            try {
                this.context = new InitialLdapContext(env, null);
            } catch (NamingException e) {
                System.err.println("Problem creating object: ");
                e.printStackTrace();
            }

            ADfilter adf = new ADfilter();
            adf.setLdapTable(this.hashtable);

            String cn = adf.getuserAttr(userName, "cn");

            int UF_ACCOUNTDISABLE = 0x0002;
            ModificationItem[] mods = new ModificationItem[1];
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl", Integer.toString(UF_ACCOUNTDISABLE)));

            context.modifyAttributes(getUserDN(cn, organisationUnit), mods);
            System.out.println("Set disabled successfully");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean moveUser(String newOrganisation) {
        try {

            Hashtable<String, String> env = new Hashtable<String, String>();

            env.put(Context.INITIAL_CONTEXT_FACTORY, getContextFactory());
            env.put(Context.SECURITY_AUTHENTICATION, getSecurityAuthentication());
            env.put(Context.SECURITY_PRINCIPAL, getAdminUser() + "@" + getDomain());
            env.put(Context.SECURITY_CREDENTIALS, getAdminPassword());
            env.put(Context.PROVIDER_URL, "ldap://" + getHostAddress() + ":" + getPort());

            hashtable = env;
            try {
                this.context = new InitialLdapContext(env, null);
            } catch (NamingException e) {
                System.err.println("Problem creating object: ");
                e.printStackTrace();
            }

            ADfilter adf = new ADfilter();
            adf.setLdapTable(this.hashtable);

            String cn = adf.getuserAttr(userName, "cn");

            context.rename(getUserDN(cn, organisationUnit), getUserDN(cn, newOrganisation));
//            System.out.println("successfully moved!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        try {

            Hashtable<String, String> env = new Hashtable<String, String>();

            env.put(Context.INITIAL_CONTEXT_FACTORY, getContextFactory());
            env.put(Context.SECURITY_AUTHENTICATION, getSecurityAuthentication());
            env.put(Context.SECURITY_PRINCIPAL, getAdminUser() + "@" + getDomain());
            env.put(Context.SECURITY_CREDENTIALS, getAdminPassword());
            env.put(Context.PROVIDER_URL, "ldap://" + getHostAddress() + ":" + getPort());

            hashtable = env;
            try {
                this.context = new InitialLdapContext(env, null);
            } catch (NamingException e) {
//                System.err.println("Problem creating object: ");
                e.printStackTrace();
            }

            ADfilter adf = new ADfilter();
            adf.setLdapTable(this.hashtable);
//            System.out.println("userName-" + userName);
            String cn = adf.getuserAttr(userName, "cn");

            ModificationItem[] mods = new ModificationItem[1];

            String OldP = "\"" + oldPassword + "\"";
            byte[] oldUnicodePassword = OldP.getBytes("UTF-16LE");

            //  "\"Password2000\"";
            String NewP = "\"" + newPassword + "\"";
            byte[] newUnicodePassword = NewP.getBytes("UTF-16LE");

            //    mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute("unicodePwd", oldUnicodePassword));
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", newUnicodePassword));

            context.modifyAttributes(getUserDN(cn, organisationUnit), mods);
//            System.out.println("Set password successfully");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getUserDN(String aUsername, String aOU) {
        //return "cn=" + aUsername + ",ou" + aOU + "," + DOMAIN_ROOT;
        //return "cn=" + aUsername + "," + aOU + "," + DOMAIN_ROOT;   
        //return "cn=" + aUsername + "," + DOMAIN_ROOT;
        System.out.println("Return Value - " + "cn=" + aUsername + "," + aOU + ", root=" + getroot());
        return "cn=" + aUsername + "," + aOU + "," + getroot();
    }

    private static String getDomain() {
        DOMAIN_NAME = getValue("AD_DOMAIN");
        return DOMAIN_NAME;
    }

    private static String getHostAddress() {
        DOMAIN_URL = getValue("AD_HOST");
        return DOMAIN_URL;
    }

    private static String getPort() {
        DOMAIN_PORT = getValue("AD_PORT");
        return DOMAIN_PORT;
    }

    private static String getroot() {
        DOMAIN_ROOT = getValue("AD_ROOT");
        return DOMAIN_ROOT;
    }

    private static String getAdminUser() {
        ADMIN_NAME = getValue("AD_ADMIN");
        return ADMIN_NAME;
    }

    private static String getAdminPassword() {
        ADMIN_PASS = getValue("AD_ADMINPASSWORD");
        return ADMIN_PASS;
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
//            System.out.println(option + " : " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
