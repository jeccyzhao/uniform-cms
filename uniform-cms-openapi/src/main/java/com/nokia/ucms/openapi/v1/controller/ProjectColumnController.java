package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.dto.TableColumnDTO;
import com.nokia.ucms.biz.service.ProjectService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.entity.ApiQueryResult;
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
    @Autowired
    private ProjectService projectService;

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody
    ApiQueryResult<Integer> createProjectColumn(@PathVariable Integer projectId,
                                                @RequestBody TableColumnDTO tableColumn, Model model)
    {
        System.out.println("Enter createProjectColumn");
        Integer result = projectService.createProjectColumn(null);
        return new ApiQueryResult<Integer>(result != null, result);
    }

    @RequestMapping(path="", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Object> deleteProjectColumn(@PathVariable Integer projectId,
                                                                    @RequestBody TableColumnDTO tableColumn, Model model)
    {
        System.out.println("Enter deleteProjectColumn");

        // TODO
        // 1. remove project column data from data table
        // 2. remove project column entry

        Object result = null;
        return new ApiQueryResult<Object>(result != null, result);
    }
}
