package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.repository.ProjectCategoryRepository;
import com.nokia.ucms.common.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by x36zhao on 2017/3/9.
 */
@Service
public class ProjectCategoryService extends BaseService
{
    private static Logger LOGGER = Logger.getLogger(ProjectCategoryService.class);

    @Autowired
    private ProjectCategoryRepository projectCategoryRepository;

    @Autowired
    private ProjectInfoService projectInfoService;

    protected String getModuleCategory ()
    {
        return "Categories";
    }
}
