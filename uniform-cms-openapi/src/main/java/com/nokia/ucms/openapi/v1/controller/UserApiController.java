package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.service.ProjectInfoService;
import com.nokia.ucms.common.entity.ApiQueryResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/15.
 */
@RestController
@RequestMapping("/openapi/v1/users")
public class UserApiController
{
    private static Logger LOGGER = Logger.getLogger(UserApiController.class);

    @Autowired
    private ProjectInfoService projectInfoService;

    @RequestMapping(path="/{userId}", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<List<ProjectInfo>> getUser(
            @PathVariable String userId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getUser - [userId : %s]", userId));

        return null;
    }

    @RequestMapping(path="/{userId}/projects", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<List<ProjectInfo>> getUserProjects(
            @PathVariable String userId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getUserProjects - [userId : %s]", userId));

        // TODO: private projects can only be visible for logon user
        List<ProjectInfo> projectList = projectInfoService.getProjectsByOwner(userId, true);
        return new ApiQueryResult<List<ProjectInfo>>(projectList);
    }

    @RequestMapping(path="/{userId}/records", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<List<ProjectInfo>> getUserRecords(
            @PathVariable String userId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getUserProjects - [userId : %s]", userId));

        return null;
    }
}
