package com.nokia.ucms.portal.controller;

import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.service.ProjectService;
import com.nokia.ucms.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/7.
 */
@Controller
@RequestMapping("/projects")
public class ProjectController extends BaseController
{
    @Autowired
    private ProjectService projectService;

    private void setBasicInfoInModel (String projectName, Model model)
    {
        List<ProjectInfo> projectInfoList = projectService.getProject(projectName);
        if (projectInfoList != null)
        {
            ProjectInfo projectInfo = projectInfoList.get(0);
            model.addAttribute("project", projectInfo);
        }
    }

    @RequestMapping("/{projectName}")
    public String showProject(@PathVariable String projectName, Model model)
    {
        setBasicInfoInModel(projectName, model);
        return "modules/projects/projectPage";
    }

    @RequestMapping("/{projectName}/tracelog")
    public String showProjectTrace(@PathVariable String projectName, Model model)
    {
        setBasicInfoInModel(projectName, model);
        return "modules/projects/projectTrace";
    }

    @RequestMapping("/{projectName}/columns")
    public String showProjectColumns(@PathVariable String projectName, Model model)
    {
        setBasicInfoInModel(projectName, model);
        return "modules/projects/projectColumn";
    }

    @RequestMapping("/{projectName}/tags")
    public String showProjectTags(@PathVariable String projectName, Model model)
    {
        setBasicInfoInModel(projectName, model);
        return "modules/projects/projectTag";
    }

    @RequestMapping("/{projectName}/authorization")
    public String showProjectAuthorization(@PathVariable String projectName, Model model)
    {
        setBasicInfoInModel(projectName, model);
        return "modules/projects/projectAuthorization";
    }
}