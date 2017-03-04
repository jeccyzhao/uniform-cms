package com.nokia.ucms.biz.service;

import com.nokia.ucms.biz.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by x36zhao on 2017/3/4.
 */
@Service
public class DatabaseService
{
    @Autowired
    private TableRepository tableRepository;

    public Integer createTable (String tableName)
    {
        return tableRepository.createTableIfNotExist(tableName);
    }

    public void addTableColumn ()
    {

    }
}
