package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.constants.EServiceDomain;
import com.nokia.ucms.biz.constants.ETemplateColumn;
import com.nokia.ucms.biz.dto.ProjectRecordDataDTO;
import com.nokia.ucms.biz.entity.ProjectCategory;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
import com.nokia.ucms.biz.repository.ProjectCategoryRepository;
import com.nokia.ucms.biz.repository.ProjectColumnRepository;
import com.nokia.ucms.biz.repository.ProjectInfoRepository;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.nokia.ucms.biz.constants.Constants.*;
import static com.nokia.ucms.biz.dto.ProjectRecordDataDTO.*;

/**
 * Created by x36zhao on 2017/3/11.
 */
@Service
public class ProjectRecordService extends BaseService
{
    @Autowired
    private ProjectInfoRepository projectInfoRepository;

    @Autowired
    private ProjectColumnRepository projectColumnRepository;

    @Autowired
    private DatabaseAdminRepository databaseAdminRepository;

    @Autowired
    private ProjectCategoryRepository projectCategoryRepository;

    public ProjectRecordDataDTO getProjectRecords (Integer projectId, Integer categoryId)
    {
        ProjectRecordDataDTO projectRecordData = null;
        ProjectInfo projectInfo = projectInfoRepository.getProjectInfoById(projectId);
        if (projectInfo != null)
        {
            projectRecordData = new ProjectRecordDataDTO();
            projectRecordData.setProject(projectInfo);
            projectRecordData.setColumns(projectColumnRepository.getColumnsByProjectId(projectInfo.getId()));

            List<ProjectColumn> projectColumns = projectColumnRepository.getColumnsByProjectId(projectInfo.getId());
            if (projectColumns != null && projectColumns.size() > 0)
            {
                String dataTableName = projectInfo.getTableName();
                List<Map<String, Object>> rows = databaseAdminRepository.query(dataTableName, categoryId);
                for (Map<String, Object> row : rows)
                {
                    projectRecordData.addRowData(constructRowData(row, projectColumns));
                }
            }
        }

        return projectRecordData;
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

            rowData.put(ETemplateColumn.TEMPLATE_COLUMN_ID.getColumnName(), getTemplateColumnData(row, ETemplateColumn.TEMPLATE_COLUMN_ID));
            rowData.put(ETemplateColumn.TEMPLATE_COLUMN_CATEGORY_NAME.getColumnName(), getTemplateColumnData(row, ETemplateColumn.TEMPLATE_COLUMN_CATEGORY_NAME));
            rowData.put(ETemplateColumn.TEMPLATE_COLUMN_CREATE_TIME.getColumnName(), getTemplateColumnData(row, ETemplateColumn.TEMPLATE_COLUMN_CREATE_TIME));
            rowData.put(ETemplateColumn.TEMPLATE_COLUMN_UPDATE_TIME.getColumnName(), getTemplateColumnData(row, ETemplateColumn.TEMPLATE_COLUMN_UPDATE_TIME));
            rowData.put(ETemplateColumn.TEMPLATE_COLUMN_OWNER.getColumnName(), getTemplateColumnData(row, ETemplateColumn.TEMPLATE_COLUMN_OWNER));
            rowData.put(ETemplateColumn.TEMPLATE_COLUMN_UPDATE_USER.getColumnName(), getTemplateColumnData(row, ETemplateColumn.TEMPLATE_COLUMN_UPDATE_USER));

            return rowData;
        }

        return null;
    }

    private Object getTemplateColumnData (final Map<String, Object> row, ETemplateColumn templateColumn)
    {
        if (row.containsKey(templateColumn.getColumnId()))
        {
            return row.get(templateColumn.getColumnId());
        }

        return null;
    }

    public Integer addProjectRecord (Integer projectId, ProjectRecordDataRow projectData)
    {
        ProjectInfo projectInfo = projectInfoRepository.getProjectInfoById(projectId);
        if (projectInfo != null)
        {
            String projectDataTableName = projectInfo.getTableName();
            if (projectDataTableName != null && projectData != null)
            {
                if (projectData.getCategoryId() != null)
                {
                    ProjectCategory projectCategory = projectCategoryRepository.getCategoryById(projectData.getCategoryId());
                    if (projectCategory == null)
                    {
                        throw new ServiceException(String.format("Project category (id: %d) does not exist", projectCategory.getId()));
                    }
                }

                List<ProjectColumn> projectColumns = projectColumnRepository.getColumnsByProjectId(projectInfo.getId());
                if (projectColumns != null && projectColumns.size() > 0)
                {
                    projectData.setCreationTime(new Date());
                    projectData.setUpdateTime(new Date());
                    projectData.setCategoryName(null);

                    // TODO replace with valid user
                    projectData.setOwner("Change It");
                    projectData.setLastUpdateUser("Change It");

                    Integer result = this.databaseAdminRepository.insertByProps(projectDataTableName,
                            projectData.getInsertedColumnIdsByNames(projectColumns), projectData.getInsertedColumnValues());

                    if (result != null && result > 0)
                    {
                        return result;
                    }

                    return null;
                }
                else
                {
                    throw new ServiceException(String.format("Project (name: %s) does not have any columns in place", projectInfo.getName()));
                }
            }
            else
            {
                throw new ServiceException(String.format("Project (%s) data table does not exit", projectInfo));
            }
        }
        else
        {
            throw new ServiceException(String.format("Project (id: %d) does not exit", projectId));
        }
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