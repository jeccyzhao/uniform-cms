package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.service.ProjectColumnService;
import com.nokia.ucms.biz.service.ProjectInfoService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.entity.ApiQueryResult;
import com.nokia.ucms.common.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/6.
 */
@RestController
@RequestMapping("/openapi/v1/projects/{projectId}/columns")
public class ProjectColumnApiController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(ProjectColumnApiController.class);

    @Autowired
    private ProjectColumnService projectColumnService;

    @RequestMapping(path="", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<List<ProjectColumn>> getProjectColumns(
            @PathVariable Integer projectId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectColumns - [projectId: %d]", projectId));

        return new ApiQueryResult<List<ProjectColumn>>(projectColumnService.getProjectColumnsByProjectId(projectId));
    }

    @RequestMapping(path="/{columnId}", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectColumn> getProjectColumn(
            @PathVariable Integer projectId, @PathVariable Integer columnId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectColumn - [projectId: %d, columnId: %d]", projectId, columnId));

        if (columnId != null && columnId > 0)
        {
            ProjectColumn projectColumn = projectColumnService.getProjectColumnById(columnId);
            if (projectColumn != null)
            {
                return new ApiQueryResult<ProjectColumn>(projectColumn);
            }
            else
            {
                throw new ServiceException(String.format("Project column (id = '%d') does not exist", columnId));
            }
        }

        throw new ServiceException("Invalid column id: " + columnId);
    }

    @RequestMapping(path="/{columnId}", method= RequestMethod.PATCH)
    public @ResponseBody ApiQueryResult<ProjectColumn> updateProjectColumn(
            @PathVariable Integer projectId,
            @PathVariable Integer columnId,
            @RequestBody ProjectColumn projectColumn)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateProjectColumn - [projectId: %d, projectColumn: %s]", projectId, projectColumn));

        //projectColumn.setId(columnId);
        //projectColumn.setProjectId(projectId);

        return new ApiQueryResult<ProjectColumn>(projectColumnService.updateProjectColumn(projectId, columnId, projectColumn));
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Integer> createProjectColumn(
            @PathVariable Integer projectId,
            @RequestBody ProjectColumn projectColumn)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter createProjectColumn - [projectId: %d, projectColumn: %s]", projectId, projectColumn));

        // use path variable as property
        projectColumn.setProjectId(projectId);

        Integer result = projectColumnService.createProjectColumn(projectColumn);
        return new ApiQueryResult<Integer>(result > 0, result);
    }

    @RequestMapping(path="/{projectColumnId}", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Integer> deleteProjectColumn(
            @PathVariable Integer projectId,
            @PathVariable Integer projectColumnId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteProjectColumn - [projectId: %d, projectColumnId: %d]", projectId, projectColumnId));

        Integer result = projectColumnService.removeProjectColumn(projectColumnId);
        return new ApiQueryResult<Integer>(result);
    }

    protected String getModulePath ()
    {
        return null;
    }
}
