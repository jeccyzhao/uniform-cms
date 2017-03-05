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
@RequestMapping("/openapi/v1/projects/{pid}/categories")
public class ProjectCategoryController extends BaseController
{
    @Autowired
    private ProjectService projectService;

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Object> createProjectCategory(@PathVariable Integer projectId, Model model)
    {
        System.out.println("Enter createProjectCategory");

        // TODO
        return createEmptyQueryResult();
    }

    @RequestMapping(path="", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Object> deleteProjectCategory(@PathVariable Integer projectId, Model model)
    {
        System.out.println("Enter deleteProjectCategory");

        // TODO
        return createEmptyQueryResult();
    }
}
