package com.nokia.ucms.portal.controller;

import com.nokia.ucms.common.controller.BaseController;
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
        return "modules/index";
    }

}
