package com.nokia.ucms.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Controller
public class AppController
{
    @RequestMapping("/")
    public String home()
    {
        return "modules/index";
    }
}
