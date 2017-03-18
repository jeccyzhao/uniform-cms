package com.nokia.ucms.portal.auth;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * Created by x36zhao on 2017/3/19.
 */
public class RefererRedirectionAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    public RefererRedirectionAuthenticationSuccessHandler()
    {
        super();
        //setUseReferer(true);
    }
}
