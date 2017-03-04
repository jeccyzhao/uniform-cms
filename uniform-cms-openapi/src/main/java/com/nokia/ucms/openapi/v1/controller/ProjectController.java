package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.service.DatabaseService;
import com.nokia.ucms.common.entity.ApiQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by x36zhao on 2017/3/5.
 */
@Controller
@RequestMapping("/openapi/v1/project")
public class ProjectController
{
    @Autowired
    private DatabaseService dbService;

    @RequestMapping(path="/", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Integer> createProject(@RequestParam String projectName, Model model)
    {
        System.out.println("Enter createProject: " + projectName);
        Integer result = dbService.createTable(projectName);
        return new ApiQueryResult<Integer>(result != null, result);
    }

    @RequestMapping(path="/column", method= RequestMethod.POST)
    public @ResponseBody String createProjectColumn(
            @RequestParam String columnName,
            @RequestParam String projectName, Model model)
    {
        System.out.println("Enter createProjectColumn");
        //dbService("dddd");
        return "modules/index";
    }
}
