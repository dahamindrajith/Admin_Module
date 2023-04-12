/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import java.util.Hashtable;
import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author MBSL2523
 */
public class ADfilter extends HttpServlet {

    private Hashtable hashtable = null;

    public void setLdapTable(Hashtable ldpHashtable) {
        this.hashtable = ldpHashtable;
    }

    public String getuserAttr(String username, String attr) throws NamingException {
        String atr = null;
        try {
            AD ad = new AD();
            DirContext authContext = new InitialDirContext(this.hashtable);

//            atr = getMultiSearchResult(attr, authContext,
//                    "(&(objectCategory=person)(objectClass=user)(SAMAccountName=" + username + "))",
//                    "OU=Users,OU=Merchant Bank,DC=mbslbank,DC=com");
            atr = getMultiSearchResult(attr, authContext,
                    "(&(objectCategory=person)(objectClass=user)(SAMAccountName=" + username + "))",
                    "OU=Users,OU=Merchant Bank,DC=TESTAD,DC=LOCAL");

        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }
        return atr;
    }

    public static String getUserBasicAttributes(String username, LdapContext ctx) {
        String user = null;
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = {"distinguishedName",
                "sn",
                "givenname",
                "mail",
                "telephonenumber"};
            constraints.setReturningAttributes(attrIDs);
            NamingEnumeration answer = ctx.search("OU=Users,OU=Merchant Bank,DC=mbslbank,DC=com", "sAMAccountName="
                    + username, constraints);
            if (answer.hasMore()) {
                Attributes attrs = ((SearchResult) answer.next()).getAttributes();
                user = getFullName(attrs.get("distinguishedName").toString());

            } else {
                throw new Exception("Invalid User");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    private static String getFullName(String data) {
        String CNFilter = null;
        try {
            String fullvalue = data.split(",")[0];
            CNFilter = fullvalue.split("=")[1];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return CNFilter;
    }

    //List of AD users ;-D
    private static final String getMultiSearchResult(final String attri, final DirContext dirContext,
            final String searchFilter,
            final String searchBase) throws NamingException {
        String attr = null;

        final SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        final NamingEnumeration<?> searchResults = dirContext.search(searchBase, searchFilter, constraints);
        while (searchResults != null && searchResults.hasMoreElements()) {
            final SearchResult searchResult = (SearchResult) searchResults.next();
            attr = displayAttribute(attri, searchResult.getAttributes());
        }

        return attr;
    }

    /**
     * This method used to display the Attribute Values
     *
     * @param attributes
     * @throws NamingException
     */
    @SuppressWarnings("unchecked")
    public static String displayAttribute(String attrName, final Attributes attributes) throws NamingException {
        String at = null;
        if (attributes == null) {
            //"*** No attributes ***"
        } else {
            for (NamingEnumeration enums = attributes.getAll(); enums.hasMore();) {
                final Attribute attribute = (Attribute) enums.next();
                if (attribute.getID().equals(attrName)) {
                    for (NamingEnumeration namingEnu = attribute.getAll(); namingEnu.hasMore();) //System.out.println("\t        = " + namingEnu.next());
                    {
                        at = namingEnu.next().toString();
                    }

                    break;
                }
            }

        }
        return at;
    }
}
