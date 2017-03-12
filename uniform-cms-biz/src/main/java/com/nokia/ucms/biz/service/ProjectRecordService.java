package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.constants.EServiceDomain;
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
import static com.nokia.ucms.biz.constants.Constants.TEMPLATE_COLUMN_UPDATE_TIME;
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
                List<Map<String, Object>> dataRows = databaseAdminRepository.query(dataTableName, categoryId);
                for (Map<String, Object> dataRow : dataRows)
                {
                    projectRecordData.addRow(constructDataRowDTO(dataRow, projectColumns));
                }
            }
        }

        return projectRecordData;
    }

    public ProjectRecordDataRow addProjectRecord (Integer projectId, ProjectRecordDataRow projectData)
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

                projectData.setCreationTime(new Date());
                projectData.setLastUpdateTime(new Date());
                projectData.setCategoryName(null);

                // TODO replace with valid user
                projectData.setOwner("Change It");
                projectData.setLastUpdateUser("Change It");

                Map<String, String> insertMapParams = new HashMap<String, String>();
                insertMapParams.put("id", null);
                insertMapParams.put("tableName", projectDataTableName);
                insertMapParams.put("columnIds", projectData.getInsertedColumnIds());
                insertMapParams.put("columnValues", projectData.getInsertedColumnValues());

                Integer result = this.databaseAdminRepository.insertByProps(projectDataTableName, projectData.getInsertedColumnIds(), projectData.getInsertedColumnValues());
                if (result != null && result > 0)
                {
                    projectData.setId(result);
                }

                return projectData;
            }
        }
        return null;
    }

    private ProjectRecordDataRow constructDataRowDTO (
            final Map<String, Object> row, final List<ProjectColumn> columns)
    {
        ProjectRecordDataRow dataRowDTO = null;
        if (row != null && row.size() > 0)
        {
            dataRowDTO = new ProjectRecordDataRow();
            dataRowDTO.setId(row.containsKey(TEMPLATE_COLUMN_ID) ? Integer.valueOf(row.get(TEMPLATE_COLUMN_ID).toString()) : null);
            dataRowDTO.setCategoryName(row.containsKey(TEMPLATE_COLUMN_CATEGORY_NAME) ? row.get(TEMPLATE_COLUMN_CATEGORY_NAME).toString() : null);
            dataRowDTO.setCreationTime(row.containsKey(TEMPLATE_COLUMN_CREATE_TIME) ? (Date) row.get(TEMPLATE_COLUMN_CREATE_TIME) : null);
            dataRowDTO.setLastUpdateTime(row.containsKey(TEMPLATE_COLUMN_UPDATE_TIME) ? (Date) row.get(TEMPLATE_COLUMN_UPDATE_TIME) : null);
            dataRowDTO.setCells(new ArrayList());
            for (Map.Entry<String, Object> entry : row.entrySet())
            {
                for (ProjectColumn column : columns)
                {
                    if (entry.getKey().equals(column.getColumnId()))
                    {
                        dataRowDTO.getCells().add(new ProjectRecordDataDTO.ProjectColumnProperty(
                                column.getColumnId(), column.getColumnName(), entry.getValue().toString()));
                        break;
                    }
                }
            }
        }

        return dataRowDTO;
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
