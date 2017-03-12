package com.nokia.ucms.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by x36zhao on 2017/3/12.
 */
public class DateUtil
{
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getFormatedDate (final Date date)
    {
        return dateFormat.format(date);
    }
}
