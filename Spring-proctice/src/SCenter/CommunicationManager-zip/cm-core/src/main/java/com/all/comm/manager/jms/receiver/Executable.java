/**
 *
 */
package com.AL.comm.manager.jms.receiver;

import java.util.Map;

/**
 * @author ebthomas
 *
 */
public interface Executable<T, U, V>
{
    // ===================Message Header Keys =====================================
    public final String MSG_TYPE_KEY = "messageType";
    public final String MSG_VERSION_KEY = "messageVersion";
    public final String SENDER_KEY = "sender";
    public final String DESIGNATION_KEY = "designation";
    
    // Web Decisions outbound error 
    public final String WD_ERROR_TYPE = "wdErrorType";

    // ===================Message Header Keys =====================================
    
    // ===================Message Header Values ===================================
    // Message Types
    public final String ORDER_TYPE = "OrderManagementRequestResponseDocument";
    public final String CUST_TYPE = "CustomerManagementRequestResponseDocument";
    public final String CCP_TYPE = "CCPRequestResponseDocument";
    public final String PUF_TYPE = "PendingUserFeedbackResponseDocument";
    public final String LINE_ITEM_TYPE = "LineItemManagementRequestResponseDocument";
    
    // Message Versions
    public final String MSG_VER_V4 = "v4";
    public final String MSG_VER_V5 = "v5";
    
    // Web Decisions Error Values
    public final String WD_SERVER_DOWN = "SERVER_DOWN";
    public final String WD_OTHER_ISSUE = "OTHER_ISSUE";
    // ===================Message Header Values ===================================
    
    // Main entry for all endpoint clients
    public V execute(final T arg, final Map<String, String> map );
    
}
