package com.nokia.ucms.portal.controller;

import com.nokia.ucms.common.controller.BaseController;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Controller
public class AppController extends BaseController
{
    @RequestMapping("/")
    public String home()
    {
        return getModulePage("index");
    }

    @RequestMapping("/login")
    public String showLoginPage()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof AnonymousAuthenticationToken ? getModulePage("logon") : getModulePage("index");
    }

    protected String getModulePath ()
    {
        return "modules";
    }
}
