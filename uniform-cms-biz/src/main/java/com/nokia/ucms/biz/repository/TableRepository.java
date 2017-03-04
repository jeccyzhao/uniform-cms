package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.dto.TableColumnDTO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by x36zhao on 2017/3/4.
 */
public interface TableRepository
{
    Integer createTableIfNotExist(@Param("tableName") String tableName);
    Integer addTableColumn(TableColumnDTO tableColumn);

    void removeTableColumn();
}
