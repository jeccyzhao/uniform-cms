package com.nokia.ucms.common.exception;

/**
 * Created by x36zhao on 2017/3/3.
 */
public class ServiceException extends RuntimeException
{
    public ServiceException (String message)
    {
        super(message);
    }

    public ServiceException (Throwable e)
    {
        super(e);
    }

    public ServiceException (String message, Exception e)
    {
        super(message, e);
    }

    public ServiceException (String message, Throwable e)
    {
        super(message, e);
    }
}
