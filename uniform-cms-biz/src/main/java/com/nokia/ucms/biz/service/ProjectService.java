package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.dto.TableColumnDTO;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
import com.nokia.ucms.biz.repository.ProjectColumnRepository;
import com.nokia.ucms.biz.repository.ProjectInfoRepository;
import com.nokia.ucms.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.nokia.ucms.biz.constants.Constants.*;

import java.util.Map;

/**
 * Created by x36zhao on 2017/3/5.
 */
@Service
public class ProjectService
{
    @Autowired
    private DatabaseAdminRepository dbAdminRepository;

    @Autowired
    private ProjectInfoRepository projectInfoRepository;

    @Autowired
    private ProjectColumnRepository projectColumnRepository;

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
                throw new ServiceException(String.format("ProjectInfo '%s' already exists", projectName));
            }

            String projectTableName = makeProjectDataTableName(projectName);
            projectInfo.setTableName(projectTableName);
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

        throw new ServiceException("Project without any properties cannot be created");
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

        return false;
    }

    public Integer createProjectColumn (ProjectColumn projectColumn)
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
                projectColumn.setColumnId(makeProjectColumnFieldId(columnName));
                Integer result = projectColumnRepository.addProjectColumn(projectColumn);
                if (result > 0)
                {
                    TableColumnDTO tableColumnDTO = new TableColumnDTO();
                    //dbAdminRepository.addTableColumn
                }
            }
            //return dbAdminRepository.addTableColumn(tableColumnDTO.getTableName(),
            //        tableColumnDTO.getColumnName(), tableColumnDTO.getColumnLength());
        }

        return null;
    }

    public Map<?, ?> getProjectData(String projectName)
    {
        return dbAdminRepository.query(projectName);
    }

    private String makeProjectDataTableName(String projectName)
    {
        return projectName.trim().replaceAll(" ", KEYWORD_SPLITTER);
    }

    private String makeProjectColumnFieldId(String columnName)
    {
        return columnName.trim().replaceAll(" ", KEYWORD_SPLITTER);
    }

}
