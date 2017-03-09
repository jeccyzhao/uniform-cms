package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.entity.ProjectTag;
import com.nokia.ucms.biz.entity.ProjectTrace;
import com.nokia.ucms.biz.service.ProjectInfoService;
import com.nokia.ucms.biz.service.ProjectTraceService;
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
@RequestMapping("/openapi/v1/projects/{pid}/traces")
public class ProjectTraceApiController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(ProjectTraceApiController.class);

    @Autowired
    private ProjectTraceService projectTraceService;

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<List<ProjectTrace>> getProjectTraces(@PathVariable Integer projectId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectTraces - [projectId: %d]", projectId));

        return new ApiQueryResult<List<ProjectTrace>>(projectTraceService.getProjectTraces(projectId));
    }

    @RequestMapping(path="/{projectTraceId}", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Object> deleteProjectTrace(@PathVariable Integer projectTraceId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteProjectTrace - [projectTraceId: %d]", projectTraceId));

        //boolean result = projectTraceService

        // TODO
        return createEmptyQueryResult();
    }

    @RequestMapping(path="/", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Object> deleteProjectTraces(@PathVariable Integer projectId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteProjectTraces - [projectId: %d]", projectId));

        //boolean result = projectTraceService

        // TODO
        return createEmptyQueryResult();
    }

    protected String getModulePath ()
    {
        return null;
    }
}
