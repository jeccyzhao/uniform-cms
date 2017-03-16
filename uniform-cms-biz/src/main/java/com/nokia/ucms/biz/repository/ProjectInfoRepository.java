package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.entity.ProjectInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/5.
 */
public interface ProjectInfoRepository
{
    List<ProjectInfo> getAllProject();
    ProjectInfo getProjectInfoByName(@Param("projectName") String projectName);
    ProjectInfo getProjectInfoById(@Param("projectId") Integer projectId);
    List<ProjectInfo> getProjectInfoByOwner (@Param("owner") String owner, @Param("publicity") Integer publicity);
    ProjectInfo getProjectInfoByTableName (@Param("tableName") String tableName);
    Integer addProjectInfo (final ProjectInfo projectInfo);
    Integer updateProjectInfo (final ProjectInfo projectInfo);
    Integer removeProjectInfo(@Param("projectId") Integer projectId);
}
