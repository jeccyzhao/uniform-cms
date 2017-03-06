package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.dto.TableColumnDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by x36zhao on 2017/3/4.
 */
public interface DatabaseAdminRepository
{
    Integer createTableIfNotExist(@Param("tableName") String tableName);
    Integer addTableColumn(
            @Param("tableName") String tableName,
            @Param("columnName") String columnName,
            @Param("columnLength") Integer columnLength);

    void removeTableColumn();

    List<Map<String, Object>> query(@Param("tableName") String tableName, @Param("categoryId") Integer categoryId);
}
