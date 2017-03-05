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
     * @return
     */
    protected ApiQueryResult<Object> createEmptyQueryResult()
    {
        return new ApiQueryResult<Object>(false, null);
    }
}
