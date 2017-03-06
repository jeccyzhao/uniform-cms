package com.nokia.ucms.biz.entity;

import com.nokia.ucms.common.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by x36zhao on 2017/3/6.
 */
@Data
@ToString(exclude = "id")
public class ProjectTag extends BaseEntity
{
    private Integer projectId;

    @NotEmpty
    private String tagName;
    private String tagDesc;
}
