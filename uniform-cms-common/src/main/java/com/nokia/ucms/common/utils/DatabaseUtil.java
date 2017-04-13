package com.nokia.ucms.common.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.Date;

/**
 * Created by x36zhao on 2017/4/11.
 */
public class DatabaseUtil
{
    private static Logger LOGGER = Logger.getLogger(DatabaseUtil.class);

    private static String MYSQLDUMP_COMMAND_PATTERN = "mysqldump -h %s -u %s --databases %s > %s";
    private static String DEFAULT_HOST = "localhost";
    private static String DEFAULT_USER = "root";

    public static String dumpDatabase (String database, String dumpFolder)
    {
        return dumpDatabase(DEFAULT_HOST, DEFAULT_USER, database, dumpFolder);
    }

    public static String dumpDatabase (String host, String user, String database, String dumpFolder)
    {
        String dumpFile = getDumpFilePath(database, dumpFolder);
        String dumpCommandLine = getMysqlDumpCommand(host, user, database, dumpFile);

        ProcessUtil.executeCommand(dumpCommandLine);
        File file = new File(dumpFile);
        if (file.exists())
        {
            return dumpFile;
        }

        return null;
    }

    private static String getDumpFilePath (String database, String dumpFolder)
    {
        return String.format("%s/%s_%s.sql", dumpFolder, database, DateUtil.getFormatedDate(new Date(), "yyyyMMddHHmmss"));
    }


    private static String getMysqlDumpCommand (String host, String user, String database, String dumpFile)
    {
        return String.format(MYSQLDUMP_COMMAND_PATTERN, host, user, database, dumpFile);
    }
}
