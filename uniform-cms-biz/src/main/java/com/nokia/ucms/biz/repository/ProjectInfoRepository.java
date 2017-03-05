package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.entity.ProjectInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by x36zhao on 2017/3/5.
 */
public interface ProjectInfoRepository
{
    ProjectInfo getProjectInfoByName(@Param("projectName") String projectName);

    Integer addProjectInfo (ProjectInfo projectInfo);
}
