package com.nokia.ucms.biz.constants;

/**
 * Created by x36zhao on 2017/3/12.
 */
public enum ETemplateColumn
{
    TEMPLATE_COLUMN_ID("id", "id"),
    TEMPLATE_COLUMN_CATEGORY_NAME("c_name", "Category Name"),
    TEMPLATE_COLUMN_CATEGORY_ID("c_id", "Category Id"),
    TEMPLATE_COLUMN_CREATE_TIME("creationTime", "Creation Time"),
    TEMPLATE_COLUMN_UPDATE_TIME("updateTime", "Update Time"),
    TEMPLATE_COLUMN_OWNER("owner", "Owner"),
    TEMPLATE_COLUMN_UPDATE_USER("lastUpdateUser", "Last Update User");

    private String columnId;
    private String columnName;

    ETemplateColumn (String columnId, String columnName)
    {
        this.columnId = columnId;
        this.columnName = columnName;
    }

    public String getColumnId()
    {
        return this.columnId;
    }

    public String getColumnName()
    {
        return this.columnName;
    }
}
