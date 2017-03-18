package com.nokia.ucms.biz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nokia.ucms.common.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

/**
 * Created by x36zhao on 2017/3/5.
 */
@Data
@ToString(exclude = "id")
@JsonIgnoreProperties(value= { "id", "userId" } )
public class UserRole extends BaseEntity
{
    private int userId;
    private String role;

    public UserRole()
    {

    }

    public UserRole (int userId, String role)
    {
        this.userId = userId;
        this.role = role;
    }
}
