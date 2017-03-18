package com.nokia.ucms.biz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nokia.ucms.common.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/5.
 */
@Data
@ToString(exclude = "id")
@JsonIgnoreProperties(value = { "id" })
public class User extends BaseEntity
{
    private String userName;
    private String userDisplayName;
    private String userIdNumber;
    private String userMail;
    private String userPassword;
    private Date lastLoginTime;

    private String role;
    private List<UserRole> roles;

    public User()
    {
    }

    public User (User user)
    {
        this(user, null);
    }

    public User (User user, UserRole role)
    {
        this.userName = user.getUserName();

        if (role != null)
        {
            this.roles.add(role);
        }
    }

    public void addRole (final UserRole userRole)
    {
        if (userRole != null)
        {
            if (roles == null)
            {
                roles = new ArrayList<UserRole>();
            }

            roles.add(userRole);
        }
    }

}
