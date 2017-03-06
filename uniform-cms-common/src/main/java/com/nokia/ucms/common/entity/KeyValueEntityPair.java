package com.nokia.ucms.common.entity;

import lombok.Data;
import lombok.ToString;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Data
@ToString
public class KeyValueEntityPair<K, V>
{
    private K key;
    private V value;

    public KeyValueEntityPair ()
    {
    }

    public KeyValueEntityPair (K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}
