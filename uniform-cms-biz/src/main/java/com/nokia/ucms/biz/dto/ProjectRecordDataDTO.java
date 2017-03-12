package com.nokia.ucms.biz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.common.dto.BaseDTO;
import com.nokia.ucms.common.util.DateUtil;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.nokia.ucms.biz.constants.Constants.*;

/**
 * Created by x36zhao on 2017/3/7.
 */
@Data
@ToString
public class ProjectRecordDataDTO<T> extends BaseDTO
{
    private ProjectInfo project;
    private List<ProjectRecordDataRow> rows;
    private List<ProjectColumn> columns;

    @Data
    @ToString
    @JsonIgnoreProperties(value = {"insertedColumnValues", "insertedColumnIds"})
    public static class ProjectRecordDataRow
    {
        private Integer id;
        private String categoryName;
        private Integer categoryId;

        //@JsonSerialize(using=JsonDateSerializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
        private Date creationTime;

        //@JsonSerialize(using=JsonDateSerializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
        private Date lastUpdateTime;
        private String owner;
        private String lastUpdateUser;
        private List<ProjectColumnProperty> cells;

        public String getInsertedColumnIds ()
        {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("`%s`,", TEMPLATE_COLUMN_CATEGORY_ID));
            builder.append(String.format("`%s`,", TEMPLATE_COLUMN_CREATE_TIME));
            builder.append(String.format("`%s`,", TEMPLATE_COLUMN_UPDATE_TIME));
            builder.append(String.format("`%s`,", TEMPLATE_COLUMN_OWNER));
            builder.append(String.format("`%s`,", TEMPLATE_COLUMN_UPDATE_USER));
            if (cells != null)
            {
                for (int i = 0, size = cells.size(); i < size; i++)
                {
                    ProjectColumnProperty columnProperty = cells.get(i);
                    String coma = (i < size - 1) ? "," : "";
                    builder.append(String.format("`%s`%s", columnProperty.getId(), coma));
                }
            }

            return builder.toString();
        }

        public String getInsertedColumnValues ()
        {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%d,", categoryId));
            builder.append(String.format("'%s',", creationTime != null ? DateUtil.getFormatedDate(creationTime) : ""));
            builder.append(String.format("'%s',", lastUpdateTime != null ? DateUtil.getFormatedDate(lastUpdateTime) : ""));
            builder.append(String.format("'%s',", owner.replaceAll("'", "\\'")));
            builder.append(String.format("'%s',", lastUpdateUser.replaceAll("'", "\\'")));
            if (cells != null)
            {
                for (int i = 0, size = cells.size(); i < size; i++)
                {
                    ProjectColumnProperty columnProperty = cells.get(i);
                    String coma = (i < size - 1) ? "," : "";
                    if (columnProperty.getValue() != null)
                    {
                        builder.append(String.format("'%s'%s", columnProperty.getValue(), coma));
                    }
                    else
                    {
                        builder.append(String.format("'%s'%s", columnProperty.getValue().replaceAll("'", "\\'"), coma));
                    }
                }
            }

            return builder.toString();
        }
    }

    @Data
    @ToString
    public static class ProjectColumnProperty
    {
        private String id;
        private String name;
        private String value;

        public ProjectColumnProperty ()
        {
        }

        public ProjectColumnProperty (String id, String name, String value)
        {
            this.id = id;
            this.name = name;
            this.value = value;
        }
    }

    public void addRow(ProjectRecordDataRow row)
    {
        if (row != null)
        {
            if (rows == null)
            {
                rows = new ArrayList<ProjectRecordDataRow>();
            }
            rows.add(row);
        }
    }

}
