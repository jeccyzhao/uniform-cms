package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.dto.ProjectDataTableDTO;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
import com.nokia.ucms.biz.repository.ProjectCategoryRepository;
import com.nokia.ucms.biz.repository.ProjectColumnRepository;
import com.nokia.ucms.biz.repository.ProjectInfoRepository;
import com.nokia.ucms.common.entity.KeyValueEntityPair;
import com.nokia.ucms.common.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nokia.ucms.biz.constants.Constants.*;
import static com.nokia.ucms.biz.dto.ProjectDataTableDTO.*;

import java.util.*;

/**
 * Created by x36zhao on 2017/3/5.
 */
@Service
public class ProjectService
{
    private static Logger LOGGER = Logger.getLogger(ProjectService.class);

    @Autowired
    private DatabaseAdminRepository dbAdminRepository;

    @Autowired
    private ProjectInfoRepository projectInfoRepository;

    @Autowired
    private ProjectColumnRepository projectColumnRepository;

    @Autowired
    private ProjectCategoryRepository projectCategoryRepository;

    public List<ProjectInfo> getProject(String projectName) throws ServiceException
    {
        if (projectName != null && !"".equals(projectName))
        {
            ProjectInfo projectInfo = projectInfoRepository.getProjectInfoByName(projectName);
            return Arrays.asList(projectInfo);
        }
        else
        {
            return projectInfoRepository.getAllProject();
        }
    }

    public ProjectInfo getProjectById (Integer projectId) throws ServiceException
    {
        ProjectInfo projectInfo = projectInfoRepository.getProjectInfoById(projectId);
        if (projectInfo != null)
        {
            return projectInfo;
        }

        throw new ServiceException(String.format("Project '%d' does not exist", projectId));
    }

    public boolean updateProject (ProjectInfo projectInfo) throws ServiceException
    {
        ProjectInfo entity = projectInfoRepository.getProjectInfoById(projectInfo.getId());
        if (entity != null)
        {
            Integer result = projectInfoRepository.updateProjectInfo(projectInfo);
            return result > 0;
        }

        throw new ServiceException(String.format("Project '%s' does not exist", projectInfo));
    }

    @Transactional
    public Integer createProject (ProjectInfo projectInfo, Integer fromProject) throws ServiceException
    {
        // Steps
        // 1. add project info --> generate unique table name for project
        // 2. create project data table
        // 3. clone from other project if applicable
        if (projectInfo != null)
        {
            String projectName = projectInfo.getName();
            ProjectInfo project = projectInfoRepository.getProjectInfoByName(projectName);
            if (project != null)
            {
                throw new ServiceException(String.format("Project '%s' already exists", projectName));
            }

            projectInfo.setTableName(makeProjectDataTableName(projectName));
            projectInfo.setCreationTime(new Date());
            projectInfo.setLastUpdateTime(projectInfo.getCreationTime());
            Integer result = projectInfoRepository.addProjectInfo(projectInfo);
            if (result > 0)
            {
                dbAdminRepository.createTableIfNotExist(projectInfo.getTableName());

                if (fromProject != null)
                {
                    cloneProject(fromProject, projectInfo);
                }

                return projectInfo.getId();
            }

            return null;
        }

        throw new ServiceException("Empty project cannot be created");
    }


    @Transactional
    public Integer createProjectColumn (ProjectColumn projectColumn) throws ServiceException
    {
        if (projectColumn != null)
        {
            // Steps
            // 1. generate column field id
            // 2. insert column entry in `t_project_column`
            // 3. create column filed in the data table of project
            ProjectInfo projectInfo = projectInfoRepository.getProjectInfoById(projectColumn.getProjectId());
            if (projectInfo != null)
            {
                String columnName = projectColumn.getColumnName();
                ProjectColumn entity = projectColumnRepository.getColumnByName(columnName, projectInfo.getId());
                if (entity == null)
                {
                    projectColumn.setColumnId(makeProjectColumnFieldId(columnName));
                    Integer result = projectColumnRepository.addProjectColumn(projectColumn);
                    if (result > 0)
                    {
                        dbAdminRepository.addTableColumn(projectInfo.getTableName(),
                                projectColumn.getColumnId(), projectColumn.getColumnLength());

                        return projectColumn.getId();
                    }
                }
                else
                {
                    throw new ServiceException(String.format("Project column '%s' already exists", entity.getColumnName()));
                }
            }
        }

        throw new ServiceException("Empty project column cannot be created");
    }

    public ProjectDataTableDTO getProjectData(Integer projectId, Integer categoryId)
    {
        ProjectDataTableDTO projectDataTable = null;
        ProjectInfo projectInfo = projectInfoRepository.getProjectInfoById(projectId);
        if (projectInfo != null)
        {
            projectDataTable = new ProjectDataTableDTO();
            projectDataTable.setProject(projectInfo);

            List<ProjectColumn> projectColumns = projectColumnRepository.getColumnsByProjectId(projectInfo.getId());
            if (projectColumns != null && projectColumns.size() > 0)
            {
                String dataTableName = projectInfo.getTableName();
                List<Map<String, Object>> dataRows = dbAdminRepository.query(dataTableName, categoryId);
                for (Map<String, Object> dataRow : dataRows)
                {
                    projectDataTable.addRow(constructDataRowDTO(dataRow, projectColumns));
                }
            }
        }

        return projectDataTable;
    }

    private ProjectDataTableRow constructDataRowDTO (
            final Map<String, Object> row, final List<ProjectColumn> columns)
    {
        ProjectDataTableRow dataRowDTO = null;
        if (row != null && row.size() > 0)
        {
            dataRowDTO = new ProjectDataTableRow();
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
                        dataRowDTO.getCells().add(new ProjectColumnProperty(
                                column.getColumnId(), column.getColumnName(), entry.getValue().toString()));
                        break;
                    }
                }
            }
        }

        return dataRowDTO;
    }


    /**
     * Clone project from another
     *
     * In Scope:
     * 1. Project columns
     * 2. Project table fields
     * 3. Project grants
     *
     * @param fromProject
     * @param targetProject
     * @return
     */
    private boolean cloneProject(Integer fromProject, ProjectInfo targetProject)
    {
        if (fromProject != null && targetProject != null)
        {
            // clone happens only from different project
            if (!fromProject.equals(targetProject.getId()))
            {
                ProjectInfo sourceProject = projectInfoRepository.getProjectInfoById(fromProject);
                if (sourceProject != null)
                {
                    System.out.println(String.format(
                            "Clone project '%s' from '%s'", targetProject.getName(), sourceProject.getName()));

                    // TODO
                    // 1. clone columns from source project
                    // 2. clone table fields from source project
                }
                else
                {
                    throw new ServiceException(String.format("Source project (id: '%d') does not exist", fromProject));
                }
            }
            else
            {
                throw new ServiceException("Project clone is allowed only from another, rather than itself");
            }
        }

        throw new ServiceException("Failed to clone cause of missing source or target project");
    }

    private String makeProjectDataTableName(String projectName)
    {
        return String.format("%s%s", DYNAMIC_TABLE_NAME_PREFIX,
                projectName.trim().replaceAll(" ", KEYWORD_SPLITTER).toLowerCase());
    }

    private String makeProjectColumnFieldId(String columnName)
    {
        return String.format("%s%d", DYNAMIC_COLUMN_NAME_PREFIX, columnName.trim().hashCode());
    }
}
