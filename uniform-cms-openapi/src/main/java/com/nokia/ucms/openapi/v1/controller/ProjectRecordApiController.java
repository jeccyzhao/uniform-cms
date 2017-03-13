package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.dto.ProjectRecordDataDTO;
import com.nokia.ucms.biz.service.ProjectRecordService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.entity.ApiQueryResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nokia.ucms.biz.dto.ProjectRecordDataDTO.*;

/**
 * Created by x36zhao on 2017/3/11.
 */
@RestController
@RequestMapping("/openapi/v1/projects/{projectId}/records")
public class ProjectRecordApiController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(ProjectRecordApiController.class);

    @Autowired
    private ProjectRecordService projectRecordService;

    @RequestMapping(path="", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectRecordDataDTO> getProjectRecords(@PathVariable Integer projectId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectRecords - [projectId : %d]", projectId));

        return new ApiQueryResult<ProjectRecordDataDTO>(projectRecordService.getProjectRecordsByCategory(projectId, null));
    }

    @RequestMapping(path="/{recordId}", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectRecordDataDTO> getProjectRecord (
            @PathVariable Integer projectId, @PathVariable Integer recordId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectRecord - [projectId : %d, recordId: %d]", projectId, recordId));

        return new ApiQueryResult<ProjectRecordDataDTO>(projectRecordService.getProjectRecordById(projectId, recordId));
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Integer> addProjectRecord(
            @PathVariable Integer projectId, @RequestParam(required = false) Integer categoryId,
            @RequestBody ProjectRecordDataRow recordData)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addProjectRecord - [projectId : %d, categoryId: %d]", projectId, categoryId));

        return new ApiQueryResult<Integer>(projectRecordService.addProjectRecord(projectId, recordData));
    }

    @RequestMapping(path="/{recordId}", method= RequestMethod.PUT)
    public @ResponseBody ApiQueryResult<ProjectRecordDataDTO> updateProjectRecord(
            @PathVariable Integer projectId, @PathVariable Integer recordId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addProjectRecord - [projectId : %d, recordId: %d]", projectId, recordId));

        return createEmptyQueryResult();
    }

    @RequestMapping(path="/{recordId}", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Integer> deleteProjectRecord(
            @PathVariable Integer projectId, @PathVariable Integer recordId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteProjectRecord - [projectId : %d, recordId: %d]", projectId, recordId));

        Integer result = this.projectRecordService.deleteProjectRecord(projectId, recordId);
        return new ApiQueryResult<Integer>(result != null, result);
    }

    protected String getModulePath ()
    {
        return null;
    }
}
