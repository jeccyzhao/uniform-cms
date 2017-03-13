package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.entity.ProjectTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/5.
 */
public interface ProjectTagRepository
{
    ProjectTag getTagByName (@Param("name") String tagName);
    ProjectTag getTagById (@Param("id") Integer tagId);
    Integer addTag (final ProjectTag projectTag);
    Integer removeTagById(@Param("id") Integer tagId);
    List<ProjectTag> getTagsByProjectId(@Param("projectId") Integer projectId);
}
