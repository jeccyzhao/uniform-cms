package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.constants.EOperationType;
import com.nokia.ucms.biz.constants.EServiceDomain;
import com.nokia.ucms.biz.constants.ETemplateColumnProperty;
import com.nokia.ucms.biz.dto.ProjectRecordDataDTO;
import com.nokia.ucms.biz.entity.ProjectCategory;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
import com.nokia.ucms.biz.repository.ProjectCategoryRepository;
import com.nokia.ucms.biz.repository.ProjectColumnRepository;
import com.nokia.ucms.biz.repository.ProjectInfoRepository;
import com.nokia.ucms.common.dto.ExcelSheetDataDTO;
import com.nokia.ucms.common.entity.ApiQueryResult;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.service.BaseService;
import com.nokia.ucms.common.utils.ExcelParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.util.*;

import static com.nokia.ucms.biz.dto.ProjectRecordDataDTO.*;

/**
 * Created by x36zhao on 2017/3/11.
 */
@Service
public class ProjectRecordService extends BaseService
{
    private static Logger LOGGER = Logger.getLogger(ProjectRecordService.class);
    public static int IMPORT_PARTIAL_SUCCESS = 1;
    public static int IMPORT_COMPLETE_SUCCESS = 0;
    public static int IMPORT_FAILURE = -1;

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private ProjectColumnService projectColumnService;

    @Autowired
    private DatabaseAdminRepository databaseAdminRepository;

    @Autowired
    private ProjectCategoryService projectCategoryService;

    @Autowired
    private ProjectTraceService projectTraceService;


    public ProjectRecordDataDTO getProjectRecordsByCategory (Integer projectId, Integer categoryId)
    {
        ProjectRecordDataDTO projectRecordData = null;
        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);

        if (categoryId != null && categoryId > 0)
        {
            ProjectCategory projectCategory = projectCategoryService.getProjectCategoryById(categoryId);
            if (projectCategory == null)
            {
                throw new ServiceException(String.format("Project category (id: %d) does not exist", categoryId));
            }
        }

        List<ProjectColumn> projectColumns = projectColumnService.getProjectColumnsByProjectId(projectInfo.getId());
        if (projectColumns != null && projectColumns.size() > 0)
        {
            projectRecordData = new ProjectRecordDataDTO();
            projectRecordData.setProject(projectInfo);
            projectRecordData.setColumns(projectColumns);

            String dataTableName = projectInfo.getTableName();
            List<Map<String, Object>> rows = databaseAdminRepository.getRecordByCategory(dataTableName, categoryId);
            for (Map<String, Object> row : rows)
            {
                projectRecordData.addRowData(constructRowData(row, projectColumns));
            }
        }
        else
        {
            throw new ServiceException(String.format("No project (name: %s) columns found", projectInfo.getName()));
        }

        return projectRecordData;
    }

    public ProjectRecordDataDTO getProjectRecordById (Integer projectId, Integer recordId)
    {
        ProjectRecordDataDTO projectRecordData = null;
        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
        List<ProjectColumn> projectColumns = projectColumnService.getProjectColumnsByProjectId(projectInfo.getId());
        if (projectColumns != null && projectColumns.size() > 0)
        {
            projectRecordData = new ProjectRecordDataDTO();
            projectRecordData.setProject(projectInfo);
            projectRecordData.setColumns(projectColumns);

            Map<String, Object> row = databaseAdminRepository.getRecordById(projectInfo.getTableName(), recordId);
            if (row != null)
            {
                projectRecordData.addRowData(constructRowData(row, projectColumns));
                return projectRecordData;
            }
            else
            {
                throw new ServiceException(String.format("Project record (id: %d) does not exist", recordId));
            }
        }
        else
        {
            throw new ServiceException(String.format("No project (name: %s) columns found", projectInfo.getName()));
        }
    }

    private Integer truncateProjectRecords (Integer projectId)
    {
        ProjectInfo projectInfo = this.projectInfoService.getProjectById(projectId);
        return this.databaseAdminRepository.empty(projectInfo.getTableName());
    }

    public Integer batchAddProjectRecordsFromExcelFile (Integer projectId, Integer categoryId, String excelFile)
    {
        try
        {
            // truncate project records before importing
            this.truncateProjectRecords(projectId);

            boolean partialSuccess = false;
            ExcelParser parser = new ExcelParser();
            ExcelSheetDataDTO sheetData = parser.parseFile(excelFile);
            if (sheetData != null)
            {
                ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
                ProjectCategory projectCategory = projectCategoryService.getProjectCategoryById(categoryId);
                List<ProjectColumn> projectColumns = this.projectColumnService.getProjectColumnsByProjectId(projectId);
                List<ProjectRecordDataRow> recordDataList = constructProjectRecordData(sheetData, projectColumns);
                for (ProjectRecordDataRow recordData : recordDataList)
                {
                    recordData.setCategoryId(projectCategory.getId());
                    try
                    {
                        this.addProjectRecord(projectInfo.getId(), recordData, false);
                        recordData = null;
                    }
                    catch (Exception ex)
                    {
                        LOGGER.error(String.format("Failed to add project record data (%s) due to %s", recordData, ex));
                        partialSuccess = true;
                    }
                }

                try
                {
                    projectTraceService.addProjectTrace(projectId,
                            EOperationType.OPERATION_IMPORT, getServiceDomain(),
                            String.valueOf(projectId), projectCategory.getName(),
                            "Import project record", null, null);

                    // update lastUpdateTime in project
                    projectInfoService.updateProject(projectId, projectInfo);
                }
                catch (Exception ex)
                {
                    LOGGER.error("Exception raised during tracing and updating project last update time: " + ex);
                }

                return partialSuccess ? IMPORT_PARTIAL_SUCCESS : IMPORT_COMPLETE_SUCCESS;
            }
            else
            {
                LOGGER.error("No data been found from the given upload file");
            }
        }
        catch (Exception ex)
        {
            LOGGER.error("Failed to import project record data:  " + ex.getMessage());
        }

        return IMPORT_FAILURE;
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

    private LinkedHashMap<String, Object> constructRowData (final Map<String, Object> row, final List<ProjectColumn> projectColumns)
    {
        if (row != null && projectColumns != null)
        {
            LinkedHashMap<String, Object> rowData = new LinkedHashMap<String, Object>();
            for (ProjectColumn projectColumn : projectColumns)
            {
                rowData.put(projectColumn.getColumnName(), row.get(projectColumn.getColumnId()));
            }

            rowData.put(ETemplateColumnProperty.TEMPLATE_COLUMN_ID.getColumnName(), getTemplateColumnData(row, ETemplateColumnProperty.TEMPLATE_COLUMN_ID));
            rowData.put(ETemplateColumnProperty.TEMPLATE_COLUMN_CATEGORY_NAME.getColumnName(), getTemplateColumnData(row, ETemplateColumnProperty.TEMPLATE_COLUMN_CATEGORY_NAME));
            rowData.put(ETemplateColumnProperty.TEMPLATE_COLUMN_CREATE_TIME.getColumnName(), getTemplateColumnData(row, ETemplateColumnProperty.TEMPLATE_COLUMN_CREATE_TIME));
            rowData.put(ETemplateColumnProperty.TEMPLATE_COLUMN_UPDATE_TIME.getColumnName(), getTemplateColumnData(row, ETemplateColumnProperty.TEMPLATE_COLUMN_UPDATE_TIME));
            rowData.put(ETemplateColumnProperty.TEMPLATE_COLUMN_OWNER.getColumnName(), getTemplateColumnData(row, ETemplateColumnProperty.TEMPLATE_COLUMN_OWNER));
            rowData.put(ETemplateColumnProperty.TEMPLATE_COLUMN_UPDATE_USER.getColumnName(), getTemplateColumnData(row, ETemplateColumnProperty.TEMPLATE_COLUMN_UPDATE_USER));

            return rowData;
        }

        return null;
    }

    private Object getTemplateColumnData (final Map<String, Object> row, ETemplateColumnProperty templateColumn)
    {
        if (row.containsKey(templateColumn.getColumnId()))
        {
            return row.get(templateColumn.getColumnId());
        }

        return null;
    }

    public Integer deleteProjectRecord (Integer projectId, Integer recordId)
    {
        if (recordId != null && recordId > 0)
        {
            ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
            ProjectRecordDataDTO recordDataDTO = this.getProjectRecordById(projectId, recordId);
            if (recordDataDTO != null && recordDataDTO.getRowData() != null && recordDataDTO.getRowData().size() > 0)
            {
                Integer result = this.databaseAdminRepository.delete(projectInfo.getTableName(), recordId);
                if (result != null && result > 0)
                {
                    try
                    {
                        LinkedHashMap<String, Object> rowEntry = (LinkedHashMap<String, Object>) recordDataDTO.getRowData().get(0);
                        String categoryName = rowEntry.get(ETemplateColumnProperty.TEMPLATE_COLUMN_CATEGORY_NAME.getColumnName()).toString();
                        projectTraceService.addProjectTrace(projectId,
                                EOperationType.OPERATION_DEL, getServiceDomain(),
                                String.valueOf(projectId), categoryName,
                                "Delete project record",
                                recordDataDTO.getRowData(), null);

                        // update lastUpdateTime in project
                        projectInfoService.updateProject(projectId, projectInfo);
                    }
                    catch (Exception ex)
                    {
                        LOGGER.error("Exception raised during tracing and updating project last update time: " + ex);
                    }

                    return result;
                }

                throw new ServiceException("Failed to delete project record: " + recordId);
            }
        }

        throw new ServiceException("Invalid project record id: " + recordId);
    }

    public ProjectRecordDataDTO updateProjectRecord (Integer projectId, Integer recordId, ProjectRecordDataRow recordDataRow)
    {
        if (recordDataRow != null)
        {
            ProjectRecordDataDTO entityById = this.getProjectRecordById(projectId, recordId);
            if (entityById != null)
            {
                // TODO: replace with right user id
                recordDataRow.setLastUpdateUser("ci");
                recordDataRow.setUpdateTime(new Date());

                Map<String, Object> fieldMap = recordDataRow.getUpdatedColumnIdsByNames(entityById.getColumns());
                if (fieldMap != null)
                {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("tableName", entityById.getProject().getTableName());
                    params.put("id", recordId);
                    params.put("fields", fieldMap);

                    Integer result = this.databaseAdminRepository.update(params);
                    if (result != null && result > 0)
                    {
                        try
                        {
                            LinkedHashMap<String, Object> rowEntry = (LinkedHashMap<String, Object>) entityById.getRowData().get(0);
                            String categoryName = rowEntry.get(ETemplateColumnProperty.TEMPLATE_COLUMN_CATEGORY_NAME.getColumnName()).toString();

                            projectTraceService.addProjectTrace(projectId,
                                    EOperationType.OPERATION_UPDATE, getServiceDomain(),
                                    String.valueOf(projectId), categoryName,
                                    "Update project record",
                                    entityById, recordDataRow);

                            // update lastUpdateTime in project
                            projectInfoService.updateProject(projectId, projectInfoService.getProjectById(projectId));
                        }
                        catch (Exception ex)
                        {
                            LOGGER.error("Exception raised during tracing and updating project last update time: " + ex);
                        }

                        return this.getProjectRecordById(projectId, recordId);
                    }
                    else
                    {
                        throw new ServiceException("Update project record failed: " + recordDataRow);
                    }
                }
                else
                {
                    throw new ServiceException("No properties set in project record data: " + recordDataRow);
                }
            }
            else
            {
                throw new ServiceException(String.format("Project record (id: %d) does not exist", recordId));
            }
        }
        else
        {
            throw new ServiceException("Invalid project record: " + recordDataRow);
        }
    }

    public Integer addProjectRecord (Integer projectId, ProjectRecordDataRow projectData)
    {
        return addProjectRecord(projectId, projectData, true);
    }

    public Integer addProjectRecord (Integer projectId, ProjectRecordDataRow projectData, boolean trace)
    {
        if (projectData != null)
        {
            ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
            ProjectCategory projectCategory = projectCategoryService.getProjectCategoryById(projectData.getCategoryId());
            List<ProjectColumn> projectColumns = projectColumnService.getProjectColumnsByProjectId(projectInfo.getId());
            if (projectColumns != null && projectColumns.size() > 0)
            {
                projectData.setCreationTime(new Date());
                projectData.setUpdateTime(new Date());

                // TODO replace with valid user
                projectData.setOwner("Change It");
                projectData.setLastUpdateUser("Change It");

                Integer result = this.databaseAdminRepository.insertByProps(projectInfo.getTableName(),
                        projectData.getInsertedColumnIdsByNames(projectColumns), projectData.getInsertedColumnValues());

                if (result != null && result > 0)
                {
                    try
                    {
                        if (trace)
                        {
                            projectTraceService.addProjectTrace(projectId,
                                    EOperationType.OPERATION_ADD, getServiceDomain(),
                                    String.valueOf(projectId), projectCategory.getName(),
                                    "Add project record",
                                    null, projectData);
                        }

                        // update the lastUpdateTime in project
                        projectInfoService.updateProject(projectId, projectInfo);

                    }
                    catch (Exception ex)
                    {
                        LOGGER.error("Exception raised during tracing and updating project last update time: " + ex);
                    }

                    return result;
                }

                throw new ServiceException("Failed to add project record: " + projectData);
            }
            else
            {
                throw new ServiceException(String.format("Project (name: %s) does not have any columns in place", projectInfo.getName()));
            }
        }

        throw new ServiceException("Invalid project record data: " + projectData);
    }

    protected String getServiceCategory ()
    {
        return null;
    }

    protected String getServiceDomain ()
    {
        return EServiceDomain.DOMAIN_PROJECT_RECORDS.getLabel();
    }
}
