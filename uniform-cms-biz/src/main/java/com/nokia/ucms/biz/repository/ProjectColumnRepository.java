package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.entity.ProjectColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/5.
 */
public interface ProjectColumnRepository
{
    Integer addProjectColumn(final ProjectColumn projectColumn);
    Integer updateProjectColumn(final ProjectColumn projectColumn);
    List<ProjectColumn> getColumnsByProjectId(@Param("projectId") Integer projectId);
    List<ProjectColumn> getColumnsByProjectName(@Param("projectName") String projectName);
    ProjectColumn getColumnById (@Param("id") Integer projectColumnId);
    ProjectColumn getColumnsByColumnName(@Param("name") String columnName, @Param("projectId") Integer projectId);
    Integer deleteProjectColumn(@Param("id") Integer projectColumnId);
}
