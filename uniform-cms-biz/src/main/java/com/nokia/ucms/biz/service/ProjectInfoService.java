package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.constants.Constants;
import com.nokia.ucms.biz.constants.EOperationType;
import com.nokia.ucms.biz.constants.EServiceDomain;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.entity.User;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
import com.nokia.ucms.biz.repository.ProjectCategoryRepository;
import com.nokia.ucms.biz.repository.ProjectColumnRepository;
import com.nokia.ucms.biz.repository.ProjectInfoRepository;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nokia.ucms.biz.constants.Constants.*;

import java.util.*;

/**
 * Created by x36zhao on 2017/3/5.
 */
@Service
public class ProjectInfoService extends BaseService
{
    private static Logger LOGGER = Logger.getLogger(ProjectInfoService.class);

    @Autowired
    private DatabaseAdminRepository databaseAdminRepository;

    @Autowired
    private ProjectInfoRepository projectInfoRepository;

    @Autowired
    private ProjectColumnService projectColumnService;

    @Autowired
    private ProjectCategoryService projectCategoryService;

    @Autowired
    private ProjectTraceService projectTraceService;

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
        if (projectId != null && projectId > 0)
        {
            ProjectInfo projectInfo = projectInfoRepository.getProjectInfoById(projectId);
            if (projectInfo != null)
            {
                if (isValidProject(projectInfo))
                {
                    return projectInfo;
                }
                else
                {
                    throw new ServiceException("Invalid project info: " + projectInfo);
                }
            }
            else
            {
                throw new ServiceException(String.format("Project (id: '%d') does not exist", projectId));
            }
        }

        throw new ServiceException("Invalid project id: " + projectId);
    }

    public ProjectInfo getProjectByName (String projectName) throws ServiceException
    {
        if (projectName != null && !"".equals(projectName))
        {
            ProjectInfo projectInfo = projectInfoRepository.getProjectInfoByName(projectName);
            if (projectInfo != null)
            {
                return projectInfo;
            }
            else
            {
                throw new ServiceException(String.format("Project (name: '%s') does not exist", projectName));
            }
        }

        throw new ServiceException("Invalid project name: " + projectName);
    }

    public List<ProjectInfo> getProjectsByOwner (String userId, boolean showAll)
    {
        return projectInfoRepository.getProjectInfoByOwner(userId, showAll ? null : ProjectInfo.P_PUBLIC);
    }

    public ProjectInfo updateProject (Integer projectId, ProjectInfo projectInfo, boolean trace) throws ServiceException
    {
        if (projectInfo != null && !"".equals(projectInfo.getName()))
        {
            ProjectInfo entityById = this.getProjectById(projectId);
            if (entityById != null)
            {
                ProjectInfo entityByName = this.projectInfoRepository.getProjectInfoByName(projectInfo.getName());
                if (entityByName == null || entityById.getId().equals(entityByName.getId()))
                {
                    projectInfo.setLastUpdateTime(new Date());
                    projectInfo.setOwner(entityById.getOwner());
                    projectInfo.setCreationTime(entityById.getCreationTime());

                    Integer result = projectInfoRepository.updateProjectInfo(projectInfo);
                    if (result != null && result > 0)
                    {
                        // trace flag added to avoid huge number of user cases to update project time
                        if (trace)
                        {
                            try
                            {
                                projectTraceService.addProjectTrace(projectInfo.getId(),
                                        EOperationType.OPERATION_UPDATE, getServiceDomain(),
                                        String.valueOf(projectInfo.getId()), getServiceCategory(),
                                        String.format("Update project from '%s' to '%s'", entityById.getName(), projectInfo.getName()),
                                        entityById, projectInfo);
                            }
                            catch (Exception ex)
                            {
                                LOGGER.error("Failed to trace when updating project: " + ex);
                            }
                        }

                        return projectInfo;
                    }

                    throw new ServiceException("Failed to update project: " + projectInfo);
                }
                else
                {
                    throw new ServiceException(String.format("Conflicted with another project with same name (%s)", entityByName));
                }
            }

            throw new ServiceException(String.format("Project '%s' does not exist", projectInfo));
        }

        throw new ServiceException("Invalid project info: " + projectInfo);
    }

    @Transactional
    public Integer removeProject (Integer projectId)
    {
        Integer result = null;
        ProjectInfo projectInfo = this.getProjectById(projectId);
        if (projectInfo != null)
        {
            try
            {
                // remove project columns
                this.projectColumnService.removeProjectColumns(projectId);

                // remove project categories
                this.projectCategoryService.removeProjectCategoriesByProjectId(projectId);

                // remove project data table
                this.databaseAdminRepository.deleteTable(projectInfo.getTableName());

                // remove project traces
                this.projectTraceService.removeProjectTraces(projectInfo.getId());

                // remove project info
                result = this.projectInfoRepository.removeProjectInfo(projectId);

                projectTraceService.addProjectTrace(projectInfo.getId(),
                        EOperationType.OPERATION_DEL, getServiceDomain(),
                        String.valueOf(projectInfo.getId()), getServiceCategory(),
                        String.format("Remove project '%s'", projectInfo.getName()),
                        projectInfo, null);

                projectInfo = null;
            }
            catch (Exception ex)
            {
                LOGGER.error(String.format("Failed to remove project (id: %d) due to (%s)", projectId, ex));
            }

            return result;
        }

        return null;
    }

    @Transactional
    public Integer addProject (ProjectInfo projectInfo, Integer fromProject) throws ServiceException
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

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            projectInfo.setOwner(user);
            projectInfo.setTableName(makeProjectDataTableName(projectName));
            projectInfo.setCreationTime(new Date());
            projectInfo.setLastUpdateTime(projectInfo.getCreationTime());
            Integer result = projectInfoRepository.addProjectInfo(projectInfo);
            if (result > 0)
            {
                databaseAdminRepository.createTableIfNotExist(projectInfo.getTableName());

                try
                {
                    projectTraceService.addProjectTrace(projectInfo.getId(),
                            EOperationType.OPERATION_ADD, getServiceDomain(),
                            String.valueOf(projectInfo.getId()), getServiceCategory(),
                            String.format("Create new project with name '%s'", projectInfo.getName()),
                            null, projectInfo);
                }
                catch (Exception ex)
                {
                    LOGGER.error("Failed to trace when creating project: " + ex);
                }

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

    private boolean isValidProject (final ProjectInfo projectInfo)
    {
        if (projectInfo != null)
        {
            return !"".equals(projectInfo.getName()) && !"".equals(projectInfo.getTableName());
        }

        return false;
    }

    protected String getServiceCategory ()
    {
        return NOT_AVAILABLE;
    }

    protected String getServiceDomain ()
    {
        return EServiceDomain.DOMAIN_PROJECT_INFO.getLabel();
    }
}
