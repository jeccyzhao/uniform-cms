package com.nokia.ucms.portal.controller;

import static com.nokia.ucms.common.constants.Constants.PAGE_ERROR;

import com.nokia.ucms.biz.entity.ProjectCategory;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.entity.User;
import com.nokia.ucms.biz.service.ProjectCategoryService;
import com.nokia.ucms.biz.service.ProjectColumnService;
import com.nokia.ucms.biz.service.ProjectInfoService;
import com.nokia.ucms.biz.service.ProjectRecordService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.exception.NoPermissionAccessPageException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/7.
 */
@Controller
@RequestMapping("/projects")
public class ProjectController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(ProjectController.class);

    @Autowired
    private ProjectInfoService projectService;

    @Autowired
    private ProjectColumnService projectColumnService;

    @Autowired
    private ProjectCategoryService projectCategoryService;

    @Autowired
    private ProjectRecordService projectRecordService;

    @Autowired
    private ProjectInfoService projectInfoService;

    private ProjectInfo setBasicInfoInModel (String projectName, Model model)
    {
        List<ProjectInfo> projectInfoList = projectService.getProject(projectName);
        if (projectInfoList != null && projectInfoList.size() > 0)
        {
            ProjectInfo projectInfo = projectInfoList.get(0);
            model.addAttribute("project", projectInfo);

            return projectInfo;
        }
        else
        {
            // TODO: Error handle must be addressed in case project not exists, probably exception could be thrown
        }

        return null;
    }

    @RequestMapping("/{projectName}/records")
    public String showProjectRecords(@PathVariable String projectName, @RequestParam (required = false) String category, Model model)
    {
        ProjectInfo projectInfo = setBasicInfoInModel(projectName, model);
        model.addAttribute("columns", projectColumnService.getProjectColumnsByProjectName(projectName));
        if (category != null && !"".equals(category.trim()))
        {
            ProjectCategory projectCategory = projectCategoryService.getProjectCategoryByName(projectInfo.getId(), category);
            if (projectCategory == null)
            {
                // TODO Error page must be returned.
            }

            model.addAttribute("category", projectCategory);
        }
        else
        {
            // get all available categories, aimed to render the dropdown list when adding record
            model.addAttribute("categories", projectCategoryService.getProjectCategories(projectName));
        }

        return getModulePage("projectRecord");
    }

    @RequestMapping("/{projectName}/trace")
    public String showProjectTrace(@PathVariable String projectName, Model model)
    {
        setBasicInfoInModel(projectName, model);
        return getModulePage("projectTrace");
    }

    @RequestMapping("/{projectName}/categories")
    public String showProjectCategories(@PathVariable String projectName, Model model)
    {
        setBasicInfoInModel(projectName, model);
        return getModulePage("projectCategory");
    }

    @RequestMapping("/{projectName}")
    public String showProjectDefaultPage(@PathVariable String projectName, Model model)
    {
        return showProjectCategories(projectName, model);
    }

    @RequestMapping("/{projectName}/columns")
    @PreAuthorize("isAuthenticated()")
    public String showProjectColumns(@PathVariable String projectName, Model model)
    {
        ProjectInfo projectInfo = setBasicInfoInModel(projectName, model);
        if (isProjectOwner(projectInfo))
        {
            return getModulePage("projectColumn");
        }

        throw new NoPermissionAccessPageException("You're not permitted to access project columns page!");
    }

    @RequestMapping("/{projectName}/tags")
    @PreAuthorize("isAuthenticated()")
    public String showProjectTags(@PathVariable String projectName, Model model)
    {
        ProjectInfo projectInfo = setBasicInfoInModel(projectName, model);
        if (isProjectOwner(projectInfo))
        {
            return getModulePage("projectTag");
        }

        throw new NoPermissionAccessPageException("You're not permitted to access project tags page!");
    }

    @RequestMapping("/{projectName}/authorization")
    @PreAuthorize("isAuthenticated()")
    public String showProjectAuthorization(@PathVariable String projectName, Model model)
    {
        ProjectInfo projectInfo = setBasicInfoInModel(projectName, model);
        if (isProjectOwner(projectInfo))
        {
            return getModulePage("projectAuthorization");
        }

        throw new NoPermissionAccessPageException("You're not permitted to access project authorization page!");
    }

    @RequestMapping("/{projectName}/categories/{categoryId}/export")
    public void exportProjectRecords (@PathVariable String projectName,
                                      @PathVariable Integer categoryId,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws IOException
    {
        String content = request.getParameter("content");
        String fileName = request.getParameter("filename");
        String format = request.getParameter("format");

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", String.format("attachment;filename=%s.%s", fileName, format));
        PrintWriter printWriter = response.getWriter();
        printWriter.print(content);
        printWriter.flush();
        printWriter.close();
    }

    protected boolean isProjectOwner (final ProjectInfo projectInfo)
    {
        User authenticatedPrinciple = (User) getAuthenticatedPrinciple();
        if (authenticatedPrinciple != null && projectInfo != null &&
                authenticatedPrinciple.getUserName().equals(projectInfo.getOwner().getUserName()))
        {
            return true;
        }

        return false;
    }

    protected String getModulePath ()
    {
        return "modules/projects";
    }
}
