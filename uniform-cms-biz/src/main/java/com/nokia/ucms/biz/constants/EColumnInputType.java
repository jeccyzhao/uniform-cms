package com.nokia.ucms.biz.constants;

/**
 * Created by x36zhao on 2017/3/13.
 */
public enum EColumnInputType
{
    COLUMN_IN_TYPE_TEXTBOX(0, "TEXT"),
    COLUMN_IN_TYPE_TEXTAREA(1, "TEXTAREA"),
    COLUMN_IN_TYPE_CHECKBOX(2, "CHECKBOX"),
    COLUMN_IN_TYPE_RADIO(3, "RADIO"),
    COLUMN_IN_TYPE_DROPDOWN(4, "SELECT");

    private int code;
    private String label;
    EColumnInputType (int code, String label)
    {
        this.code = code;
        this.label = label;
    }

    public int getCode()
    {
        return this.code;
    }

    public String getLabel()
    {
        return this.label;
    }
}
