package com.nokia.ucms.biz.constants;

/**
 * Created by x36zhao on 2017/3/9.
 */
public enum EServiceDomain
{
    DOMAIN_PROJECT_COLUMN("Project Columns"),
    DOMAIN_PROJECT_TAGS("Project Tags"),
    DOMAIN_PROJECT_CATEGORIES("Project Categories"),
    DOMAIN_PROJECT_TRACES("Project Traces"),
    DOMAIN_PROJECT_INFO("Project Info"),
    DOMAIN_PROJECT_RECORDS("Project Records");

    private String label;
    EServiceDomain (String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return this.label;
    }
}
