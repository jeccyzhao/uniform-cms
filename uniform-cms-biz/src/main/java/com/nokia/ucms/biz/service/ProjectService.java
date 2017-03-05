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

    public boolean createProject (ProjectInfo projectInfo) throws ServiceException
    {
        // Steps
        // 1. add project info --> generate unique table name for project
        // 2. create project data table
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
                return true;
            }

            return false;
        }

        throw new ServiceException("Null project info given for creation");
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
