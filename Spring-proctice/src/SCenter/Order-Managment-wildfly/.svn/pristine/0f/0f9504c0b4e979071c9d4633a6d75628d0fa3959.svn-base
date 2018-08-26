package com.AL.service.impl;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.AL.Vdao.dao.SyncDao;
import com.AL.vm.service.SyncService;

/**
 * @author ebthomas
 *
 */

@Component
public class SyncServiceImpl implements SyncService
{
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool( 1 );
    private static ScheduledFuture<?> schedulerHandle;

    @Autowired
    private SyncDao syncDao;

   // @Autowired
   // private SystemPropertiesDao systemPropsDao;


    private static Logger logger = Logger.getLogger( SyncServiceImpl.class );

    public SyncServiceImpl()
    {
        super();
        load();
    }

    public void load()
    {
        final long timerOperationInterval = 1; // Minutes
        final long initialDelay = 0L;
        final TimeUnit unit = TimeUnit.MINUTES;

        Runnable command = new Runnable()
        {
            public void run()
            {
                syncDao.sync();
                //systemPropsDao.sync();
            }
        };

        //System.out.println( "************Start Timer Service ************************ " );
        SyncServiceImpl.schedulerHandle = SCHEDULER.scheduleAtFixedRate( command, initialDelay, timerOperationInterval, unit );


    }

    /**
     * @return Scheduler Handle.
     */
    public ScheduledFuture<?> getSchedulerHandle()
    {
        return schedulerHandle;
    }

    /**
     * Cancel the recurring scheduler.
     */
    public void cancelScheduler()
    {
        if ( schedulerHandle != null )
        {
            schedulerHandle.cancel( true );
        }

    }

    /**
     * Ensure that Context Administrator is operational.
     */
    public void ping()
    {
        Calendar cal = Calendar.getInstance();

        logger.debug( "---->" + cal.getTime() + " ping <-----" );
    }

    /**
     * {@inheritDoc}
     */
    public void sync()
    {
        load();
    }

}
