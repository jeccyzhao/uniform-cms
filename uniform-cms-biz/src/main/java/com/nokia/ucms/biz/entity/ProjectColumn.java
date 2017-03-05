package com.nokia.ucms.biz.entity;

import com.nokia.ucms.common.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import static com.nokia.ucms.biz.constants.Constants.*;

/**
 * Created by x36zhao on 2017/3/4.
 */
@Data
@ToString(exclude = "id")
public class ProjectColumn extends BaseEntity
{
    private Integer projectId;
    private String columnName;
    private String columnDescription;
    private String columnId;
    private Integer columnLength = DEFAULT_COLUMN_LENGTH;
    private Integer visible;
    private Integer seq;
}
