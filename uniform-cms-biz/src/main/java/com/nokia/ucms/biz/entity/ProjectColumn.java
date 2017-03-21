package com.nokia.ucms.biz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nokia.ucms.biz.constants.EColumnInputType;
import com.nokia.ucms.common.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

import static com.nokia.ucms.biz.constants.Constants.*;

/**
 * Created by x36zhao on 2017/3/4.
 */
@Data
@ToString(exclude = "id")
//@JsonIgnoreProperties(value = { "columnId" })
public class ProjectColumn extends BaseEntity
{
    public static Integer STAT_VISIBLE = 1;
    public static Integer STAT_INVISIBLE = 0;

    private Integer projectId;

    @NotEmpty
    private String columnName;
    private String columnRemark;
    private String columnDataExample;
    private String columnId;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    // default column length would be used if not set
    private Integer columnLength;

    // default column type would be used if not set
    private Integer columnInputType;

    // default as visibility if not set
    private Integer visible;

    private Integer seq = 0;

    public Integer getColumnLength()
    {
        return columnLength != null && columnLength > 0 ? columnLength : DEFAULT_COLUMN_LENGTH;
    }

    public Integer getColumnInputType()
    {
        return columnInputType != null && columnInputType >= 0 ? columnInputType : EColumnInputType.COLUMN_IN_TYPE_TEXTBOX.getCode();
    }

    public Integer getVisible()
    {
        return visible != null && visible >= 0 ? visible : STAT_VISIBLE;
    }
}
