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
