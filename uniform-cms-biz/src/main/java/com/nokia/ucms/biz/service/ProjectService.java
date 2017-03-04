package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.dto.TableColumnDTO;
import com.nokia.ucms.biz.repository.DatabaseAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by x36zhao on 2017/3/5.
 */
@Service
public class ProjectService
{
    @Autowired
    private DatabaseAdminRepository dbAdminRepository;

    public Integer createProject (String tableName)
    {
        return dbAdminRepository.createTableIfNotExist(tableName);
    }

    public Integer addProjectField (final TableColumnDTO tableColumnDTO)
    {
        if (tableColumnDTO != null)
        {
            return dbAdminRepository.addTableColumn(tableColumnDTO.getTableName(),
                    tableColumnDTO.getColumnName(), tableColumnDTO.getColumnLength());
        }

        return null;
    }

}
