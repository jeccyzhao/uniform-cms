package com.nokia.ucms.biz.dto;

import com.nokia.ucms.common.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by x36zhao on 2017/3/5.
 */
@Getter
@Setter
public class TableColumnDTO extends BaseDTO
{
    private String tableName;
    private String columnName;
    private Integer columnLength;

    public String getTableName ()
    {
        return tableName;
    }

    public void setTableName (String tableName)
    {
        this.tableName = tableName;
    }

    public String getColumnName ()
    {
        return columnName;
    }

    public void setColumnName (String columnName)
    {
        this.columnName = columnName;
    }

    public Integer getColumnLength ()
    {
        return columnLength;
    }

    public void setColumnLength (Integer columnLength)
    {
        this.columnLength = columnLength;
    }
}
