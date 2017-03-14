package com.nokia.ucms.common.util;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by x36zhao on 2017/3/14.
 */
public class ExcelParser
{
    private static Logger LOGGER = Logger.getLogger(ExcelParser.class);
    private static int DEFAULT_SHEET_INDEX = 0;

    /**
     * Parses the given excel file and converted to list of objects
     *
     * @param excelFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<ExcelSheetDataDTO> parseFile (String excelFile) throws FileNotFoundException, IOException
    {
        return parseFile(excelFile, DEFAULT_SHEET_INDEX);
    }

    public List<ExcelSheetDataDTO> parseFile (String excelFile, int sheetIndex) throws FileNotFoundException, IOException
    {
        LOGGER.info("Start parsing excel file: " + excelFile);

        //List<GimIdeaItem> ideaItems = new ArrayList<GimIdeaItem>();
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFile));
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

        Map<String, Integer> ideaColumns = getColumns(sheet.getRow(0));
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++)
        {
            //ideaItems.add(transformToItem(sheet.getRow(i), ideaColumns));
        }

        //LOGGER.info("By total " + ideaItems.size() + " number of ideas been parsed.");

        return null;
    }

    /**
     * Return all columns from given row
     *
     * @param row
     * @return
     */
    private Map<String, Integer> getColumns (XSSFRow row)
    {
        Map<String, Integer> columns = new HashMap<String, Integer>();
        if (row != null)
        {
            for (int i = row.getFirstCellNum(); i < row.getPhysicalNumberOfCells(); i++)
            {
                columns.put(row.getCell(i).toString(), i);
            }
        }

        return columns;
    }

    /**
     * Return cell value
     *
     * @param propName
     * @param row
     * @param columns
     * @return
     */
    private String getCellValue (String propName, XSSFRow row, Map<String, Integer> columns)
    {
        // TODO: retrieve the correct column name
        Integer cellNumber = columns.get("");
        if (cellNumber != null)
        {
            XSSFCell cell = row.getCell(cellNumber);
            if (cell != null)
            {
                switch (cell.getCellType())
                {
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        short format = cell.getCellStyle().getDataFormat();
                        SimpleDateFormat sdf = null;

                        if(format == 14 || format == 31 || format == 57 || format == 58)
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
        }

        return null;
    }
}
