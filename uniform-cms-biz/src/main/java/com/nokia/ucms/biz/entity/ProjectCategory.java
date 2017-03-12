package com.nokia.ucms.biz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nokia.ucms.common.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * Created by x36zhao on 2017/3/4.
 */
@Data
@ToString(exclude = "id")
public class ProjectCategory extends BaseEntity
{
    private Integer projectId;

    @NotEmpty
    private String name;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date creationTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;
    private String owner;
}
