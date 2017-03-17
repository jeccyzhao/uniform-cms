package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.entity.ProjectTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/5.
 */
public interface ProjectTagRepository
{
    ProjectTag getProjectTagByName (@Param("projectId") Integer projectId, @Param("name") String tagName);
    ProjectTag getProjectTagById (@Param("projectId") Integer projectId, @Param("id") Integer tagId);
    Integer addTag (final ProjectTag projectTag);
    Integer updateProjectTag(final ProjectTag projectTag);
    Integer removeProjectTagById(@Param("projectId") Integer projectId, @Param("id") Integer tagId);
    List<ProjectTag> getTagsByProjectId(@Param("projectId") Integer projectId);
    List<ProjectTag> getTagsByName(@Param("projectId") Integer projectId, @Param("name") String tagName);
}
