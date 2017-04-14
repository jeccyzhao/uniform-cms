package com.nokia.ucms.portal.controller;

import com.nokia.ucms.biz.constants.Constants;
import com.nokia.ucms.common.controller.BaseController;
import org.apache.log4j.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(AppController.class);

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
        String errorMessage = (String) errorBody.get("error");
        Integer statusCode = (Integer) errorBody.get("status");
        model.addAttribute("message", errorMessage);
        model.addAttribute("statusCode", statusCode);
        LOGGER.error("Error occurred when access page '" + request.getRequestURI() + "'  due to '" + errorMessage + "'");
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
