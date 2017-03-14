package com.nokia.ucms.biz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nokia.ucms.biz.constants.ETemplateColumnProperty;
import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.common.dto.BaseDTO;
import com.nokia.ucms.common.utils.DateUtil;
import lombok.Data;
import lombok.ToString;

import java.util.*;

/**
 * Created by x36zhao on 2017/3/7.
 */
@Data
@ToString
public class ProjectRecordDataDTO<T> extends BaseDTO
{
    private ProjectInfo project;
    //private List<ProjectRecordDataRow> rows;
    private List<ProjectColumn> columns;
    private List<LinkedHashMap<String, Object>> rowData;

    @Data
    @ToString
    public static class ProjectRecordDataRow
    {
        private Integer id;
        private String categoryName;
        private Integer categoryId;

        private String owner;
        private String lastUpdateUser;
        private List<ProjectColumnProperty> props;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
        private Date creationTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
        private Date updateTime;

        private String buildDataTableTemplateColumns ()
        {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("`%s`,", ETemplateColumnProperty.TEMPLATE_COLUMN_CATEGORY_ID.getColumnId()));
            builder.append(String.format("`%s`,", ETemplateColumnProperty.TEMPLATE_COLUMN_CREATE_TIME.getColumnId()));
            builder.append(String.format("`%s`,", ETemplateColumnProperty.TEMPLATE_COLUMN_UPDATE_TIME.getColumnId()));
            builder.append(String.format("`%s`,", ETemplateColumnProperty.TEMPLATE_COLUMN_OWNER.getColumnId()));
            builder.append(String.format("`%s`,", ETemplateColumnProperty.TEMPLATE_COLUMN_UPDATE_USER.getColumnId()));
            return builder.toString();
        }

        public String getInsertedColumnIds ()
        {
            StringBuilder builder = new StringBuilder();
            builder.append(buildDataTableTemplateColumns());
            if (props != null)
            {
                for (int i = 0, size = props.size(); i < size; i++)
                {
                    ProjectColumnProperty columnProperty = props.get(i);
                    if (columnProperty.getId() != null)
                    {
                        String coma = (i < size - 1) ? "," : "";
                        builder.append(String.format("`%s`%s", columnProperty.getId(), coma));
                    }
                }
            }

            return builder.toString();
        }

        public String getInsertedColumnIdsByNames (final List<ProjectColumn> projectColumns)
        {
            StringBuilder builder = new StringBuilder();
            builder.append(buildDataTableTemplateColumns());
            if (props != null && projectColumns != null && projectColumns.size() > 0)
            {
                for (int i = 0, size = props.size(); i < size; i++)
                {
                    ProjectColumnProperty columnProperty = props.get(i);
                    if (columnProperty.getName() != null)
                    {
                        for (ProjectColumn projectColumn : projectColumns)
                        {
                            if (projectColumn.getColumnName().equals(columnProperty.getName().trim()))
                            {
                                String columnId = projectColumn.getColumnId();
                                String coma = (i < size - 1) ? "," : "";
                                builder.append(String.format("`%s`%s", columnId, coma));
                            }
                        }
                    }
                }
            }

            return builder.toString();
        }

        public Map<String, String> getUpdatedColumnIdsByNames (final List<ProjectColumn> projectColumns)
        {
            Map<String, String> updateColumnIds = null;
            if (props != null && projectColumns != null && projectColumns.size() > 0)
            {
                updateColumnIds = new HashMap<String, String>();
                for (int i = 0, size = props.size(); i < size; i++)
                {
                    ProjectColumnProperty columnProperty = props.get(i);
                    if (columnProperty.getName() != null)
                    {
                        for (ProjectColumn projectColumn : projectColumns)
                        {
                            if (projectColumn.getColumnName().equals(columnProperty.getName().trim()))
                            {
                                updateColumnIds.put(projectColumn.getColumnId(), columnProperty.getValue().replace("'", "''"));
                            }
                        }
                    }
                }
            }

            return updateColumnIds;
        }

        public String getInsertedColumnValues ()
        {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%d,", categoryId));
            builder.append(String.format("'%s',", creationTime != null ? DateUtil.getFormatedDate(creationTime) : ""));
            builder.append(String.format("'%s',", updateTime != null ? DateUtil.getFormatedDate(updateTime) : ""));
            builder.append(String.format("'%s',", owner.replaceAll("'", "\\'")));
            builder.append(String.format("'%s',", lastUpdateUser.replaceAll("'", "\\'")));
            if (props != null)
            {
                for (int i = 0, size = props.size(); i < size; i++)
                {
                    ProjectColumnProperty columnProperty = props.get(i);
                    String coma = (i < size - 1) ? "," : "";
                    if (columnProperty.getValue() == null)
                    {
                        builder.append(String.format("'%s'%s", "", coma));
                    }
                    else
                    {
                        builder.append(String.format("'%s'%s", columnProperty.getValue().replaceAll("'", "''"), coma));
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

//    public void addRow(ProjectRecordDataRow row)
//    {
//        if (row != null)
//        {
//            if (rows == null)
//            {
//                rows = new ArrayList<ProjectRecordDataRow>();
//            }
//            rows.add(row);
//        }
//    }

    public void addRowData (LinkedHashMap<String, Object> data)
    {
        if (data != null)
        {
            if (rowData == null)
            {
                rowData = new ArrayList<LinkedHashMap<String, Object>>();
            }
            rowData.add(data);
        }
    }

}
