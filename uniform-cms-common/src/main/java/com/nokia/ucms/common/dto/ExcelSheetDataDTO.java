package com.nokia.ucms.common.dto;

import com.nokia.ucms.common.dto.BaseDTO;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * Created by x36zhao on 2017/3/14.
 */
@Data
@ToString
public class ExcelSheetDataDTO extends BaseDTO
{
    private int sheetName;
    private int sheetIndex;
    private List<HeaderColumn> headerColumns;
    private List<Row> rows;

    @Data
    @ToString
    public static class HeaderColumn
    {
        private int columnIndex;
        private String columnName;
    }

    @Data
    @ToString
    public static class Row
    {
        private int rowIndex;
        private List<Cell> cells;
    }

    @Data
    @ToString
    public static class Cell
    {
        private int cellIndex;
        private Object cellValue;
    }
}
