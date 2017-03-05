package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.dto.TableColumnDTO;
import com.nokia.ucms.biz.entity.ProjectInfo;
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

    @RequestMapping(path="/test", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectInfo> testApi(@RequestParam String projectName)
    {
        System.out.println("Enter testApi: " + projectName);

        // TODO
        // 1. retrieve project

        ProjectInfo project = null;
        return new ApiQueryResult<ProjectInfo>(project != null, project);
    }

    @RequestMapping(path="/{projectId}", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectInfo> getProject(@PathVariable String projectId)
    {
        System.out.println("Enter getProject: " + projectId);

        // TODO
        // 1. retrieve project

        ProjectInfo project = null;
        return new ApiQueryResult<ProjectInfo>(project != null, project);
    }

    @RequestMapping(path="/", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectInfo> getAllProject()
    {
        System.out.println("Enter getProject");

        // TODO
        // 1. retrieve project

        ProjectInfo project = null;
        return new ApiQueryResult<ProjectInfo>(project != null, project);
    }

    @RequestMapping(path="/{projectId}", method= RequestMethod.PUT)
    public @ResponseBody ApiQueryResult<ProjectInfo> updateProject(@RequestBody String projectId)
    {
        System.out.println("Enter updateProject: " + projectId);

        // TODO
        // 1. update project

        ProjectInfo project = null;
        return new ApiQueryResult<ProjectInfo>(project != null, project);
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Object> createProject(@RequestBody ProjectInfo projectInfo, Model model)
    {
        System.out.println("Enter createProject: " + projectInfo);

        // TODO
        // 1. create project entry
        // 2. create project table

        boolean result = projectService.createProject(projectInfo);
        return new ApiQueryResult<Object>(result, null);
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
