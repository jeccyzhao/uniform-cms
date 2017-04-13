package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.entity.SystemConfig;
import com.nokia.ucms.biz.repository.SystemConfigRepository;
import com.nokia.ucms.common.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemConfigService
{
    private static Logger LOGGER = Logger.getLogger(SystemConfigService.class);

    @Autowired
    private SystemConfigRepository sysConfigRepository;

    public SystemConfig findByPropertyName (String propName)
    {
        return sysConfigRepository.getConfigByName(propName);
    }

    public String getPropertyValueByName (String propName, String defaultValue)
    {
        SystemConfig systemConfig = findByPropertyName(propName);
        if (systemConfig != null && systemConfig.getPropertyValue() != null && !"".equals(systemConfig.getPropertyValue()))
        {
            return systemConfig.getPropertyValue();
        }

        return defaultValue;
    }

    public SystemConfig findById (int id)
    {
        return sysConfigRepository.getConfigById(id);
    }

    public List<SystemConfig> findAll ()
    {
        return sysConfigRepository.getAllConfigs();
    }

    public SystemConfig saveOrUpdate (SystemConfig systemConfig)
    {
        if (systemConfig != null && systemConfig.getPropertyName() != null)
        {
            SystemConfig entityByName = findByPropertyName(systemConfig.getPropertyName());
            if (entityByName == null)
            {
                return addConfig(systemConfig);
            }

            systemConfig.setId(entityByName.getId());
            return updateConfig(systemConfig);
        }

        throw new ServiceException("Invalid config to save or update: " + systemConfig);
    }

    private SystemConfig addConfig (SystemConfig sysConfig)
    {
        if (sysConfig != null && sysConfig.getPropertyName() != null)
        {
            Integer result = this.sysConfigRepository.insertConfig(sysConfig);
            if (result > 0)
            {
                return sysConfig;
            }

            throw new ServiceException("Failed to add system config: " + sysConfig);
        }

        throw new ServiceException("Invalid config to be added: " + sysConfig);
    }

    private SystemConfig updateConfig (SystemConfig sysConfig)
    {
        if (sysConfig != null && sysConfig.getId() != null)
        {
            Integer result = this.sysConfigRepository.updateConfig(sysConfig);
            if (result > 0)
            {
                return sysConfig;
            }

            throw new ServiceException("Failed to update system config: " + sysConfig);
        }

        throw new ServiceException("Invalid config to be updated: " + sysConfig);
    }

    public int removeConfigById (int configId)
    {
        return sysConfigRepository.deleteConfigById(configId);
    }

    public int removeConfigByName (String propName)
    {
        SystemConfig entity = findByPropertyName(propName);
        if (entity != null)
        {
            return removeConfigById(entity.getId());
        }

        return -1;
    }

}
