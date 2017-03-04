package com.nokia.ucms.portal.controller;

import com.nokia.ucms.biz.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Controller
public class AppController
{
    @RequestMapping("/")
    public String home()
    {
        System.out.println("Enter home(): "); return "modules/index";
    }

}
