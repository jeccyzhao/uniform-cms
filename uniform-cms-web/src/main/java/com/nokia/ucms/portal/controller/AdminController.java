package com.nokia.ucms.portal.controller;

import com.nokia.ucms.biz.entity.SystemConfig;
import com.nokia.ucms.biz.service.SystemConfigService;
import com.nokia.ucms.common.controller.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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
    public String showAdminDefaultPage(Model model)
    {
        return showAdminUsers(model);
    }

    @RequestMapping("/users")
    public String showAdminUsers(Model model)
    {
        setBasicInfoInModel(model);
        return getModulePage("adminUsers");
    }

    @RequestMapping("/database")
    public String showAdminDatabase(Model model)
    {
        setBasicInfoInModel(model);
        return getModulePage("adminDatabase");
    }

    @Override
    protected String getModulePath ()
    {
        return "modules/admin";
    }
}
