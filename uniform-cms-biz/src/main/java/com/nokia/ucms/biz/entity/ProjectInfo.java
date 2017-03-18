package com.nokia.ucms.biz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nokia.ucms.common.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Data
@ToString(exclude = "id")
@JsonIgnoreProperties(value = { "tableName", "state" })
public class ProjectInfo extends BaseEntity
{
    public static final Integer STAT_ACTIVATED = 1;
    public static final Integer STAT_DEACTIVIATED = 0;

    public static final Integer P_PUBLIC = 1;
    public static final Integer P_PRIVATE = 0;

    @NotEmpty
    private String name;
    private String description;

    //@NotEmpty
    //private String owner;

    private User owner;

    // table name is generated dynamically and update by operator not allowed
    private String tableName;

    // marked as activated as default state
    private Integer state = STAT_ACTIVATED;

    // marked as public as default
    private Integer publicity = P_PUBLIC;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationTime;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    /*
    public String toString()
    {
        return String.format(
                "ProjectInfo [name = %s, description = %s, owner = %s, tableName = %s, state = %d]",
                name, description, owner, tableName, state);
    }
    */
}
