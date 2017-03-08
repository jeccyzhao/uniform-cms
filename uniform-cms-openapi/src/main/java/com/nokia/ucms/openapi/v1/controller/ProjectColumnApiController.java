package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.dto.TableColumnDTO;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.service.ProjectService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.entity.ApiQueryResult;
import com.nokia.ucms.common.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Service;
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
    private ProjectService projectService;

    @RequestMapping(path="", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<List<ProjectColumn>> getProjectColumn(
            @PathVariable Integer projectId,
            @RequestParam(required = false) String columnName,
            @RequestParam(required = false) Integer columnId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format(
                    "Enter getProjectColumn - [projectId: %d, columnName: %s, columnId: %d]", projectId, columnName, columnId));

        // column id first
        if (columnId != null && columnId > 0)
        {
            ProjectColumn projectColumn = projectService.getProjectColumnById(columnId);
            if (projectColumn != null)
            {
                return new ApiQueryResult<List<ProjectColumn>>(Arrays.asList(projectColumn));
            }
            else
            {
                throw new ServiceException(String.format("Project column (id = '%d') does not exist", columnId));
            }
        }

        // column name second
        if (columnName != null && !"".equals(columnName))
        {
            return new ApiQueryResult<List<ProjectColumn>>(projectService.getProjectColumnsByName(columnName));
        }

        return new ApiQueryResult<List<ProjectColumn>>(projectService.getProjectColumnsByProjectId(projectId));
    }

    @RequestMapping(path="", method= RequestMethod.PUT)
    public @ResponseBody ApiQueryResult<ProjectColumn> updateProjectColumn(
            @PathVariable Integer projectId,
            @RequestBody ProjectColumn projectColumn)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateProjectColumn - [projectId: %d, projectColumn: %s]", projectId, projectColumn));

        return new ApiQueryResult<ProjectColumn>(projectService.updateProjectColumn(projectId, projectColumn));
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

        Integer result = projectService.createProjectColumn(projectColumn);
        return new ApiQueryResult<Integer>(result > 0, result);
    }

    @RequestMapping(path="", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Integer> deleteProjectColumn(
            @PathVariable Integer projectId,
            @RequestBody TableColumnDTO tableColumn)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteProjectColumn - [projectId: %d, tableColumn: %s]", projectId, tableColumn));

        // TODO
        // 1. remove project column data from data table
        // 2. remove project column entry
        return createEmptyQueryResult();
    }

    protected String getModulePath ()
    {
        return null;
    }
}
