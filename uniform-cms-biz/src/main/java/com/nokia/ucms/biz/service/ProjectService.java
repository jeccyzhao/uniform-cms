package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
import com.nokia.ucms.biz.repository.ProjectColumnRepository;
import com.nokia.ucms.biz.repository.ProjectInfoRepository;
import com.nokia.ucms.common.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nokia.ucms.biz.constants.Constants.*;

import java.util.Map;

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

    @Transactional
    public boolean createProject (ProjectInfo projectInfo, Integer fromProject) throws ServiceException
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
            Integer result = projectInfoRepository.addProjectInfo(projectInfo);
            if (result > 0)
            {
                dbAdminRepository.createTableIfNotExist(projectInfo.getTableName());

                if (fromProject != null)
                {
                    return cloneProject(fromProject, projectInfo);
                }

                return true;
            }

            return false;
        }

        throw new ServiceException("Empty project cannot be created");
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

    @Transactional
    public Integer createProjectColumn (Integer projectId, ProjectColumn projectColumn)
    {
        if (projectColumn != null)
        {
            // Steps
            // 1. generate column field id
            // 2. insert column entry in `t_project_column`
            // 3. create column filed in the data table of project
            ProjectInfo projectInfo = projectInfoRepository.getProjectInfoById(projectId);
            if (projectInfo != null)
            {
                String columnName = projectColumn.getColumnName();
                ProjectColumn entity = projectColumnRepository.getColumnByName(columnName, projectInfo.getId());
                if (entity == null)
                {
                    projectColumn.setProjectId(projectId);
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

    public Map<?, ?> getProjectData(String projectName)
    {
        return dbAdminRepository.query(projectName);
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
