package com.nokia.ucms.biz.dto;

import com.nokia.ucms.common.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by x36zhao on 2017/3/5.
 */
@Data
public class TableColumnDTO extends BaseDTO
{
    private String tableName;
    private String columnName;
    private Integer columnLength;

    public TableColumnDTO()
    {

    }

    public TableColumnDTO (String tableName, String columnName)
    {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public String toString()
    {
        return String.format("TableColumnDTO [tableName = %s, columnName = %s, columnLength = %s]", tableName, columnName, columnLength);
    }
}
