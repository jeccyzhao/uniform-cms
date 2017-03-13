package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.entity.ProjectCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/5.
 */
public interface ProjectCategoryRepository
{
    ProjectCategory getCategoryByName(@Param("projectId") Integer projectId, @Param("name") String categoryName);
    ProjectCategory getCategoryById(@Param("id") Integer categoryId);
    Integer addCategory(final ProjectCategory projectCategory);
    Integer removeCategoryById(@Param("id") Integer categoryId);
    Integer updateCategory (final ProjectCategory projectCategory);
    Integer removeCategoriesByProjectId (@Param("projectId") Integer projectId);
    List<ProjectCategory> getCategoriesByProjectId(@Param("projectId") Integer projectId);
}
