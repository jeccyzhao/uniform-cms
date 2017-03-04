package com.nokia.ucms.biz.entity;

import com.nokia.ucms.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by x36zhao on 2017/3/4.
 */
@Getter
@Setter
public class ProjectCategory extends BaseEntity
{
    private Project project;
    private String name;
    private String description;
}
