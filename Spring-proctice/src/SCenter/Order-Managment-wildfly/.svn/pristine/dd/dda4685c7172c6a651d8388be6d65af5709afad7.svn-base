package com.AL.util.audit;
 
 
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
 

/**
* @author ebthomas
* 
*/
public final class AuditAdministrator
{
   private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool( 1 );
   private static ScheduledFuture< ? > schedulerHandle;

   private static Logger logger = Logger.getLogger( AuditAdministrator.class );
    
   static
   {

       final long timerOperationInterval = 15; //Minutes
       final long initialDelay = 0L;
       final TimeUnit unit = TimeUnit.MINUTES;

       Runnable command = new Runnable()
       {
           public void run()
           {
        	   logger.debug("Starting Audit Maintain.  Operation" );
               
               logger.debug("Completing Audit Maintain Operation  Completed" );
           }
       };

       AuditAdministrator.schedulerHandle = SCHEDULER.scheduleAtFixedRate( command, initialDelay, timerOperationInterval, unit );
   }

   public static AuditAdministrator getInstance()
   {
       return FACTORY.INSTANCE;
   }

   /**
    * @author ebthomas
    * 
    */
   private static final class FACTORY
   {
       private static final AuditAdministrator INSTANCE = new AuditAdministrator();

       /**
        * DEFAULT CONSTRUCTOR HIDDEN FROM EXTERIOR CREATION.
        */
       private FACTORY()
       {
           super();
       }

   }

   /**
    * Use FACTORY to create object.
    */
   private AuditAdministrator()
   {
       super();
   }

   /**
    * @return Scheduler Handle.
    */
   public ScheduledFuture< ? > getSchedulerHandle()
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

       logger.info("---->" + cal.getTime() + " ping <-----" );
   }

   
}
