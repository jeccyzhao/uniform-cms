package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.entity.ProjectTag;
import com.nokia.ucms.biz.service.ProjectInfoService;
import com.nokia.ucms.biz.service.ProjectTagService;
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
@RequestMapping("/openapi/v1/projects/{projectId}/tags")
public class ProjectTagApiController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(ProjectTagApiController.class);

    @Autowired
    private ProjectInfoService projectService;

    @Autowired
    private ProjectTagService projectTagService;


    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<ProjectTag> createProjectTag(
            @PathVariable Integer projectId,
            @RequestBody ProjectTag projectTag)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter createProjectTag - [projectId: %d, tag: %s]", projectId, projectTag));

        ProjectTag tag = this.projectTagService.addProjectTag(projectId, projectTag);
        if (tag != null)
        {
            return new ApiQueryResult<ProjectTag>(tag);
        }
        else
        {
            return new ApiQueryResult<ProjectTag>(false, null, "Failed to create project tag");
        }
    }

    @RequestMapping(path="", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<List<ProjectTag>> getProjectTags(
            @PathVariable Integer projectId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectTags - [projectId: %d", projectId));

        List<ProjectTag> tags = this.projectTagService.getProjectTags(projectId);
        return new ApiQueryResult<List<ProjectTag>>(tags);
    }

    @RequestMapping(path="/{tagId}", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectTag> getProjectTag(
            @PathVariable Integer projectId,
            @PathVariable Integer tagId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectTag - [projectId: %d, tagId: %d]", projectId, tagId));

        ProjectTag tag = this.projectTagService.getProjectTagById(tagId);
        return new ApiQueryResult<ProjectTag>(tag);
    }

    @RequestMapping(path="/{tagId}", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Object> deleteProjectTag(
            @PathVariable Integer projectId,
            @PathVariable Integer tagId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteProjectTag - [projectId: %d, tagId: %d]", projectId, tagId));

        boolean result = this.projectTagService.removeProjectTag(tagId);
        return new ApiQueryResult<Object>(result, null);
    }

    protected String getModulePath ()
    {
        return null;
    }
}
