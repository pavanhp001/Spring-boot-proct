/**
 *
 */
package com.AL.ie.ws.impl;

 

import org.apache.log4j.Logger;

 
 

/**
 * @author ebthomas
 *
 */
public class IeHandlerBase
{
 
    private static Logger logger = Logger.getLogger(IeHandlerBase.class);
    
    
    
    private static final String SERVICE_TASK_PREFIX = "Task";
    
    /**
     * @param taskname
     *            name of task to be transformed from method format to class format
     * @return transformed name
     */
    protected String transformTaskName( final String taskname )
    {
    	logger.debug("transforming.taskname.to.find.component:"+taskname);

        if ( ( taskname == null ) || ( taskname.length() == 0 ) )
        {
            throw new IllegalArgumentException( "invalid taskname value" );
        }

        String newTaskName = SERVICE_TASK_PREFIX + taskname.substring( 0, 1 ).toUpperCase() + taskname.substring( 1 );
        
        logger.debug("transformed.taskname.to.component.name:"+newTaskName);
        
        return newTaskName;

    }
    
    

}
