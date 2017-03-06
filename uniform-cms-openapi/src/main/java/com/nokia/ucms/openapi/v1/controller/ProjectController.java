package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.service.ProjectService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.entity.ApiQueryResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by x36zhao on 2017/3/5.
 */
@RestController
@RequestMapping("/openapi/v1/projects")
public class ProjectController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @RequestMapping(path="/", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectInfo> getProject(
            @RequestParam(required = false) String projectName)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter getAllProject");

        // TODO
        // 1. retrieve project

        ProjectInfo project = null;
        return new ApiQueryResult<ProjectInfo>(project != null, project);
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Object> createProject(
            @RequestBody ProjectInfo projectInfo,
            @RequestParam(value = "from", required = false) Integer fromProject)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter createProject - [projectInfo: %s, from: %d]", projectInfo, fromProject));

        // TODO
        // 1. create project entry
        // 2. create project table

        boolean result = projectService.createProject(projectInfo, fromProject);
        return new ApiQueryResult<Object>(result, null);
    }

    @RequestMapping(path="", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Object> deleteProject(
            @RequestParam String projectName)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteProject - [projectName: %s]", projectName));

        // TODO
        // 1. remove project data
        // 2. remove project columns
        // 3. remove project entry
        return createEmptyQueryResult();
    }

    @RequestMapping(path="/{projectId}", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectInfo> getProject(
            @PathVariable Integer projectId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProject - [projectId: %d]", projectId));

        // TODO
        // 1. retrieve project
        return createEmptyQueryResult();
    }

    @RequestMapping(path="/{projectId}", method= RequestMethod.PUT)
    public @ResponseBody ApiQueryResult<ProjectInfo> updateProject(
            @RequestBody Integer projectId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateProject - [projectId: %d]", projectId));

        // TODO
        // 1. update project
        return createEmptyQueryResult();
    }

}
