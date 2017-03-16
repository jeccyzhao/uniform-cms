package com.nokia.ucms.common.utils;

import com.nokia.ucms.common.dto.ExcelSheetDataDTO;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.nokia.ucms.common.dto.ExcelSheetDataDTO.*;

/**
 * Created by x36zhao on 2017/3/14.
 */
public class ExcelParser
{
    private static Logger LOGGER = Logger.getLogger(ExcelParser.class);
    private static int DEFAULT_SHEET_INDEX = 0;
    private static int DEFAULT_HEADER_ROW_INDEX = 0;

    /**
     * Parses the given excel file and converted to list of objects
     *
     * @param excelFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ExcelSheetDataDTO parseFile (String excelFile) throws FileNotFoundException, IOException
    {
        return parseFile(excelFile, DEFAULT_SHEET_INDEX);
    }

    public ExcelSheetDataDTO parseFile (String excelFile, int sheetIndex) throws FileNotFoundException, IOException
    {
        LOGGER.info(String.format("Start parsing excel file (%s) in sheet-%d", excelFile, sheetIndex));
        ExcelSheetDataDTO sheetDataDTO = null;
        if (sheetIndex > -1)
        {
            sheetDataDTO = new ExcelSheetDataDTO();

            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFile));
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

            sheetDataDTO.setSheetIndex(sheetIndex);

            // build header columns
            Map<Integer, String> headerColumns = getColumns(sheet.getRow(DEFAULT_HEADER_ROW_INDEX));
            sheetDataDTO.setHeaderColumns(constructHeaderColumns(headerColumns));

            for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++)
            {
                Row row = constructRow(sheet.getRow(i));
                if (row != null)
                {
                    sheetDataDTO.addRow(row);
                }
            }

            LOGGER.info("By total " + sheetDataDTO.getRows().size() + " number of rows been parsed.");
        }

        return sheetDataDTO;
    }

    private Row constructRow (final XSSFRow xssfRow)
    {
        Row row = null;
        if (xssfRow != null)
        {
            row = new Row();
            row.setRowIndex(xssfRow.getRowNum());

            Map<Integer, String> cells = getColumns(xssfRow);
            for (Integer cellIndex : cells.keySet())
            {
                row.addCell(new Cell(cellIndex, getCellValue(xssfRow, cellIndex)));
            }
        }

        return row;
    }

    private List<HeaderColumn> constructHeaderColumns (Map<Integer, String> columnsMap)
    {
        List<HeaderColumn> headerColumns = null;
        if (columnsMap != null)
        {
            headerColumns = new ArrayList<HeaderColumn>();
            for (Map.Entry<Integer, String> entry : columnsMap.entrySet())
            {
                headerColumns.add(new HeaderColumn(entry.getKey(), entry.getValue()));
            }
        }

        return headerColumns;
    }

    /**
     * Return all columns from given row
     *
     * @param row
     * @return
     */
    private Map<Integer, String> getColumns (final XSSFRow row)
    {
        Map<Integer, String> columns = new HashMap<Integer, String>();
        if (row != null)
        {
            for (int i = row.getFirstCellNum(); i < row.getPhysicalNumberOfCells(); i++)
            {
                columns.put(i, row.getCell(i) != null ? row.getCell(i).toString() : "");
            }
        }

        return columns;
    }

    /**
     * Return cell value
     *
     * @param row
     * @param cellIndex
     * @return
     */
    private String getCellValue (XSSFRow row, int cellIndex)
    {
        XSSFCell cell = row.getCell(cellIndex);
        if (cell != null)
        {
            switch (cell.getCellType())
            {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    short format = cell.getCellStyle().getDataFormat();
                    SimpleDateFormat sdf = null;

                    if (format == 14 || format == 31 || format == 57 || format == 58)
                    {
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    else if (format == 20 || format == 32)
                    {
                        sdf = new SimpleDateFormat("HH:mm");
                    }

                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);

                    return sdf != null ? sdf.format(date) : cell.toString();
                case HSSFCell.CELL_TYPE_STRING:
                    return cell.toString();
                default:
                    return "";
            }
        }

        return null;
    }
}
