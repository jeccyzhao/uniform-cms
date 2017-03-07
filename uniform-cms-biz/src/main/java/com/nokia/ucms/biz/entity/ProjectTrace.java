package com.nokia.ucms.biz.entity;

import com.nokia.ucms.common.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by x36zhao on 2017/3/7.
 */
@Data
@ToString(exclude = "id")
public class ProjectTrace extends BaseEntity
{
    private Integer projectId;
    private String operator;
    private Date operationTime;
    private Integer operationType;
    private String message;
}
