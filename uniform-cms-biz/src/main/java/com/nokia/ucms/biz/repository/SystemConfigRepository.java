package com.nokia.ucms.biz.repository;

import com.nokia.ucms.biz.entity.SystemConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by x36zhao on 2017/3/16.
 */
public interface SystemConfigRepository
{
    SystemConfig getConfigByName (@Param("name") String propertyName);
    SystemConfig getConfigById (@Param("id") Integer id);
    List<SystemConfig> getAllConfigs();
    Integer deleteConfigById (@Param("id") Integer id);
    Integer deleteConfigByName (@Param("name") String propertyName);
    Integer insertConfig(final SystemConfig systemConfig);
    Integer updateConfig(final SystemConfig systemConfig);
}
