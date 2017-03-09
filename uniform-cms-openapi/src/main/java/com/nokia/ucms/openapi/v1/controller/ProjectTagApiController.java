package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.entity.ProjectTag;
import com.nokia.ucms.biz.service.ProjectInfoService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.entity.ApiQueryResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/6.
 */
@RestController
@RequestMapping("/openapi/v1/projects/{pid}/tags")
public class ProjectTagApiController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(ProjectTagApiController.class);

    @Autowired
    private ProjectInfoService projectService;

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Integer> createProjectTag(
            @PathVariable Integer projectId,
            @RequestBody ProjectTag projectTag)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter createProjectTag - [projectId: %d, tag: %s]", projectId, projectTag));

        // TODO
        return createEmptyQueryResult();
    }

    @RequestMapping(path="", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<List<ProjectTag>> getProjectTag(
            @PathVariable Integer projectId,
            @RequestParam String tagName)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectTag - [projectId: %d, tagName: %s]", projectId, tagName));

        // TODO
        return createEmptyQueryResult();
    }

    @RequestMapping(path="", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Object> deleteProjectTag(
            @PathVariable Integer projectId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteProjectTag - [projectId: %d]", projectId));

        // TODO
        return createEmptyQueryResult();
    }

    protected String getModulePath ()
    {
        return null;
    }
}
