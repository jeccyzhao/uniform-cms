package com.nokia.ucms.common.controller;

import com.nokia.ucms.common.entity.ApiQueryResult;
import com.nokia.ucms.common.utils.StringUtil;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by x36zhao on 2017/3/3.
 */
public abstract class BaseController
{
    /**
     * Placeholder and must be replaced with valid query result
     *
     * @param <T>
     * @return
     */
    protected <T> ApiQueryResult<T> createEmptyQueryResult()
    {
        return new ApiQueryResult<T>(false, null);
    }

    /**
     * Creates error query result with exception message
     *
     * @param ex
     * @param <T>
     * @return
     */
    protected <T> ApiQueryResult<T> createErrorQueryResult (Exception ex)
    {
        return new ApiQueryResult<T>(false, null, ex.getMessage());
    }

    /**
     * Obtains all of the requested parameters
     *
     * @param request
     *         the http servlet request
     * @return all of requested parameters
     */
    protected Map<String, List<String>> getAllRequestParameters (HttpServletRequest request)
    {
        return this.getAllRequestParameters(request, null);
    }

    protected Object getAuthenticatedPrinciple()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null)
        {
            return principal;
        }

        return null;
    }

    /**
     * Obtains all of the requested parameters
     *
     * @param request
     *         the http servlet request
     * @param paramPrefixs
     *         list of the parameter prefixs
     * @return all of requested parameters
     */
    protected Map<String, List<String>> getAllRequestParameters (HttpServletRequest request, String[] paramPrefixs)
    {
        Map<String, List<String>> parameters = new HashMap<String, List<String>>();

        if (request != null)
        {
            Enumeration<String> paramNames = request.getParameterNames();

            while(paramNames.hasMoreElements())
            {
                String paramName = (String)paramNames.nextElement();
                String paramValue = request.getParameter(paramName);

                if (!parameters.containsKey(paramName))
                {
                    if (paramPrefixs != null && paramPrefixs.length > 0)
                    {
                        for (String paramPrefix : paramPrefixs)
                        {
                            if (paramName.startsWith(paramPrefix) && StringUtil.isNotEmpty(paramValue))
                            {
                                if (!parameters.containsKey(paramPrefix))
                                {
                                    List<String> paramVals = new ArrayList<String>();
                                    parameters.put(paramPrefix, paramVals);
                                }
                                parameters.get(paramPrefix).add(paramValue);
                            }
                        }
                    }
                    else
                    {
                        List<String> paramVals = new ArrayList<String>();
                        paramVals.add(paramValue);

                        parameters.put(paramName, paramVals);
                    }
                }
            }
        }

        return parameters;
    }

    protected String getModulePage(String pageName)
    {
        return String.format("%s/%s", getModulePath(), pageName);
    }

    protected abstract String getModulePath();
}
