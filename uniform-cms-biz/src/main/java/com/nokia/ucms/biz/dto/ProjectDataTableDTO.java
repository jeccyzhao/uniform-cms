package com.nokia.ucms.biz.dto;

import com.nokia.ucms.biz.entity.ProjectColumn;
import com.nokia.ucms.biz.entity.ProjectInfo;
import com.nokia.ucms.common.dto.BaseDTO;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by x36zhao on 2017/3/7.
 */
@Data
@ToString
public class ProjectDataTableDTO<T> extends BaseDTO
{
    private ProjectInfo project;
    private List<ProjectDataTableRow> rows;
    private List<ProjectColumn> columns;

    @Data
    @ToString
    public static class ProjectDataTableRow
    {
        private Integer id;
        private String categoryName;
        private List<ProjectColumnProperty> cells;
        private Date creationTime;
        private Date lastUpdateTime;
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

    public void addRow(ProjectDataTableRow row)
    {
        if (row != null)
        {
            if (rows == null)
            {
                rows = new ArrayList<ProjectDataTableRow>();
            }
            rows.add(row);
        }
    }

}
