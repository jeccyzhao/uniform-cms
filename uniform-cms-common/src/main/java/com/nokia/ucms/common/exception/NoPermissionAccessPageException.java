package com.nokia.ucms.common.exception;

/**
 * Created by x36zhao on 2017/3/3.
 */
public class NoPermissionAccessPageException extends RuntimeException
{
    public NoPermissionAccessPageException (String message)
    {
        super(message);
    }

    public NoPermissionAccessPageException (Throwable e)
    {
        super(e);
    }

    public NoPermissionAccessPageException (String message, Exception e)
    {
        super(message, e);
    }

    public NoPermissionAccessPageException (String message, Throwable e)
    {
        super(message, e);
    }
}
