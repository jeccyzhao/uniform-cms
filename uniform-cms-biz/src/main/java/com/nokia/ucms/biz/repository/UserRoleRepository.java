package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.entity.UserRole;
import org.apache.ibatis.annotations.Param;

/**
 * Created by x36zhao on 2017/3/17.
 */
public interface UserRoleRepository
{
    Integer insertUserRole(final UserRole userRole);
    Integer updateUserRole(final UserRole userRole);
    UserRole getUserRoleByUserId(@Param("userId") Integer userId);
    UserRole getUserRoleById(@Param("id") Integer id);
    Integer deleteUserRoleById(@Param("id") Integer id);
    Integer deleteUserRoleByUserId(@Param("userId") Integer userId);
}
