package com.nokia.ucms.portal.controller;

import com.nokia.ucms.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by x36zhao on 2017/3/7.
 */
@Controller
@RequestMapping("/workspace/{uid}")
public class WorkspaceController extends BaseController
{
    @RequestMapping("")
    public String showUserWorkspace(@PathVariable String uid, Model model)
    {
        setBasicInfoInModel(uid, model);
        return getModulePage("myProject");
    }

    @RequestMapping("/projects")
    public String showUserProjects(@PathVariable String uid, Model model)
    {
        setBasicInfoInModel(uid, model);
        return getModulePage("myProject");
    }

    @RequestMapping("/records")
    public String showUserRecords(@PathVariable String uid, Model model)
    {
        setBasicInfoInModel(uid, model);
        return getModulePage("myRecord");
    }

    private void setBasicInfoInModel(String userId, Model model)
    {
        model.addAttribute("uid", userId);
        model.addAttribute("mod", "workspace");
    }

    protected String getModulePath ()
    {
        return "modules/workspace";
    }
}
