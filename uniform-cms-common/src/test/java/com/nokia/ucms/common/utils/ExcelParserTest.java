package com.nokia.ucms.common.utils;

import com.nokia.ucms.common.dto.ExcelSheetDataDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

/**
 * Created by x36zhao on 2017/3/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExcelParserTest
{
    private ExcelParser parser;
    private String excelFile;
    private int sheetIndex = 0;

    @Before
    public void setUp() throws Exception
    {
        excelFile = ExcelParserTest.class.getResource("/Component_Ownership_Directory.xlsx").getPath();
        parser = new ExcelParser();
    }

    @Test
    public void testParseExcelFile () throws Exception
    {
        ExcelSheetDataDTO sheetDataDTO = parser.parseFile(excelFile, sheetIndex);
        assertNotNull(sheetDataDTO.getHeaderColumns());
        assertTrue(sheetDataDTO.getHeaderColumns().size() > 0);
        assertTrue(sheetDataDTO.getSheetIndex() == sheetIndex);
        assertEquals(sheetDataDTO.getHeaderColumns().get(0).getColumnName(), "Value Stream");
    }

}
