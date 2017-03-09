package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.constants.EServiceDomain;
import com.nokia.ucms.biz.repository.ProjectTagRepository;
import com.nokia.ucms.common.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    protected String getServiceCategory ()
    {
        return NOT_AVAILABLE;
    }

    protected String getServiceDomain ()
    {
        return EServiceDomain.DOMAIN_PROJECT_TAGS.getLabel();
    }
}
