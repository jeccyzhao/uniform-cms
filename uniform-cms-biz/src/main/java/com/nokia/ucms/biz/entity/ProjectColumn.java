package com.nokia.ucms.biz.entity;

import com.nokia.ucms.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by x36zhao on 2017/3/4.
 */
@Setter
@Getter
public class ProjectColumn extends BaseEntity
{
    private ProjectInfo project;
    private String name;
    private String fieldId;
    private Integer length;
    private boolean visible;
    private Integer seq;
}
