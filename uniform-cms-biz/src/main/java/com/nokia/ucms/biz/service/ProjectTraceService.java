package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.constants.EOperationType;
import com.nokia.ucms.biz.constants.EServiceDomain;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.entity.ProjectTrace;
import com.nokia.ucms.biz.entity.User;
import com.nokia.ucms.biz.repository.ProjectTraceRepository;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

import static com.nokia.ucms.biz.constants.Constants.*;

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
        return projectTraceRepository.getTraceByProjectId(projectInfo.getId());
    }

    public ProjectTrace addProjectTrace (
            Integer projectId, EOperationType operationType,
            String serviceDomain, String identifier, String serviceCategory, String message,
            Object oldData, Object newData)
    {
        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
        ProjectTrace projectTrace = new ProjectTrace();
        projectTrace.setEventTime(new Date());
        projectTrace.setProjectId(projectId);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectTrace.setOperator(user.getUserDisplayName());
        projectTrace.setEventType(operationType.getCode());
        projectTrace.setMessage(message);
        projectTrace.setCategory(serviceCategory);
        projectTrace.setIdentifier(identifier);

        if (serviceDomain != null)
        {
            projectTrace.setDomain(serviceDomain);
        }

        if (newData != null)
        {
            projectTrace.setNewData(newData.toString());
        }

        if (oldData != null)
        {
            projectTrace.setOldData(oldData.toString());
        }

        Integer result = projectTraceRepository.addProjectTrace(projectTrace);
        if (result > 0)
        {
            return projectTrace;
        }
        else
        {
            throw new ServiceException(String.format("Failed to add project trace: %s", projectTrace));
        }
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
        return projectTraceRepository.deleteProjectTrace(projectInfo.getId()) != null;
    }

    protected String getServiceCategory ()
    {
        return NOT_AVAILABLE;
    }

    protected String getServiceDomain ()
    {
        return EServiceDomain.DOMAIN_PROJECT_TRACES.getLabel();
    }
}
