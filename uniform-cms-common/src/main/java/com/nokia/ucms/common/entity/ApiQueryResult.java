package com.nokia.ucms.common.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Getter
@Setter
public class ApiQueryResult<T>
{
    private boolean success;
    private T data;
    private String error;

    public ApiQueryResult(T data)
    {
        this.data = data;
    }

    public ApiQueryResult(boolean success, T data)
    {
        this(success, null, data);
    }

    public ApiQueryResult(boolean success, String error)
    {
        this(success, error, null);
    }

    public ApiQueryResult(boolean success, String error, T data)
    {
        this.success = success;
        this.error = error;
        this.data = data;
    }
}
