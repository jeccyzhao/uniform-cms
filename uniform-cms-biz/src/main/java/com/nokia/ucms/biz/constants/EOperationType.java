package com.nokia.ucms.biz.constants;

/**
 * Created by x36zhao on 2017/3/9.
 */
public enum EOperationType
{
    OPERATION_ADD(0),
    OPERATION_UPDATE(1),
    OPERATION_DEL(2);

    private int code;
    EOperationType (int code)
    {
        this.code = code;
    }

    public int getCode()
    {
        return this.code;
    }
}
