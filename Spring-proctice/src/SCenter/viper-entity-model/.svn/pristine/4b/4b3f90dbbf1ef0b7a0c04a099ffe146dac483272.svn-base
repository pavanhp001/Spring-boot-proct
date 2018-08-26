package com.A.V.interfaces;

import com.A.V.beans.CapabilitiesRecord;
import com.A.V.beans.entity.AddressBean;


/**
 * 
 * @author ebaugh
 *
 */
public interface RealTimeCapabilitiesInterface
{
    /**
     * Standard method for returning the capabilities at a given address.
     * @param address addressBean to check.
     * @return a capabilities record for that address.
     */
    CapabilitiesRecord getCapabilitesAtAddress( AddressBean address );
    

    /**
     * This method preprocesses the passed address to fit the needs of the
     * particular integration partner. This should only be called by the implementing
     * class itself, and never anyone who instantiates it.
     * @param address the AddressBean that callout is to be performed on
     * @return the AddressBean as needed for the external callout
     */
    AddressBean preprocessAddress( AddressBean address );

}
