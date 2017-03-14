package com.nokia.ucms.openapi.v1.controller;

import com.nokia.ucms.biz.dto.ProjectRecordDataDTO;
import com.nokia.ucms.biz.entity.ProjectCategory;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.service.ProjectCategoryService;
import com.nokia.ucms.biz.service.ProjectColumnService;
import com.nokia.ucms.biz.service.ProjectInfoService;
import com.nokia.ucms.biz.service.ProjectRecordService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.dto.ExcelSheetDataDTO;
import com.nokia.ucms.common.entity.ApiQueryResult;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.utils.ExcelParser;
import com.nokia.ucms.common.utils.FileUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nokia.ucms.biz.dto.ProjectRecordDataDTO.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/11.
 */
@RestController
@RequestMapping("/openapi/v1/projects/{projectId}/records")
public class ProjectRecordApiController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(ProjectRecordApiController.class);

    private static int IMPORT_PARTIAL_SUCCESS = 1;
    private static int IMPORT_COMPLETE_SUCCESS = 0;
    private static int IMPORT_FAILURE = -1;

    @Autowired
    private ProjectRecordService projectRecordService;

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private ProjectColumnService projectColumnService;

    @Autowired
    private ProjectCategoryService projectCategoryService;

    @RequestMapping(path="", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectRecordDataDTO> getProjectRecords(
            @PathVariable Integer projectId,
            @RequestParam(required = false) Integer categoryId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectRecords - [projectId : %d]", projectId));

        return new ApiQueryResult<ProjectRecordDataDTO>(projectRecordService.getProjectRecordsByCategory(projectId, categoryId));
    }

    @RequestMapping(path="/{recordId}", method= RequestMethod.GET)
    public @ResponseBody ApiQueryResult<ProjectRecordDataDTO> getProjectRecord (
            @PathVariable Integer projectId,
            @PathVariable Integer recordId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getProjectRecord - [projectId : %d, recordId: %d]", projectId, recordId));

        return new ApiQueryResult<ProjectRecordDataDTO>(projectRecordService.getProjectRecordById(projectId, recordId));
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public @ResponseBody ApiQueryResult<Integer> addProjectRecord(
            @PathVariable Integer projectId,
            @RequestParam(required = false) Integer categoryId,
            @RequestBody ProjectRecordDataRow recordData)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addProjectRecord - [projectId : %d, categoryId: %d]", projectId, categoryId));

        return new ApiQueryResult<Integer>(projectRecordService.addProjectRecord(projectId, recordData));
    }

    @RequestMapping(path="/{recordId}", method= RequestMethod.PATCH)
    public @ResponseBody ApiQueryResult<ProjectRecordDataDTO> updateProjectRecord(
            @PathVariable Integer projectId,
            @PathVariable Integer recordId,
            @RequestBody ProjectRecordDataRow recordData)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateProjectRecord - [projectId : %d, recordId: %d]", projectId, recordId));

        return new ApiQueryResult<ProjectRecordDataDTO>(projectRecordService.updateProjectRecord(projectId, recordId, recordData));
    }

    @RequestMapping(path="/{recordId}", method= RequestMethod.DELETE)
    public @ResponseBody ApiQueryResult<Integer> deleteProjectRecord(
            @PathVariable Integer projectId,
            @PathVariable Integer recordId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteProjectRecord - [projectId : %d, recordId: %d]", projectId, recordId));

        Integer result = this.projectRecordService.deleteProjectRecord(projectId, recordId);
        return new ApiQueryResult<Integer>(result != null, result);
    }

    @RequestMapping(path="/import", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    public @ResponseBody ApiQueryResult<Integer> importProjectRecord (
            @PathVariable Integer projectId, @RequestParam ("categoryId") Integer categoryId, @RequestParam("file") MultipartFile file)
    {
        if (file.isEmpty())
        {
            return new ApiQueryResult<Integer>("The uploaded file for import is empty");
        }

        String targetFile = FileUtil.saveMultipartFile(file);
        if (targetFile != null)
        {
            int result = this.projectRecordService.batchAddProjectRecordsFromExcelFile(projectId, categoryId, targetFile);
            if (result == ProjectRecordService.IMPORT_COMPLETE_SUCCESS)
            {
                return new ApiQueryResult<Integer>(true);
            }
            else if (result == ProjectRecordService.IMPORT_PARTIAL_SUCCESS)
            {
                return new ApiQueryResult<Integer>("Import is partial successful and probably some properties exceeds the column length in database");
            }
            else
            {
                return new ApiQueryResult<Integer>("Import is failed");
            }
        }
        else
        {
            return new ApiQueryResult<Integer>("Failed to handle the input file");
        }
    }

    protected String getModulePath ()
    {
        return null;
    }
}
