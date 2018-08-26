package com.AL.task;

import javax.ejb.Local;
import javax.ejb.Remote;
 

/**
 * @author ebthomas
 * 
 * @param <T>
 */
@Local
@Remote
public interface Task<T>
{
    /**
     * @param doc xml document that will be parsed.
     * @return return processed document
     */
    T execute( T doc );

    
}
