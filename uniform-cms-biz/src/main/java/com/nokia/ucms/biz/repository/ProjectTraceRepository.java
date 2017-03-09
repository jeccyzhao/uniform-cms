package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.entity.ProjectTrace;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/7.
 */
public interface ProjectTraceRepository
{
    ProjectTrace addProjectTrace(final ProjectTrace projectTrace);
    ProjectTrace getTraceById(@Param("id") Integer projectTraceId);
    List<ProjectTrace> getTraceByProjectId(@Param("projectId") Integer projectId);
    Integer deleteProjectTrace(@Param("projectId") Integer projectId);
    Integer deleteProjectTraceById(@Param("id") Integer projectTraceId);
}
