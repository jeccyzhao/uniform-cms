package com.nokia.ucms.biz.dto;

import com.nokia.ucms.common.dto.BaseDTO;
import lombok.Data;
import lombok.ToString;

/**
 * Created by x36zhao on 2017/3/5.
 */
@Data
@ToString
public class TableColumnDTO extends BaseDTO
{
    private String columnName;
    private String columnId;
    private String columnValue;

    public TableColumnDTO()
    {
    }

    public TableColumnDTO (String columnId, String columnName, String columnValue)
    {
        this.columnId = columnId;
        this.columnName = columnName;
        this.columnValue = columnValue;
    }
}
