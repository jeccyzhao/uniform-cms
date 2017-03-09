package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.entity.ProjectCategory;
import com.nokia.ucms.biz.service.ProjectInfoService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.entity.ApiQueryResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by x36zhao on 2017/3/6.
 */
@RestController
@RequestMapping("/openapi/v1/projects/{pid}/categories")
public class ProjectCategoryApiController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(ProjectCategoryApiController.class);

    @Autowired
    private ProjectInfoService projectService;

    @RequestMapping(path="", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectCategory> getProjectCategory(
            @PathVariable Integer projectId,
            @RequestParam(required = false) String categoryName)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectCategory - [projectId: %d, categoryName: %s]", projectId, categoryName));

        // TODO
        return createEmptyQueryResult();
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Integer> createProjectCategory(
            @PathVariable Integer projectId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter createProjectCategory - [projectId: %d]", projectId));

        // TODO
        return createEmptyQueryResult();
    }

    @RequestMapping(path="", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Object> deleteProjectCategory(
            @PathVariable Integer projectId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteProjectCategory - [projectId: %d]", projectId));

        // TODO
        return createEmptyQueryResult();
    }

    protected String getModulePath ()
    {
        return null;
    }
}
