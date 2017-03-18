package com.nokia.ucms.portal.auth;

import com.nokia.ucms.biz.entity.SystemConfig;
import com.nokia.ucms.biz.entity.User;
import com.nokia.ucms.biz.service.SystemConfigService;
import com.nokia.ucms.biz.service.UserService;
import com.nokia.ucms.common.auth.LdapAuthentication;
import com.nokia.ucms.common.auth.LdapUserEntity;
import com.nokia.ucms.common.exception.LdapAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.util.Date;

import static com.nokia.ucms.biz.constants.Constants.*;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate (Authentication authentication)
            throws AuthenticationException
    {
        String userName = authentication.getName();
        String password = (String) authentication.getCredentials();

        SystemConfig ldapServer = systemConfigService.findByPropertyName(PROP_LDAP_SERVER);
        SystemConfig ldapSearchBase = systemConfigService.findByPropertyName(PROP_LDAP_BASE);

        if (ldapServer != null && ldapSearchBase != null)
        {
            LdapAuthentication ldapAuth = new LdapAuthentication();
            try
            {
                LdapUserEntity ldapUserEntry = ldapAuth.authenticate(userName, password,
                        ldapServer.getPropertyValue(), ldapSearchBase.getPropertyValue());

                if (ldapUserEntry != null)
                {
                    User user = userService.getUserByName(userName);
                    if (user == null)
                    {
                        // adding authorized user with default role in case of not existing in system
                        user = new User();
                        user.setUserDisplayName(ldapUserEntry.getDisplayName());
                        user.setUserIdNumber(ldapUserEntry.getUidNumber());
                        user.setUserMail(ldapUserEntry.getMail());
                        user.setUserName(ldapUserEntry.getUid());
                    }

                    user.setLastLoginTime(new Date());
                    user.setUserPassword(password);

                    String role = ROLE_DEFAULT;
                    if (user.getId() != null && user.getRole() != null)
                    {
                        role = user.getRole();  // user.getRoles().get(0).getRole();
                    }

                    userService.saveOrUpdate(user, role);

                    MyUserDetails userDetails = new MyUserDetails(user);
                    return new UsernamePasswordAuthenticationToken(user, password, userDetails.getAuthorities());
                }
                else
                {
                    throw new UsernameNotFoundException("User not exists in remote LDAP server");
                }
            }
            catch (NamingException ex)
            {
                throw new LdapAuthenticationException("LDAP authentication failed.", ex);
            }
        }
        else
        {
            throw new BadCredentialsException("Invalid credentials!");
        }
    }

    @Override
    public boolean supports (Class<?> arg0)
    {
        return true;
    }

}
