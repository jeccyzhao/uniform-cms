package com.nokia.ucms.portal.schedule;

import com.nokia.ucms.biz.service.SystemConfigService;
import com.nokia.ucms.common.utils.DatabaseUtil;
import static com.nokia.ucms.biz.constants.Constants.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by x36zhao on 2017/4/11.
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledDumpDatabase
{
    private static Logger LOGGER = Logger.getLogger(ScheduledDumpDatabase.class);

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * Dump database on 20:00 PM every day
     */
    @Scheduled(cron = "${database.cron.expression}")
    public void scheduleDatabaseDump()
    {
        LOGGER.info("Scheduled task to start dump database");

        String dumpFolder = systemConfigService.getPropertyValueByName(PROP_BACKUP_PATH, DEFAULT_BACKUP_FOLDER);
        String dumpFile = DatabaseUtil.dumpDatabase(DATABASE_NAME, dumpFolder);
        if (dumpFile != null)
        {
            LOGGER.info("Database dumped on " + dumpFile);
        }
        else
        {
            LOGGER.warn("Failed to dump database");
        }
    }
}
