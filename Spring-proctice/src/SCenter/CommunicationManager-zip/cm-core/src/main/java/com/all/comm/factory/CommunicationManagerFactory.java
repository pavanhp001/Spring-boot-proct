/**
 *
 */
package com.AL.comm.factory;

import com.AL.comm.manager.CommunicationManager;


 

/**
 * @author ebthomas
 *
 */
public interface CommunicationManagerFactory<T,U>
{
    public   CommunicationManager<T,U> createCommunicationManager(final String namespace);
}
