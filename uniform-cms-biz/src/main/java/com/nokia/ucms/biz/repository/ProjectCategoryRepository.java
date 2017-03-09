package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.entity.ProjectCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/5.
 */
public interface ProjectCategoryRepository
{
    ProjectCategory getCategoryByName(@Param("name") String categoryName);
    ProjectCategory getCategoryById(@Param("id") Integer categoryId);
    List<ProjectCategory> getCategoryByProjectId(@Param("projectId") Integer projectId);
    Integer addCategory(final ProjectCategory projectCategory);
    Integer removeCategoryById(@Param("id") Integer categoryId);
}
