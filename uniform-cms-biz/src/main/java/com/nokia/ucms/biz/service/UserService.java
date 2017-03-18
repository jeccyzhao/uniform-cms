package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.entity.User;
import com.nokia.ucms.biz.entity.UserRole;
import com.nokia.ucms.biz.repository.UserRepository;
import com.nokia.ucms.biz.repository.UserRoleRepository;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class UserService extends BaseService
{
    private static Logger LOGGER = Logger.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public User findById (Integer userId)
    {
        if (userId != null && userId > 0)
        {
            User user = this.userRepository.getUserById(userId);
            if (user != null)
            {
                UserRole userRole = this.userRoleRepository.getUserRoleByUserId(user.getId());
                if (userRole != null)
                {
                    user.setRole(userRole.getRole());
                    user.addRole(userRole);
                }
                return user;
            }

            throw new ServiceException(String.format("User (id: %d) does not exist", userId));
        }

        throw new ServiceException("Invalid user id: " + userId);
    }

    public List<User> getAllUsers ()
    {
        List<User> users = this.userRepository.getAllUsers();
        if (users != null)
        {
            for (User user : users)
            {
                UserRole userRole = this.userRoleRepository.getUserRoleByUserId(user.getId());
                if (userRole != null)
                {
                    user.setRole(userRole.getRole());
                    user.addRole(userRole);
                }
            }
        }

        return users;
    }

    public User getUserByName (String userName)
    {
        if (userName != null && !"".equals(userName))
        {
            User user = this.userRepository.getUserByName(userName);
            if (user != null)
            {
                UserRole userRole = this.userRoleRepository.getUserRoleByUserId(user.getId());
                if (userRole != null)
                {
                    user.setRole(userRole.getRole());
                    user.addRole(userRole);
                }
            }

            return user;
        }

        throw new ServiceException("Invalid user name: " + userName);
    }

    public User saveOrUpdate (User user, String role)
    {
        return user.getId() != null ? updateUser(user, role) : addUser(user, role);
    }

    private User addUser (User user, String role)
    {
        if (user != null)
        {
            user.setLastLoginTime(new Date());
            Integer result = this.userRepository.insertUser(user);
            if (result > 0)
            {
                UserRole userRole = new UserRole(user.getId(), role);
                userRoleRepository.insertUserRole(userRole);

                return user;
            }

            throw new ServiceException("Failed to add user: " + user);
        }

        throw new ServiceException("Invalid user to be added: " + user);
    }

    private User updateUser (User user, String role)
    {
        if (user != null && user.getId() != null)
        {
            user.setLastLoginTime(new Date());
            Integer result = this.userRepository.updateUser(user);
            if (result > 0)
            {
                UserRole userRole = userRoleRepository.getUserRoleByUserId(user.getId());
                if (userRole == null)
                {
                    userRole = new UserRole(user.getId(), role);
                }
                else
                {
                    userRole.setRole(role);
                }

                userRoleRepository.updateUserRole(userRole);
                return user;
            }

            throw new ServiceException("Failed to update user: " + user);
        }

        throw new ServiceException("Invalid user to be updated: " + user);
    }

    protected String getServiceCategory ()
    {
        return null;
    }

    protected String getServiceDomain ()
    {
        return null;
    }
}
