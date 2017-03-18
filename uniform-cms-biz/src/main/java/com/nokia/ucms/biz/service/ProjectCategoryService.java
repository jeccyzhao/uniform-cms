package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.constants.EOperationType;
import com.nokia.ucms.biz.constants.EServiceDomain;
import com.nokia.ucms.biz.entity.ProjectCategory;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.entity.User;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
import com.nokia.ucms.biz.repository.ProjectCategoryRepository;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import static com.nokia.ucms.biz.constants.Constants.*;

import java.util.Date;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/9.
 */
@Service
public class ProjectCategoryService extends BaseService
{
    private static Logger LOGGER = Logger.getLogger(ProjectCategoryService.class);

    @Autowired
    private ProjectCategoryRepository projectCategoryRepository;

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private DatabaseAdminRepository databaseAdminRepository;

    @Autowired
    private ProjectTraceService projectTraceService;

    public ProjectCategory getProjectCategoryById (Integer categoryId)
    {
        if (categoryId != null && categoryId > 0)
        {
            ProjectCategory category = projectCategoryRepository.getCategoryById(categoryId);
            if (category != null)
            {
                return category;
            }

            throw new ServiceException(String.format("Project category (id: %d) does not exist", categoryId));
        }

        throw new ServiceException("Invalid project category id: " + categoryId);
    }

    public ProjectCategory getProjectCategoryByName (Integer projectId, String categoryName)
    {
        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
        if (projectInfo != null)
        {
            return projectCategoryRepository.getCategoryByName(projectId, categoryName);
        }

        throw new ServiceException("Failed to get project categories by project id: " + projectId);
    }

    public List<ProjectCategory> getProjectCategories (Integer projectId)
    {
        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
        if (projectInfo != null)
        {
            return projectCategoryRepository.getCategoriesByProjectId(projectId);
        }

        throw new ServiceException("Failed to get project categories by project id: " + projectId);
    }

    public List<ProjectCategory> getProjectCategories (String projectName)
    {
        ProjectInfo projectInfo = projectInfoService.getProjectByName(projectName);
        if (projectInfo != null)
        {
            return projectCategoryRepository.getCategoriesByProjectId(projectInfo.getId());
        }

        throw new ServiceException("Failed to get project categories by project name: " + projectName);
    }

    public ProjectCategory addProjectCategory (Integer projectId, ProjectCategory category)
    {
        ProjectInfo projectInfo = this.projectInfoService.getProjectById(projectId);
        if (projectInfo != null)
        {
            if (category != null)
            {
                category.setProjectId(projectId);
                category.setCreationTime(new Date());
                category.setUpdateTime(category.getCreationTime());

                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                category.setOwner(user.getUserDisplayName());

                Integer result = projectCategoryRepository.addCategory(category);
                if (result != null)
                {
                    try
                    {
                        projectTraceService.addProjectTrace(projectId,
                                EOperationType.OPERATION_ADD, getServiceDomain(),
                                String.valueOf(category.getId()), getServiceCategory(),
                                String.format("Add project category '%s'", category.getName()),
                                null, category);

                        // update lastUpdateTime in project
                        projectInfoService.updateProject(projectId, projectInfo);
                    }
                    catch (Exception ex)
                    {
                        LOGGER.error("Failed to trace when adding project category: " + ex);
                    }

                    // update the lastUpdateTime in project
                    projectInfoService.updateProject(projectId, projectInfo);

                    return category;
                }

                throw new ServiceException("Failed to add project category: " + category);
            }
            else
            {
                throw new ServiceException("Invalid category: " + category);
            }
        }

        throw new ServiceException(String.format("Project (id: %d) does not exist", projectId));
    }

    public ProjectCategory updateProjectCategory (Integer projectId, Integer categoryId, ProjectCategory projectCategory)
    {
        if (projectCategory != null && !"".equals(projectCategory.getName()))
        {
            ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
            ProjectCategory entityById = this.getProjectCategoryById(categoryId);
            ProjectCategory entityByName = projectCategoryRepository.getCategoryByName(projectId, projectCategory.getName());
            if (entityByName == null || entityByName.getId().equals(entityById.getId()))
            {
                projectCategory.setId(categoryId);
                projectCategory.setProjectId(projectId);
                projectCategory.setUpdateTime(new Date());

                // adding owner and creation time back only for the purpose of displaying in front-end page
                projectCategory.setOwner(entityById.getOwner());

                Integer result = this.projectCategoryRepository.updateCategory(projectCategory);
                if (result != null)
                {
                    try
                    {
                        projectTraceService.addProjectTrace(projectId,
                                EOperationType.OPERATION_UPDATE, getServiceDomain(),
                                String.valueOf(projectCategory.getId()), getServiceCategory(),
                                String.format("Update project category from '%s' to '%s'", entityById.getName(), projectCategory.getName()),
                                entityById, projectCategory);

                        // update lastUpdateTime in project
                        projectInfoService.updateProject(projectId, projectInfo);
                    }
                    catch (Exception ex)
                    {
                        LOGGER.error("Failed to trace when updating project category: " + ex);
                    }

                    projectCategory.setCreationTime(entityById.getCreationTime());

                    return projectCategory;
                }
                else
                {
                    throw new ServiceException(String.format("Project category (%s) update failed", projectCategory));
                }
            }
            else
            {
                throw new ServiceException(String.format("Conflicted with another column with same name (%s)", entityByName));
            }
        }
        else
        {
            throw new ServiceException("Invalid project category: " + projectCategory);
        }
    }

    public boolean removeProjectCategory (Integer categoryId)
    {
        // TODO
        // 1. remove category data
        // 2. remove category info
        ProjectCategory projectCategory = this.projectCategoryRepository.getCategoryById(categoryId);
        if (projectCategory != null)
        {
            ProjectInfo projectInfo = this.projectInfoService.getProjectById(projectCategory.getProjectId());
            Integer result = this.projectCategoryRepository.removeCategoryById(categoryId);
            if (result != null)
            {
                try
                {
                    projectTraceService.addProjectTrace(projectCategory.getProjectId(),
                            EOperationType.OPERATION_DEL, getServiceDomain(),
                            String.valueOf(projectCategory.getId()), getServiceCategory(),
                            String.format("Delete project category '%s'", projectCategory.getName()),
                            projectCategory, null);

                    // update lastUpdateTime in project
                    projectInfoService.updateProject(projectInfo.getId(), projectInfo);
                }
                catch (Exception ex)
                {
                    LOGGER.error("Failed to trace when removing project category: " + ex);
                }

                // remove category data
                return true;
            }
            else
            {
                throw new ServiceException(String.format("Project category (%s) deletion failed", projectCategory));
            }
        }

        throw new ServiceException(String.format("Project category (id: %d) does not exist", categoryId));
    }

    public boolean removeProjectCategoriesByProjectId (Integer projectId)
    {
        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
        if (projectInfo != null)
        {
            return projectCategoryRepository.removeCategoriesByProjectId(projectId) != null;
        }

        throw new ServiceException(String.format("Project (id: %d) does not exist", projectId));
    }

    protected String getServiceCategory ()
    {
        return NOT_AVAILABLE;
    }

    protected String getServiceDomain ()
    {
        return EServiceDomain.DOMAIN_PROJECT_CATEGORIES.getLabel();
    }
}
