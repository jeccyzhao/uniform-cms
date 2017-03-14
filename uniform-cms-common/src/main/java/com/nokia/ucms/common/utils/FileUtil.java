package com.nokia.ucms.common.utils;

import com.nokia.ucms.common.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by x36zhao on 2017/3/14.
 */
public final class FileUtil
{
    private static final String TEMPORARY_FILE_EXTENSION = "tmp";

    public static String saveMultipartFile (final MultipartFile file)
    {
        OutputStream outputStream = null;
        try
        {
            String fileFullName = file.getOriginalFilename();
            String fileExt = null;
            String fileName = null;
            if (fileFullName.lastIndexOf(".") > -1)
            {
                fileExt = fileFullName.substring(fileFullName.lastIndexOf("."));
                fileName = fileFullName.substring(0, fileFullName.lastIndexOf("."));
            }
            else
            {
                fileExt = TEMPORARY_FILE_EXTENSION;
                fileName = fileFullName;
            }

            File tempFile = File.createTempFile(fileName, fileExt);
            byte[] bytes = file.getBytes();
            outputStream = new FileOutputStream(tempFile);
            outputStream.write(bytes);

            return tempFile.getAbsolutePath();
        }
        catch (IOException e)
        {
            throw new ServiceException("Failed to write to file", e.getCause());
        }
        finally
        {
            if (outputStream != null)
            {
                try
                {
                    outputStream.close();
                }
                catch (IOException e)
                {
                    // do nothing
                }
            }
        }
    }
}
