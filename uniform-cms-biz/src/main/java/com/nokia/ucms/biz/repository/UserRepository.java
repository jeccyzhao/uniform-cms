package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/17.
 */
public interface UserRepository
{
    Integer insertUser (final User user);
    Integer updateUser (final User user);
    User getUserByName(@Param("userName") String userName);
    User getUserById (@Param("id") Integer userId);
    Integer deleteUserById (@Param("id") Integer userId);
    Integer deleteUserByName (@Param("userName") String userName);
    List<User> getAllUsers();
}
