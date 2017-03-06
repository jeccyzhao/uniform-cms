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
    public static Integer STAT_VISIBLE = 1;
    public static Integer STAT_INVISIBLE = 0;

    private Integer projectId;
    private String columnName;
    private String columnRemark;
    private String columnDataExample;
    private String columnId;

    // default column length would be used if not set
    private Integer columnLength = DEFAULT_COLUMN_LENGTH;

    // default as visibility if not set
    private Integer visible = STAT_VISIBLE;

    private Integer seq;
}
