package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.entity.ProjectCategory;
import com.nokia.ucms.biz.service.ProjectCategoryService;
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
@RequestMapping("/openapi/v1/projects/{projectId}/categories")
public class ProjectCategoryApiController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(ProjectCategoryApiController.class);

    @Autowired
    private ProjectInfoService projectService;

    @Autowired
    private ProjectCategoryService projectCategoryService;

    @RequestMapping(path="", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<List<ProjectCategory>> getProjectCategories(
            @PathVariable Integer projectId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectCategories - [projectId: %d]", projectId));

        List<ProjectCategory> categories = this.projectCategoryService.getProjectCategories(projectId);
        return new ApiQueryResult<List<ProjectCategory>>(categories);
    }

    @RequestMapping(path="/{categoryId}", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectCategory> getProjectCatgory(
            @PathVariable Integer projectId, @PathVariable Integer categoryId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectCatgory - [projectId: %d, categoryId: %d]", projectId, categoryId));

        ProjectCategory category = this.projectCategoryService.getProjectCategoryById(categoryId);
        return new ApiQueryResult<ProjectCategory>(category);
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<ProjectCategory> createProjectCategory(
            @PathVariable Integer projectId, @RequestBody ProjectCategory projectCategory)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter createProjectCategory - [projectId: %d]", projectId));

        ProjectCategory category = this.projectCategoryService.addProjectCategory(projectId, projectCategory);
        if (category != null)
        {
            return new ApiQueryResult<ProjectCategory>(category);
        }
        else
        {
            return new ApiQueryResult<ProjectCategory>(false, null, "Failed to create project category");
        }
    }

    @RequestMapping(path="/{categoryId}", method= RequestMethod.PATCH)
    public @ResponseBody ApiQueryResult<ProjectCategory> updateProjectCategory(
            @PathVariable Integer projectId,
            @PathVariable Integer categoryId,
            @RequestBody ProjectCategory projectCategory)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateProjectCategory - [projectId: %d, projectCategory: %s]", projectId, projectCategory));

        if (projectCategory != null)
        {
            // overwrite with given project id anyway to avoid incorrect update
            projectCategory.setProjectId(projectId);

            ProjectCategory category = this.projectCategoryService.updateProjectCategory(projectId, categoryId, projectCategory);
            if (category != null)
            {
                return new ApiQueryResult<ProjectCategory>(category);
            }
            else
            {
                return new ApiQueryResult<ProjectCategory>(false, null, "Failed to update project category: " + projectCategory);
            }
        }

        return new ApiQueryResult<ProjectCategory>(false, null, "Invalid project category: " + projectCategory);
    }

    @RequestMapping(path="/{categoryId}", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Object> deleteProjectCategory(
            @PathVariable Integer projectId, @PathVariable Integer categoryId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteProjectCategory - [projectId: %d, categoryId: %d]", projectId, categoryId));

        boolean result = this.projectCategoryService.removeProjectCategory(categoryId);
        return new ApiQueryResult<Object>(result, null);
    }

    protected String getModulePath ()
    {
        return null;
    }
}
