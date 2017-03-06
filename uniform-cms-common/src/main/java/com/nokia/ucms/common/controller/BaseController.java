package com.nokia.ucms.common.controller;

import com.nokia.ucms.common.entity.ApiQueryResult;

/**
 * Created by x36zhao on 2017/3/3.
 */
public class BaseController
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
}
