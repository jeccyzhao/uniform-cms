package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.constants.EOperationType;
import com.nokia.ucms.biz.constants.EServiceDomain;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.biz.entity.ProjectTag;
import com.nokia.ucms.biz.repository.ProjectTagRepository;
import com.nokia.ucms.common.exception.ServiceException;
import com.nokia.ucms.common.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

import static com.nokia.ucms.biz.constants.Constants.*;


/**
 * Created by x36zhao on 2017/3/9.
 */
@Service
public class ProjectTagService extends BaseService
{
    private static Logger LOGGER = Logger.getLogger(ProjectTagService.class);

    @Autowired
    private ProjectTagRepository projectTagRepository;

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private ProjectTraceService projectTraceService;

    public List<ProjectTag> findTagsByName (Integer projectId, String tagName)
    {
        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
        return projectTagRepository.getTagsByName(projectId, tagName);
    }

    public ProjectTag addProjectTag(Integer projectId, ProjectTag tag) throws ServiceException {

        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
        if (projectInfo != null)
        {
            if (tag != null && tag.getTagName() != null)
            {
                if (! tag.getTagName().trim().equals("")) {
                    ProjectTag projectTag = projectTagRepository.getProjectTagByName(projectId, tag.getTagName());
                    if (projectTag == null) {
                        tag.setProjectId(projectId);
                        Integer result = projectTagRepository.addTag(tag);
                        if (result != null) {
                            try {
                                projectTraceService.addProjectTrace(projectId,
                                        EOperationType.OPERATION_ADD, getServiceDomain(),
                                        String.valueOf(tag.getId()),
                                        getServiceCategory(),
                                        String.format("Add project tag '%s'", tag.getTagName()),
                                        null, tag);
                            } catch (Exception ex) {
                                LOGGER.error("Failed to trace when adding project tag: " + ex);
                            }

                            return tag;
                        }
                        throw new ServiceException("Failed to add project tag: " + tag);
                    } else {
                        throw new ServiceException(String.format(
                                "Conflicted with another tag with same name (tag name: %s, tag id: %d), ", projectTag.getTagName(), projectTag.getId()));
                    }
                } else {
                    throw new ServiceException("Invalid tag name");
                }
            } else {
                throw new ServiceException("Invalid tag: " + tag);
            }
        } else {
            throw new ServiceException(String.format("Project (id: %d) does not exist", projectId));
        }
    }

    public List<ProjectTag> getProjectTags (Integer projectId) throws ServiceException
    {
        ProjectInfo projectInfo = projectInfoService.getProjectById(projectId);
        if (projectInfo != null)
        {
            return projectTagRepository.getTagsByProjectId(projectId);
        }

        throw new ServiceException("Failed to get project tags by project id: " + projectId);
    }

    public ProjectTag getProjectTagById (Integer projectId, Integer tagId) throws ServiceException
    {
        ProjectTag tag = projectTagRepository.getProjectTagById(projectId, tagId);
        if (tag != null)
        {
            return tag;
        }

        throw new ServiceException(String.format("Project tag (id: %d) does not exist", tagId));
    }

    public ProjectTag getProjectTagByName (Integer projectId, String tagName)throws ServiceException
    {
        ProjectTag tag = projectTagRepository.getProjectTagByName(projectId, tagName);
        if (tag != null)
        {
            return tag;
        }

        throw new ServiceException(String.format("Project tag (name: %s) does not exist", tagName));
    }

    public ProjectTag updateProjectTag(Integer projectId, ProjectTag projectTag) throws ServiceException
    {
        if (projectTag != null && projectTag.getId() != null && projectTag.getTagName()!= null)
        {
            ProjectTag entityById = projectTagRepository.getProjectTagById(projectId, projectTag.getId());
            if (entityById != null)
            {
                ProjectTag entityByName = projectTagRepository.getProjectTagByName(projectId, projectTag.getTagName());
                if (entityByName == null || entityByName.getId() == projectTag.getId())
                {
                    int result = projectTagRepository.updateProjectTag(projectTag);
                    if (result > 0)
                    {
                        try
                        {
                            projectTraceService.addProjectTrace(projectId,
                                    EOperationType.OPERATION_UPDATE, getServiceDomain(),
                                    String.valueOf(projectTag.getProjectId()), getServiceCategory(),
                                    String.format("Update project tag from '%s' to '%s'",
                                            entityById.getTagName(), projectTag.getTagName()),
                                    entityById, projectTag);
                        }
                        catch (Exception ex) {
                            LOGGER.error("Failed to trace when updating project column: " + ex);
                        }

                        return projectTag;
                    } else {
                        throw new ServiceException(String.format("Project tag (%s) update failed", projectTag));
                    }
                } else {
                    throw new ServiceException(String.format(
                            "Conflicted with another tag with same name (%s)", projectTag.getTagName()));
                }
            } else {
                throw new ServiceException(String.format("Project tag (id: %d) does not exist", projectTag.getId()));
            }
        } else {
            throw new ServiceException("Invalid project tag: " + projectTag);
        }
    }

    public boolean removeProjectTag (Integer projectId, Integer tagId) throws ServiceException
    {
        ProjectTag projectTag = this.projectTagRepository.getProjectTagById(projectId, tagId);
        if (projectTag != null)
        {
            Integer result = this.projectTagRepository.removeProjectTagById(projectId, tagId);
            if (result != null)
            {
                try {
                    projectTraceService.addProjectTrace(projectTag.getProjectId(),
                            EOperationType.OPERATION_DEL, getServiceDomain(),
                            String.valueOf(projectTag.getId()), getServiceCategory(),
                            String.format("Delete project tag '%s'", projectTag.getTagName()),
                            projectTag, null);
                } catch (Exception ex) {
                    LOGGER.error("Failed to trace when removing project tag: " + ex);
                }

                return true;
            } else {
                throw new ServiceException(String.format("Project tag (%s) deletion failed", projectTag));
            }
        }
        throw new ServiceException(String.format("Project tag (id: %d) does not exist", tagId));
    }

    protected String getServiceCategory ()
    {
        return NOT_AVAILABLE;
    }

    protected String getServiceDomain ()
    {
        return EServiceDomain.DOMAIN_PROJECT_TAGS.getLabel();
    }
}
