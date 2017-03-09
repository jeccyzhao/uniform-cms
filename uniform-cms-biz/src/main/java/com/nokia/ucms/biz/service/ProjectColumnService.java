package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.constants.EOperationDomain;
import com.nokia.ucms.biz.constants.EOperationType;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
import com.nokia.ucms.biz.repository.ProjectColumnRepository;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.nokia.ucms.biz.constants.Constants.DYNAMIC_COLUMN_NAME_PREFIX;

/**
 * Created by x36zhao on 2017/3/9.
 */
@Service
public class ProjectColumnService extends BaseService
{
    private static Logger LOGGER = Logger.getLogger(ProjectColumnService.class);

    @Autowired
    private ProjectColumnRepository projectColumnRepository;

    @Autowired
    private DatabaseAdminRepository dbAdminRepository;

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private ProjectTraceService projectTraceService;

    public List<ProjectColumn> getProjectColumnsByProjectName (String projectName) throws ServiceException
    {
        ProjectInfo projectInfo = projectInfoService.getProjectByName(projectName);
        if (projectInfo != null)
        {
            return projectColumnRepository.getColumnsByProjectName(projectName);
        }

        throw new ServiceException("Failed to get project column by project name: " + projectName);
    }

    public List<ProjectColumn> getProjectColumnsByName (String columnName) throws ServiceException
    {
        return null;
    }

    public ProjectColumn getProjectColumnById (Integer projectColumnId) throws ServiceException
    {
        if (projectColumnId != null && projectColumnId > 0)
        {
            ProjectColumn projectColumn = projectColumnRepository.getColumnById(projectColumnId);
            if (projectColumn != null)
            {
                return projectColumn;
            }

            throw new ServiceException(String.format("Project column (id: '%d') does not exist", projectColumnId));
        }

        throw new ServiceException("Invalid project column id: " + projectColumnId);
    }

    public List<ProjectColumn> getProjectColumnsByProjectId (Integer projectId) throws ServiceException
    {
        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
        if (projectInfo != null)
        {
            return projectColumnRepository.getColumnsByProjectId(projectId);
        }

        throw new ServiceException("Failed to get project column by project id: " + projectId);
    }
    @Transactional
    public Integer removeProjectColumn (Integer projectColumnId) throws ServiceException
    {
        ProjectColumn projectColumn = getProjectColumnById(projectColumnId);
        if (projectColumn != null)
        {
            ProjectInfo projectInfo = projectInfoService.getProjectById(projectColumn.getProjectId());
            if (projectInfo != null)
            {
                Integer result = projectColumnRepository.deleteProjectColumn(projectColumnId);
                if (result != null)
                {
                    try
                    {
                        projectTraceService.addProjectTrace(projectInfo.getId(),
                                EOperationType.OPERATION_DEL, EOperationDomain.DOMAIN_PROJECT_COLUMN,
                                String.valueOf(projectColumn.getProjectId()), getModuleCategory(),
                                String.format("Remove project column '%s'", projectColumn.getColumnName()),
                                projectColumn, null);
                    }
                    catch (Exception ex)
                    {
                        LOGGER.error("Failed to trace when removing project column: " + ex);
                    }

                    return dbAdminRepository.removeTableColumn(projectInfo.getTableName(), projectColumn.getColumnId());
                }
            }
        }

        throw new ServiceException(String.format("Failed to remove project column (id: %s)", projectColumnId));
    }

    @Transactional
    public ProjectColumn updateProjectColumn (Integer projectId, ProjectColumn projectColumn) throws ServiceException
    {
        if (projectColumn != null && projectColumn.getId() != null &&
                projectColumn.getColumnName() != null && !"".equals(projectColumn.getColumnName()))
        {
            ProjectColumn entityByName = projectColumnRepository.getColumnsByColumnName(projectColumn.getColumnName(), projectId);
            if (entityByName == null || entityByName.getId().equals(projectColumn.getId()))
            {
                ProjectColumn entityById = projectColumnRepository.getColumnById(projectColumn.getId());
                if (entityById != null)
                {
                    projectColumn.setUpdateTime(new Date());
                    if (projectColumnRepository.updateProjectColumn(projectColumn) > 0)
                    {
                        try
                        {
                            projectTraceService.addProjectTrace(projectId,
                                    EOperationType.OPERATION_UPDATE, EOperationDomain.DOMAIN_PROJECT_COLUMN,
                                    String.valueOf(projectColumn.getProjectId()), getModuleCategory(),
                                    String.format("Update project column from '%s' to '%s'", entityById.getColumnName(), projectColumn.getColumnName()),
                                    entityById, projectColumn);
                        }
                        catch (Exception ex)
                        {
                            LOGGER.error("Failed to trace when updating project column: " + ex);
                        }

                        return projectColumn;
                    }
                    else
                    {
                        throw new ServiceException(String.format("Project column (%s) update failed", projectColumn));
                    }
                }
                else
                {
                    throw new ServiceException(String.format("Project column (%s) does not exist", projectColumn));
                }
            }
            else
            {
                throw new ServiceException(String.format("Conflicted with another column with same name (%s)", entityByName));
            }
        }

        throw new ServiceException("Invalid project column: " + projectColumn);
    }

    @Transactional
    public Integer createProjectColumn (ProjectColumn projectColumn) throws ServiceException
    {
        if (projectColumn != null && projectColumn.getColumnName() != null && !"".equals(projectColumn.getColumnName().trim()))
        {
            // Steps
            // 1. generate column field id
            // 2. insert column entry in `t_project_column`
            // 3. create column filed in the data table of project
            ProjectInfo projectInfo = projectInfoService.getProjectById(projectColumn.getProjectId());
            if (projectInfo != null)
            {
                String columnName = projectColumn.getColumnName();
                ProjectColumn entity = projectColumnRepository.getColumnsByColumnName(columnName, projectInfo.getId());
                if (entity == null)
                {
                    projectColumn.setColumnId(makeProjectColumnFieldId(columnName));
                    projectColumn.setUpdateTime(new Date());
                    Integer result = projectColumnRepository.addProjectColumn(projectColumn);
                    if (result > 0)
                    {
                        dbAdminRepository.addTableColumn(projectInfo.getTableName(),
                                projectColumn.getColumnId(), projectColumn.getColumnLength());
                        try
                        {
                            projectTraceService.addProjectTrace(projectColumn.getProjectId(),
                                    EOperationType.OPERATION_ADD, EOperationDomain.DOMAIN_PROJECT_COLUMN,
                                    String.valueOf(projectColumn.getProjectId()), getModuleCategory(),
                                    String.format("Create project column '%s'", projectColumn.getColumnName()),
                                    null, projectColumn);
                        }
                        catch (Exception ex)
                        {
                            LOGGER.error("Failed to trace when updating project column: " + ex);
                        }

                        return projectColumn.getId();
                    }
                }
                else
                {
                    throw new ServiceException(String.format("Project column '%s' already exists", columnName));
                }
            }
        }

        throw new ServiceException("Project column cannot be created due to missing properties, e.g. column name, length and etc.");
    }

    private String makeProjectColumnFieldId(String columnName)
    {
        return String.format("%s%d", DYNAMIC_COLUMN_NAME_PREFIX, columnName.trim().hashCode());
    }

    protected String getModuleCategory ()
    {
        return "Columns";
    }
}
