package com.nokia.ucms.portal.controller;

import static com.nokia.ucms.biz.dto.ProjectRecordDataDTO.*;

import com.nokia.ucms.biz.entity.ProjectCategory;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.service.ProjectCategoryService;
import com.nokia.ucms.biz.service.ProjectColumnService;
import com.nokia.ucms.biz.service.ProjectInfoService;
import com.nokia.ucms.biz.service.ProjectRecordService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.dto.ExcelSheetDataDTO;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.utils.ExcelParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
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

    @RequestMapping("/{projectName}")
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

    @RequestMapping("/{projectName}/columns")
    public String showProjectColumns(@PathVariable String projectName, Model model)
    {
        setBasicInfoInModel(projectName, model);
        return getModulePage("projectColumn");
    }

    @RequestMapping("/{projectName}/tags")
    public String showProjectTags(@PathVariable String projectName, Model model)
    {
        setBasicInfoInModel(projectName, model);
        return getModulePage("projectTag");
    }

    @RequestMapping("/{projectName}/authorization")
    public String showProjectAuthorization(@PathVariable String projectName, Model model)
    {
        setBasicInfoInModel(projectName, model);
        return getModulePage("projectAuthorization");
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

    @RequestMapping("/{projectName}/categories/{categoryId}/import")
    public String importProjectRecords (@PathVariable String projectName,
                                        @PathVariable Integer categoryId,
                                        @RequestParam("file") MultipartFile file)
    {
        if (file.isEmpty())
        {
            // TODO: return error page
        }

        String storageFile = saveToFile(file);
        if (storageFile != null)
        {
            try
            {
                ExcelParser parser = new ExcelParser();
                ExcelSheetDataDTO sheetData = parser.parseFile(storageFile);
                if (sheetData != null)
                {
                    ProjectInfo projectInfo = projectInfoService.getProjectByName(projectName);
                    ProjectCategory projectCategory = projectCategoryService.getProjectCategoryById(categoryId);
                    List<ProjectColumn> projectColumns = this.projectColumnService.getProjectColumnsByProjectName(projectName);

                    List<ProjectRecordDataRow> recordDataList = constructProjectRecordData(sheetData, projectColumns);
                    for (ProjectRecordDataRow recordData : recordDataList)
                    {
                        recordData.setCategoryId(projectCategory.getId());
                        try
                        {
                            this.projectRecordService.addProjectRecord(projectInfo.getId(), recordData);
                        }
                        catch (Exception ex)
                        {
                            LOGGER.error(String.format("Failed to add project record data (%s) due to %s", recordData, ex));
                        }
                    }
                }
            }
            catch (ServiceException ex)
            {
                LOGGER.error("Failed to import project record data:  " + ex.getMessage());
            }
            catch (Exception ex)
            {
                LOGGER.error("Failed to import project record data:  " + ex.getMessage());
            }
        }
        else
        {
            // TODO:...
        }

        return "";
    }

    private List<ProjectRecordDataRow> constructProjectRecordData (final ExcelSheetDataDTO sheetData, final List<ProjectColumn> projectColumns)
    {
        List<ProjectRecordDataRow> recordDataList = new ArrayList<ProjectRecordDataRow>();
        for (ExcelSheetDataDTO.Row row : sheetData.getRows())
        {
            ProjectRecordDataRow recordData = new ProjectRecordDataRow();
            for (ProjectColumn projectColumn : projectColumns)
            {
                int columnIndex = sheetData.getHeaderColumnIndexByName(projectColumn.getColumnName());
                if (columnIndex > -1)
                {
                    Object cellValue = row.getCellValue(columnIndex);
                    recordData.addProperty(projectColumn.getColumnName(), cellValue != null ? cellValue.toString() : null);
                }
            }

            recordDataList.add(recordData);
        }

        return recordDataList;
    }


    private String saveToFile (final MultipartFile file)
    {
        OutputStream outputStream = null;
        try
        {
            String fileFullName = file.getOriginalFilename();
            String fileExt = null;
            String fileName = null;
            if (fileFullName.lastIndexOf(".") > -1)
            {
                fileExt = fileFullName.substring(fileFullName.lastIndexOf("."));
                fileName = fileFullName.substring(0, fileFullName.lastIndexOf("."));
            }
            else
            {
                fileExt = "tmp";
                fileName = fileFullName;
            }

            File tempFile = File.createTempFile(fileName, fileExt);
            byte[] bytes = file.getBytes();
            // String targetFile = String.format("%s/%s", DEFAULT_UPLOAD_FOLDER, file.getOriginalFilename());
            outputStream = new FileOutputStream(tempFile);
            outputStream.write(bytes);

            return tempFile.getAbsolutePath();
        }
        catch (IOException e)
        {
            LOGGER.error("Failed to save to file: " + e);
        }
        finally
        {
            if (outputStream != null)
            {
                try
                {
                    outputStream.close();
                }
                catch (IOException e)
                {
                    // do nothing
                }
            }
        }

        return null;
    }

    protected String getModulePath ()
    {
        return "modules/projects";
    }
}
