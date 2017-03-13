package com.nokia.ucms.common.controller;

import com.nokia.ucms.common.entity.ApiQueryResult;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * Created by x36zhao on 2017/3/6.
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler extends BaseController
{
    private static final Logger LOGGER = Logger.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiQueryResult<Map<String, Object>> handleException(Exception ex)
    {
        LOGGER.error("Unexpected exception caught: " + ex);
        return createErrorQueryResult(ex);
    }

    protected String getModulePath ()
    {
        return null;
    }
}
