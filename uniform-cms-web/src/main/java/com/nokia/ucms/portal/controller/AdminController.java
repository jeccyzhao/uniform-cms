package com.nokia.ucms.portal.controller;

import static com.nokia.ucms.biz.constants.Constants.*;
import com.nokia.ucms.biz.entity.SystemConfig;
import com.nokia.ucms.biz.service.SystemConfigService;
import com.nokia.ucms.common.controller.BaseController;
import com.nokia.ucms.common.utils.FileUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by x36zhao on 2017/3/16.
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController
{
    private static Logger LOGGER = Logger.getLogger(AdminController.class);

    @Autowired
    private SystemConfigService systemConfigService;

    private void setBasicInfoInModel(Model model)
    {
        model.addAttribute("mod", "admin");
    }

    @RequestMapping("/settings")
    @PreAuthorize("hasRole('" + ROLE_ADMIN + "')")
    public String showAdminSettings(Model model)
    {
        setBasicInfoInModel(model);

        List<SystemConfig> systemConfigList = systemConfigService.findAll();
        if (systemConfigList != null)
        {
            Map<String, String> systemConfigMap = new HashMap<String, String>();
            for (SystemConfig systemConfig : systemConfigList)
            {
                systemConfigMap.put(systemConfig.getPropertyName(), systemConfig.getPropertyValue());
            }

            model.addAttribute("configs", systemConfigMap);
        }

        return getModulePage("adminSettings");
    }

    @RequestMapping("/saveSettings")
    @PreAuthorize("hasRole('" + ROLE_ADMIN + "')")
    public String saveAdminSettings(HttpServletRequest request)
    {
        Map<String, List<String>> params = getAllRequestParameters(request);
        if (params != null)
        {
            for (Map.Entry<String, List<String>> entry : params.entrySet())
            {
                for (String paramValue : entry.getValue())
                {
                    SystemConfig config = new SystemConfig();
                    config.setPropertyName(entry.getKey());
                    config.setPropertyValue(paramValue);

                    systemConfigService.saveOrUpdate(config);
                }
            }
        }

        return "redirect:/admin/settings";
    }

    @RequestMapping("")
    @PreAuthorize("hasRole('" + ROLE_ADMIN + "')")
    public String showAdminDefaultPage(Model model)
    {
        return showAdminUsers(model);
    }

    @RequestMapping("/users")
    @PreAuthorize("hasRole('" + ROLE_ADMIN + "')")
    public String showAdminUsers(Model model)
    {
        setBasicInfoInModel(model);
        return getModulePage("adminUsers");
    }

    @RequestMapping("/database")
    @PreAuthorize("hasRole('" + ROLE_ADMIN + "')")
    public String showAdminDatabase(Model model)
    {
        setBasicInfoInModel(model);
        model.addAttribute("files", FileUtil.listFiles(systemConfigService.getPropertyValueByName(PROP_BACKUP_PATH, DEFAULT_BACKUP_FOLDER)));
        return getModulePage("adminDatabase");
    }

    @RequestMapping("/database/download")
    @PreAuthorize("hasRole('" + ROLE_ADMIN + "')")
    public void downloadFile (@RequestParam("file") String file,
                                HttpServletRequest request,
                                HttpServletResponse response) throws IOException
    {
        if (file != null && !"".equals(file))
        {
            String backupFolder = systemConfigService.getPropertyValueByName(PROP_BACKUP_PATH, DEFAULT_BACKUP_FOLDER);
            String filePath = String.format("%s/%s", backupFolder, file);

            File fos = new File(filePath);
            FileInputStream fis = new FileInputStream(fos);
            if (fos.exists())
            {
                response.setContentType("text/plain");
                response.setHeader("Content-disposition", String.format("attachment;filename=%s", file));
                response.setContentLength((int) fos.length());
                OutputStream outputStream = response.getOutputStream();

                InputStream in = null;
                try
                {
                    in = new BufferedInputStream(new FileInputStream(fos) );
                    int ch;
                    while ((ch = in.read()) !=-1)
                    {
                        outputStream.write((char)ch);
                    }
                }
                catch (Exception ex)
                {
                    LOGGER.error("Failed to proceed file stream - " + file);
                    return;
                }
                finally
                {
                    if (in != null) in.close();  // very important
                    outputStream.close();
                }

//                try
//                {
//                    byte[] buffer = new byte[BUFF_SIZE];
//                    int byteRead = 0;
//                    while ((byteRead = fis.read()) != -1)
//                    {
//                        outputStream.write(buffer, 0, byteRead);
//                    }
//                    outputStream.flush();
//                }
//                catch (Exception e)
//                {
//                    LOGGER.error("Failed to proceed file stream - " + file);
//                    return;
//                }
//                finally
//                {
//                    outputStream.close();
//                    fis.close();
//                }
            }
        }
    }

    @Override
    protected String getModulePath ()
    {
        return "modules/admin";
    }
}
