package com.nokia.ucms.biz.constants;

/**
 * Created by x36zhao on 2017/3/14.
 */
public enum EUserRole
{
    ROLE_PROJECT_UPDATE ("ROLE_PROJECT");

    private String role;
    EUserRole (String role)
    {
        this.role = role;
    }

    public String getRole()
    {
        return this.role;
    }
}
