package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.dto.ProjectDataTableDTO;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.service.ProjectInfoService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.entity.ApiQueryResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/5.
 */
@RestController
@RequestMapping("/openapi/v1/projects")
public class ProjectInfoApiController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(ProjectInfoApiController.class);

    @Autowired
    private ProjectInfoService projectInfoService;

    @RequestMapping(path="/{projectId}", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectDataTableDTO> getProject(
            @PathVariable Integer projectId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProject - [projectId : %d]", projectId));

        return new ApiQueryResult<ProjectDataTableDTO>(projectInfoService.getProjectById(projectId, null));
    }

    @RequestMapping(path="/{projectId}", method= RequestMethod.PUT)
    public @ResponseBody ApiQueryResult<ProjectInfo> updateProject(
            @PathVariable Integer projectId,
            @RequestBody ProjectInfo projectInfo)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateProject - [projectId: %d, projectInfo: %s]", projectId, projectInfo));

        // TODO Validation must be added before update

        // set id with path variable
        projectInfo.setId(projectId);

        boolean result = projectInfoService.updateProject(projectInfo);
        return new ApiQueryResult<ProjectInfo>(result);
    }

    @RequestMapping(path="/", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<List<ProjectInfo>> getProject(
            @RequestParam(required = false) String projectName)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter getAllProject");

        return new ApiQueryResult<List<ProjectInfo>>(projectInfoService.getProject(projectName));
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Object> createProject(
            @RequestBody ProjectInfo projectInfo,
            @RequestParam(value = "from", required = false) Integer fromProject)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter createProject - [projectInfo: %s, from: %d]", projectInfo, fromProject));

        Integer result = projectInfoService.createProject(projectInfo, fromProject);
        return new ApiQueryResult<Object>(result > 0, result);
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

    protected String getModulePath ()
    {
        return null;
    }
}