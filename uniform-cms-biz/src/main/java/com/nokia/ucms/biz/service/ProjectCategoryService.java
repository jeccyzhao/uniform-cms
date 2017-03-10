package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.entity.ProjectCategory;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
import com.nokia.ucms.biz.repository.ProjectCategoryRepository;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.service.BaseService;
import org.apache.log4j.Logger;
import org.h2.engine.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean updateProjectCategory (ProjectCategory category)
    {
        if (category != null && category.getId() != null)
        {
            if (category.getName() != null && !"".equals(category.getName().trim()))
            {
                category.setUpdateTime(new Date());

                return this.projectCategoryRepository.updateCategory(category) > 0;
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
                // remove category data
                return true;
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
        return null;
    }

    protected String getServiceDomain ()
    {
        return null;
    }
}
