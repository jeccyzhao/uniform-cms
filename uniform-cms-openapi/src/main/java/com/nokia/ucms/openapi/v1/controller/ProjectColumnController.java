package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.dto.TableColumnDTO;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.service.ProjectService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.entity.ApiQueryResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by x36zhao on 2017/3/6.
 */
@Controller
@RequestMapping("/openapi/v1/projects/{pid}/columns")
public class ProjectColumnController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(ProjectColumnController.class);

    @Autowired
    private ProjectService projectService;

    @RequestMapping(path="", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectColumn> getProjectColumn(
            @PathVariable Integer projectId,
            @RequestParam(required = false) String columnName)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectColumn - [projectId: %d, columnName: %s]", projectId, columnName));

        // TODO
        return createEmptyQueryResult();
    }

    @RequestMapping(path="", method= RequestMethod.PUT)
    public @ResponseBody ApiQueryResult<Integer> updateProjectColumn(
            @PathVariable Integer projectId,
            @RequestBody TableColumnDTO tableColumn)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateProjectColumn - [projectId: %d, tableColumn: %s]", projectId, tableColumn));

        // TODO
        return createEmptyQueryResult();
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Integer> createProjectColumn(
            @PathVariable Integer projectId,
            @RequestBody TableColumnDTO tableColumn)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter createProjectColumn - [projectId: %d, tableColumn: %s]", projectId, tableColumn));

        Integer result = projectService.createProjectColumn(null);
        return new ApiQueryResult<Integer>(result != null, result);
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
}
