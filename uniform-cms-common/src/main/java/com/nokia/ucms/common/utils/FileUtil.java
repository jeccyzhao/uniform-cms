package com.nokia.ucms.common.utils;

import com.nokia.ucms.common.exception.ServiceException;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by x36zhao on 2017/3/14.
 */
public final class FileUtil
{
    private static final String TEMPORARY_FILE_EXTENSION = "tmp";

    public static boolean isFileExists (String file)
    {
        return new File(file).exists();
    }

    public static List<FileEntry> listFiles (String folder)
    {
        List<FileEntry> fileEntries = new ArrayList<FileEntry>();
        File[] files = null;
        File fos = new File (folder);
        if (fos.exists() && fos.isDirectory())
        {
            files = fos.listFiles();
            // Arrays.sort(files, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));  --> lambda in java8
            Arrays.sort(files, new Comparator<File>()
            {
                public int compare (File o1, File o2)
                {
                    return o1.lastModified() < o2.lastModified() ? 1  : -1;
                }
            });
        }

        if (files != null)
        {
            for (File file : files)
            {
                if (!file.isDirectory())
                {
                    fileEntries.add(new FileEntry(file.getName(), file.getPath(), file.length(), file.lastModified()));
                }
            }
        }

        return fileEntries;
    }

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

    @Data
    public static class FileEntry
    {
        private String name;
        private String path;
        private long length;
        private long lastModified;
        private String timestamp;

        public FileEntry () {}
        public FileEntry (String name, String path, long length, long lastModified)
        {
            this.name = name;
            this.path = path;
            this.length = length;
            this.lastModified = lastModified;

            Date date = new Date();
            date.setTime(this.lastModified);
            this.timestamp = DateUtil.getFormatedDate(date);
        }

    }
}
