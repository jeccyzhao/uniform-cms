package com.nokia.ucms.biz.constants;

/**
 * Created by x36zhao on 2017/3/9.
 */
public enum EOperationDomain
{
    DOMAIN_PROJECT_COLUMN("Project Columns"),
    DOMAIN_PROJECT_TAGS("Project Tags"),
    DOMAIN_PROJECT_CATEGORIES("Project Categories"),
    DOMAIN_PROJECT_DATA("Project Data");

    private String label;
    EOperationDomain (String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return this.label;
    }
}
