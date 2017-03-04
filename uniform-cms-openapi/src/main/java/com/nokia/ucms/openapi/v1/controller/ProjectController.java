package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.dto.TableColumnDTO;
import com.nokia.ucms.biz.entity.Project;
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
@RequestMapping("/openapi/v1/projects")
public class ProjectController
{
    @Autowired
    private ProjectService projectService;

    @RequestMapping(path="/{projectId}", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<Project> getProject(@PathVariable String projectId)
    {
        System.out.println("Enter getProject: " + projectId);

        // TODO
        // 1. retrieve project

        Project project = null;
        return new ApiQueryResult<Project>(project != null, project);
    }

    @RequestMapping(path="/{projectId}", method= RequestMethod.PUT)
    public @ResponseBody ApiQueryResult<Project> updateProject(@RequestBody String projectId)
    {
        System.out.println("Enter updateProject: " + projectId);

        // TODO
        // 1. update project

        Project project = null;
        return new ApiQueryResult<Project>(project != null, project);
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Integer> createProject(@RequestParam String projectName, Model model)
    {
        System.out.println("Enter createProject: " + projectName);

        // TODO
        // 1. create project entry
        // 2. create project table

        Integer result = projectService.createProject(projectName);
        return new ApiQueryResult<Integer>(result != null, result);
    }

    @RequestMapping(path="", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Object> deleteProject(@RequestParam String projectName, Model model)
    {
        System.out.println("Enter deleteProject: " + projectName);

        // TODO
        // 1. remove project data
        // 2. remove project columns
        // 3. remove project entry

        Object result = null;
        return new ApiQueryResult<Object>(result != null, result);
    }

    @RequestMapping(path="/columns", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Integer> createProjectColumn(@RequestBody TableColumnDTO tableColumn, Model model)
    {
        System.out.println("Enter createProjectColumn");
        Integer result = projectService.addProjectField(tableColumn);
        return new ApiQueryResult<Integer>(result != null, result);
    }

    @RequestMapping(path="/columns", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Object> deleteProjectColumn(@RequestBody TableColumnDTO tableColumn, Model model)
    {
        System.out.println("Enter removeProjectColumn");

        // TODO
        // 1. remove project column data from data table
        // 2. remove project column entry

        Object result = null;
        return new ApiQueryResult<Object>(result != null, result);
    }
}
