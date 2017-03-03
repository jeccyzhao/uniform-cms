package com.nokia.ucms.common.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by x36zhao on 2017/3/3.
 */
@Getter
@Setter
public class EntityPair<K, V>
{
    private K first;
    private V second;

    public String toString()
    {
        return String.format("EntityPair [first: %s, second: %s]", first, second);
    }
}
