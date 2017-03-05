package com.nokia.ucms.biz.entity;

import com.nokia.ucms.common.entity.BaseEntity;
import lombok.Data;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Data
public class ProjectInfo extends BaseEntity
{
    public static final Integer STAT_ACTIVATED = 1;
    public static final Integer STAT_DEACTIVIATED = 0;

    private String name;
    private String description;
    private String owner;
    private String tableName;
    private Integer state = STAT_ACTIVATED;

    public String toString()
    {
        return String.format(
                "ProjectInfo [name = %s, description = %s, owner = %s, tableName = %s, state = %d]",
                name, description, owner, tableName, state);
    }
}
