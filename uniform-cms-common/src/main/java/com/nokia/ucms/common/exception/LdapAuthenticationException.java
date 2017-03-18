package com.nokia.ucms.common.exception;

import org.springframework.security.core.AuthenticationException;

public class LdapAuthenticationException extends AuthenticationException
{
    public LdapAuthenticationException (String msg)
    {
        super(msg);
    }

    public LdapAuthenticationException (String msg, Throwable t)
    {
        super(msg, t);
    }

}