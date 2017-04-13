package com.nokia.ucms.common.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by x36zhao on 2017/2/12.
 */
public class ProcessUtil
{
    private static Logger logger = Logger.getLogger(ProcessUtil.class);

    public static String executeCommand (String command)
    {
        if (logger.isDebugEnabled())
            logger.debug(String.format("exec: %s", command));

        Process process = null;
        StringBuilder output = new StringBuilder();
        BufferedReader bufferedReader = null;
        try
        {
            String line = null;
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = bufferedReader.readLine()) != null)
            {
                output.append(line);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to execute command: " + command, e);
        }
        finally
        {
            try
            {
                bufferedReader.close();
            }
            catch(Exception e)
            {
               logger.warn("Failed to close i/o channel when executing command", e);
            }

            try
            {
                if (process != null)
                {
                    process.destroy();
                }
            }
            catch (Exception e)
            {
                logger.warn("Failed to destroy process when executing command", e);
            }
        }

        return output.toString();
    }
}
