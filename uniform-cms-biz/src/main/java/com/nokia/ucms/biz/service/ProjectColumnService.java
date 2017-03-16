package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.constants.EServiceDomain;
import com.nokia.ucms.biz.constants.EOperationType;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
import com.nokia.ucms.biz.repository.ProjectColumnRepository;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.service.BaseService;
import com.nokia.ucms.common.utils.UUIDGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.nokia.ucms.biz.constants.Constants.*;

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

    public List<ProjectColumn> getProjectColumnsByName (Integer projectId, String columnName) throws ServiceException
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
    public Integer removeProjectColumns (Integer projectId) throws ServiceException
    {
        ProjectInfo projectInfo = this.projectInfoService.getProjectById(projectId);
        if (projectInfo != null)
        {
            return this.projectColumnRepository.deleteProjectColumnsByProjectId(projectId);
        }

        return null;
    }

    @Transactional
    public Integer removeProjectColumn (Integer projectColumnId) throws ServiceException
    {
        ProjectColumn projectColumn = getProjectColumnById(projectColumnId);
        if (projectColumn != null)
        {
            ProjectInfo projectInfo = projectInfoService.getProjectById(projectColumn.getProjectId());
            Integer result = projectColumnRepository.deleteProjectColumn(projectColumnId);
            if (result != null)
            {
                try
                {
                    projectTraceService.addProjectTrace(projectInfo.getId(),
                            EOperationType.OPERATION_DEL, getServiceDomain(),
                            String.valueOf(projectColumn.getProjectId()), getServiceCategory(),
                            String.format("Remove project column '%s'", projectColumn.getColumnName()),
                            projectColumn, null);

                    // update lastUpdateTime in project
                    projectInfoService.updateProject(projectInfo.getId(), projectInfo);
                }
                catch (Exception ex)
                {
                    LOGGER.error("Failed to trace when removing project column: " + ex);
                }

                return dbAdminRepository.removeTableColumn(projectInfo.getTableName(), projectColumn.getColumnId());
            }
        }

        throw new ServiceException(String.format("Failed to remove project column (id: %s)", projectColumnId));
    }

    @Transactional
    public ProjectColumn updateProjectColumn (Integer projectId, Integer projectColumnId, ProjectColumn projectColumn) throws ServiceException
    {
        if (projectColumn != null && !"".equals(projectColumn.getColumnName()))
        {
            ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
            ProjectColumn entityById = this.getProjectColumnById(projectColumnId);
            ProjectColumn entityByName = projectColumnRepository.getColumnsByColumnName(projectColumn.getColumnName(), projectId);
            if (entityByName == null || entityByName.getId().equals(entityById.getId()))
            {
                projectColumn.setUpdateTime(new Date());
                projectColumn.setProjectId(projectInfo.getId());
                projectColumn.setId(projectColumnId);
                if (projectColumnRepository.updateProjectColumn(projectColumn) > 0)
                {
                    if (entityById.getColumnLength() != projectColumn.getColumnLength())
                    {
                        // update table column length in case any update happens
                        dbAdminRepository.updateColumnLength(projectInfo.getTableName(),
                                projectColumn.getColumnId(), projectColumn.getColumnLength());
                    }

                    try
                    {
                        projectTraceService.addProjectTrace(projectId,
                                EOperationType.OPERATION_UPDATE, getServiceDomain(),
                                String.valueOf(projectColumn.getProjectId()), getServiceCategory(),
                                String.format("Update project column from '%s' to '%s'", entityById.getColumnName(), projectColumn.getColumnName()),
                                entityById, projectColumn);

                        // update the lastUpdateTime in project
                        projectInfoService.updateProject(projectId, projectInfo);
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
                throw new ServiceException(String.format("Conflicted with another column with same name (%s)", entityByName));
            }
        }
        else
        {
            throw new ServiceException("Invalid project column: " + projectColumn);
        }
    }

    @Transactional
    public Integer createProjectColumn (ProjectColumn projectColumn) throws ServiceException
    {
        if (projectColumn != null && projectColumn.getColumnName() != null && !"".equals(projectColumn.getColumnName().trim()))
        {
            ProjectInfo projectInfo = projectInfoService.getProjectById(projectColumn.getProjectId());
            String columnName = projectColumn.getColumnName();
            ProjectColumn entity = projectColumnRepository.getColumnsByColumnName(columnName, projectInfo.getId());
            if (entity == null)
            {
                projectColumn.setColumnId(makeProjectColumnFieldId());
                projectColumn.setUpdateTime(new Date());
                Integer result = projectColumnRepository.addProjectColumn(projectColumn);
                if (result > 0)
                {
                    dbAdminRepository.addTableColumn(projectInfo.getTableName(),
                            projectColumn.getColumnId(), projectColumn.getColumnLength());
                    try
                    {
                        projectTraceService.addProjectTrace(projectColumn.getProjectId(),
                                EOperationType.OPERATION_ADD, getServiceDomain(),
                                String.valueOf(projectColumn.getProjectId()), getServiceCategory(),
                                String.format("Create project column '%s'", projectColumn.getColumnName()),
                                null, projectColumn);

                        // update the lastUpdateTime in project
                        projectInfoService.updateProject(projectInfo.getId(), projectInfo);
                    }
                    catch (Exception ex)
                    {
                        LOGGER.error("Failed to trace when updating project column: " + ex);
                    }

                    return projectColumn.getId();
                }
                else
                {
                    throw new ServiceException("Failed to add project column: " + projectColumn);
                }
            }
            else
            {
                throw new ServiceException(String.format("Project column '%s' already exists", columnName));
            }
        }

        throw new ServiceException("Project column cannot be created due to missing properties, e.g. column name, length and etc.");
    }

    private String makeProjectColumnFieldId()
    {
        return String.format("%s%s", DYNAMIC_COLUMN_NAME_PREFIX, UUIDGenerator.generateShortUuid());
    }

    protected String getServiceCategory ()
    {
        return NOT_AVAILABLE;
    }

    protected String getServiceDomain ()
    {
        return EServiceDomain.DOMAIN_PROJECT_COLUMN.getLabel();
    }
}
