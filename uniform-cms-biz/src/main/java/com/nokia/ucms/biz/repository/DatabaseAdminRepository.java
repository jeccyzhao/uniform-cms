package com.nokia.ucms.biz.repository;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by x36zhao on 2017/3/4.
 */
public interface DatabaseAdminRepository
{
    /**
     * The specified map must contain below keys,
     *
     * key: id
     * key: tableName
     * key: columnIds
     * key: columnValues
     *
     * @param mapParams
     * @return
     */
    Integer insertByMap(Map mapParams);

    Integer insertByProps(
            @Param("tableName") String tableName,
            @Param("columnIds") String columnIds,
            @Param("columnValues") String columnValues);

    Integer createTableIfNotExist(
            @Param("tableName") String tableName);

    Integer addTableColumn(
            @Param("tableName") String tableName,
            @Param("columnName") String columnName,
            @Param("columnLength") Integer columnLength);

    Integer removeTableColumn(
            @Param("tableName") String tableName,
            @Param("columnId") String columnId);

    Integer delete(
            @Param("tableName") String tableName,
            @Param("id") Integer id);

    Integer update (
            @Param("tableName") String tableName);

    List<Map<String, Object>> getRecordByCategory(
            @Param("tableName") String tableName,
            @Param("categoryId") Integer categoryId);

    Map<String, Object> getRecordById(
            @Param("tableName") String tableName,
            @Param("id") Integer recordId);

}
