package com.nokia.ucms.common.auth;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LdapAuthentication
{
    public LdapUserEntity authenticate (String userName, String password,
                                        String ldapServer, String searchBase) throws NamingException
    {
        boolean isDebug = false;
        if (!isDebug)
        {
            return new LdapUserEntity();
        }

        DirContext ctx = null;
        LdapUserEntity userEntry = null;

        String principal = "uid=" + userName;
        String ldapURL = ldapServer + "/" + searchBase;

        ctx = new InitialDirContext(buildEnv(principal, password, ldapURL));
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration<SearchResult> en = ctx.search("", principal, constraints);
        while (en != null && en.hasMoreElements())
        {
            Object obj = en.nextElement();
            if (obj instanceof SearchResult)
            {
                SearchResult si = (SearchResult) obj;
                Attributes attrs = si.getAttributes();
                if (attrs != null)
                {
                    userEntry = new LdapUserEntity();
                    userEntry.setDisplayName(attrs.get("displayName").get().toString());
                    userEntry.setUid(attrs.get("uid").get().toString());
                    userEntry.setUidNumber(attrs.get("uidNumber").get().toString());
                    userEntry.setMail(attrs.get("mail").get().toString());
                }
            }
        }

        return userEntry;
    }

    private Hashtable<String, String> buildEnv (String principal, String password, String ldapURL)
    {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_PRINCIPAL, principal);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.PROVIDER_URL, ldapURL);

        return env;
    }

    public static void main (String[] args) throws NamingException
    {
    }
}