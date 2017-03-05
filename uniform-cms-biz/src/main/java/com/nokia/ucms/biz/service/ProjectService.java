package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.dto.TableColumnDTO;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
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
    private ProjectInfoRepository projectRepository;

    public boolean createProject (ProjectInfo projectInfo) throws ServiceException
    {
        // Steps
        // 1. add project info --> generate unique table name for project
        // 2. create project data table
        if (projectInfo != null)
        {
            String projectName = projectInfo.getName();
            ProjectInfo project = projectRepository.getProjectInfoByName(projectName);
            if (project != null)
            {
                throw new ServiceException(String.format("ProjectInfo '%s' already exists", projectName));
            }

            String projectTableName = generateProjectDataTableName(projectName);
            projectInfo.setTableName(projectTableName);
            Integer result = projectRepository.addProjectInfo(projectInfo);
            if (result > 0)
            {
                dbAdminRepository.createTableIfNotExist(projectInfo.getTableName());
                return true;
            }

            return false;
        }

        throw new ServiceException("Null project info given for creation");
    }

    public Integer addProjectField (final TableColumnDTO tableColumnDTO)
    {
        if (tableColumnDTO != null)
        {
            return dbAdminRepository.addTableColumn(tableColumnDTO.getTableName(),
                    tableColumnDTO.getColumnName(), tableColumnDTO.getColumnLength());
        }

        return null;
    }

    public Map<?, ?> getProjectData(String projectName)
    {
        return dbAdminRepository.query(projectName);
    }

    private String generateProjectDataTableName(String projectName)
    {
        return projectName.trim().replaceAll(" ", KEYWORD_SPLITTER);
    }
}
