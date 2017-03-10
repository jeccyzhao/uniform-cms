package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.constants.EOperationType;
import com.nokia.ucms.biz.constants.EServiceDomain;
import com.nokia.ucms.biz.entity.ProjectCategory;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
import com.nokia.ucms.biz.repository.ProjectCategoryRepository;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
        ProjectCategory category = projectCategoryRepository.getCategoryById(categoryId);
        if (category != null)
        {
            return category;
        }

        throw new ServiceException(String.format("Project category (id: %d) does not exist", categoryId));
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

                // TODO replace with logon user
                category.setOwner("Change It");

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
                    }
                    catch (Exception ex)
                    {
                        LOGGER.error("Failed to trace when adding project category: " + ex);
                    }

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

    public ProjectCategory updateProjectCategory (ProjectCategory category)
    {
        if (category != null && category.getId() != null)
        {
            if (category.getName() != null && !"".equals(category.getName().trim()))
            {
                ProjectCategory entityByName = this.projectCategoryRepository.getCategoryByName(category.getName());
                if (entityByName == null || entityByName.getId().equals(category.getId()))
                {
                    ProjectCategory entityById = this.projectCategoryRepository.getCategoryById(category.getId());
                    if (entityById != null)
                    {
                        category.setUpdateTime(new Date());

                        // adding owner and creation time back only for the purpose of displaying in front-end page
                        category.setOwner(entityById.getOwner());
                        category.setCreationTime(entityById.getCreationTime());

                        Integer result = this.projectCategoryRepository.updateCategory(category);
                        if (result != null)
                        {
                            try
                            {
                                projectTraceService.addProjectTrace(category.getProjectId(),
                                        EOperationType.OPERATION_UPDATE, getServiceDomain(),
                                        String.valueOf(category.getId()), getServiceCategory(),
                                        String.format("Update project category from '%s' to '%s'", entityById.getName(), category.getName()),
                                        entityById, category);
                            }
                            catch (Exception ex)
                            {
                                LOGGER.error("Failed to trace when updating project category: " + ex);
                            }

                            return category;
                        }
                        else
                        {
                            throw new ServiceException(String.format("Project category (%s) update failed", category));
                        }
                    }
                    else
                    {
                        throw new ServiceException(String.format("Project category (%s) does not exist", category));
                    }
                }
                else
                {
                    throw new ServiceException(String.format("Conflicted with another category with same name (%s)", entityByName));
                }
            }
            else
            {
                throw new ServiceException("Invalid category name: " + category.getName());
            }
        }

        throw new ServiceException("Invalid category: " + category);
    }

    public boolean removeProjectCategory (Integer categoryId)
    {
        // TODO
        // 1. remove category data
        // 2. remove category info
        ProjectCategory projectCategory = this.projectCategoryRepository.getCategoryById(categoryId);
        if (projectCategory != null)
        {
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
                } catch (Exception ex)
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
