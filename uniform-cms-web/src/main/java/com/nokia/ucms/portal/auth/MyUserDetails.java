package com.nokia.ucms.portal.auth;

import com.nokia.ucms.biz.entity.User;
import com.nokia.ucms.biz.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails
{
    private User user;

    public MyUserDetails (User user)
    {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities ()
    {
        if (user != null)
        {
            List<UserRole> roles = user.getRoles();
            if (roles == null || roles.size() < 1)
            {
                return AuthorityUtils.commaSeparatedStringToAuthorityList("");
            }

            StringBuilder commaBuilder = new StringBuilder();
            for (UserRole role : roles)
            {
                commaBuilder.append(role.getRole()).append(",");
            }

            String authorities = commaBuilder.substring(0, commaBuilder.length() - 1);
            return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
        }

        return AuthorityUtils.commaSeparatedStringToAuthorityList("");
    }

    @Override
    public String getPassword ()
    {
        return user.getUserPassword();
    }

    @Override
    public String getUsername ()
    {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired ()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked ()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired ()
    {
        return true;
    }

    @Override
    public boolean isEnabled ()
    {
        return true;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

}