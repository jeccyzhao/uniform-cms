package com.nokia.ucms.common.controller;

import com.nokia.ucms.common.entity.ApiQueryResult;
import com.nokia.ucms.common.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

/**
 * Created by x36zhao on 2017/3/6.
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler extends BaseController
{
    private static final Logger LOGGER = Logger.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiQueryResult<Map<String, Object>> handleServiceException(Exception ex)
    {
        LOGGER.error("Internal server error caught: " + ex);
        return createErrorQueryResult(ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus( HttpStatus.UNAUTHORIZED)
    public ApiQueryResult<Map<String, Object>> handleUnauthorizedException(Exception ex)
    {
        LOGGER.error("Access is denied: " + ex);
        return createErrorQueryResult(ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus( HttpStatus.UNAUTHORIZED)
    public ApiQueryResult<Map<String, Object>> handleUnexpectedException(Exception ex)
    {
        LOGGER.error("Unexpected exception caught: " + ex);
        return createErrorQueryResult(ex);
    }

    protected String getModulePath ()
    {
        return null;
    }
}
