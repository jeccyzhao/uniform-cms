package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.dto.TableColumnDTO;
import com.nokia.ucms.biz.service.ProjectService;
import com.nokia.ucms.common.entity.ApiQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by x36zhao on 2017/3/5.
 */
@Controller
@RequestMapping("/openapi/v1/project")
public class ProjectController
{
    @Autowired
    private ProjectService projectService;

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Integer> createProject(@RequestParam String projectName, Model model)
    {
        System.out.println("Enter createProject: " + projectName);
        Integer result = projectService.createProject(projectName);
        return new ApiQueryResult<Integer>(result != null, result);
    }

    @RequestMapping(path="/column", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Integer> addProjectColumn(@RequestBody TableColumnDTO tableColumn, Model model)
    {
        System.out.println("Enter createProjectColumn");
        Integer result = projectService.addProjectField(tableColumn);
        return new ApiQueryResult<Integer>(result != null, result);
    }
}
