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
    private String projectName;
    private String columnName;
    private Integer columnLength;
}
