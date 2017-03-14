package com.nokia.ucms.common.entity;

import lombok.Data;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Data
public class ApiQueryResult<T>
{
    private boolean success;
    private T data;
    private String error;

    public ApiQueryResult(T data)
    {
        this(true, data);
    }

    public ApiQueryResult(String error)
    {
        this.error = error;
        this.success = false;
    }

    public ApiQueryResult(boolean success)
    {
        this(success, null);
    }

    public ApiQueryResult(boolean success, T data)
    {
        this(success, data, null);
    }

    public ApiQueryResult(boolean success, T data, String error)
    {
        this.success = success;
        this.error = error;
        this.data = data;
    }
}
