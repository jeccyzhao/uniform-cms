package com.nokia.ucms.portal.controller;

import com.nokia.ucms.biz.constants.Constants;
import com.nokia.ucms.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.nokia.ucms.common.constants.Constants.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Controller
public class AppController extends BaseController implements ErrorController
{
    private static final String ERROR_PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping("/")
    public String home()
    {
        return getModulePage("index");
    }

    @RequestMapping("/login")
    public String showLoginPage(HttpServletRequest request, Model model)
    {
        model.addAttribute(Constants.REDIRECT_URL_PARAMETER, request.getHeader("Referer"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof AnonymousAuthenticationToken ? getModulePage("logon") : getModulePage("index");
    }

    @RequestMapping(value = ERROR_PATH)
    public String showErrorPage(HttpServletRequest request, Model model)
    {
        Map<String, Object> errorBody = getErrorAttributes(request, true);
        Integer status = (Integer) errorBody.get("status");
        if(status == 404)
        {
            model.addAttribute("message", "[" + status + "] Page not found!");
        }
        else
        {
            model.addAttribute("message", "[" + status + "] " + (String) errorBody.get("error"));
        }

        return PAGE_ERROR;
    }

    protected String getModulePath ()
    {
        return "modules";
    }

    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    public String getErrorPath ()
    {
        return ERROR_PATH;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace)
    {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}
