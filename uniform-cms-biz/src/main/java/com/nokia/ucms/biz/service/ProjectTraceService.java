package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.constants.EOperationType;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.entity.ProjectTrace;
import com.nokia.ucms.biz.repository.ProjectTraceRepository;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

/**
 * Created by x36zhao on 2017/3/9.
 */
@Service
public class ProjectTraceService extends BaseService
{
    private static Logger LOGGER = Logger.getLogger(ProjectTraceService.class);

    @Autowired
    private ProjectTraceRepository projectTraceRepository;

    @Autowired
    private ProjectInfoService projectInfoService;

    public List<ProjectTrace> getProjectTraces (Integer projectId)
    {
        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
        if (projectInfo != null)
        {
            return projectTraceRepository.getTraceByProjectId(projectId);
        }

        throw new ServiceException(String.format("Invalid project id (%d) or project does not exist.", projectId));
    }

    public ProjectTrace addProjectTrace (
            Integer projectId, EOperationType operationType, String message, Object data)
    {
        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
        if (projectInfo != null)
        {
            ProjectTrace projectTrace = new ProjectTrace();
            projectTrace.setOperationTime(new Date());
            projectTrace.setProjectId(projectId);
            projectTrace.setOperator("Change It");
            projectTrace.setOperationType(operationType.getCode());
            projectTrace.setMessage(message);

            if (data != null)
            {
                projectTrace.setData(data.toString());
            }

            return projectTraceRepository.addProjectTrace(projectTrace);
        }

        throw new ServiceException(String.format("Invalid project id (%d) or project does not exist.", projectId));
    }

    public boolean removeProjectTraceById (Integer projectTraceId)
    {
        ProjectTrace projectTrace = projectTraceRepository.getTraceById(projectTraceId);
        if (projectTrace != null)
        {
            return projectTraceRepository.deleteProjectTraceById(projectTraceId) != null;
        }

        throw new ServiceException(String.format("Invalid projectTraceId id (%d) or project trace does not exist.", projectTraceId));
    }

    public boolean removeProjectTraces (Integer projectId)
    {
        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
        if (projectInfo != null)
        {
            return projectTraceRepository.deleteProjectTrace(projectId) != null;
        }

        throw new ServiceException(String.format("Invalid project id (%d) or project does not exist.", projectId));
    }
}
