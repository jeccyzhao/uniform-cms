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
    Integer addProjectInfo (ProjectInfo projectInfo);
    Integer removeProjectInfo(@Param("projectId") Integer projectId);
}
