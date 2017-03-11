package com.nokia.ucms.portal.controller;

import com.nokia.ucms.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by x36zhao on 2017/3/11.
 */
@Controller
@RequestMapping("/export")
public class ExportController extends BaseController
{
    @RequestMapping("")
    public void exportExcel (HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String content = request.getParameter("content");
        String fileName = request.getParameter("filename");
        String format = request.getParameter("format");

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", String.format("attachment;filename=%s.%s", fileName, format));
        PrintWriter printWriter = response.getWriter();
        printWriter.print(content);
        printWriter.flush();
        printWriter.close();
    }

    protected String getModulePath ()
    {
        return null;
    }
}
