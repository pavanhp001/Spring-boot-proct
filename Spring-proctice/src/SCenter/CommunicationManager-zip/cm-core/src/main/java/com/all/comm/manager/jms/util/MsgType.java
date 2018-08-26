package com.AL.comm.manager.jms.util;

/**
 * Standard message construct to ensure
 * portability when passing message headers
 * to various services. To be used by both
 * sender and receiver when identifying 
 * incoming and outgoing messages.
 * 
 * 
 * @author klyons
 *
 */
public enum MsgType
{
    ORDER_TYPE,    
    CUSTOMER_TYPE,
    UNKNOWN_TYPE,   
    V4,
    V5
}
