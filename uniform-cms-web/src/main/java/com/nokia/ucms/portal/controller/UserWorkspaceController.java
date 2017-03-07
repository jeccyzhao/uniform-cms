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
public class UserWorkspaceController extends BaseController
{
    @RequestMapping("/")
    public String showUserWorkspace(@PathVariable String uid, Model model)
    {
        return getModulePage("workspacePage");
    }

    protected String getModulePath ()
    {
        return "modules/workspace";
    }
}
