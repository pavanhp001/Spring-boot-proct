package com.A.ui.context.impl;

import java.util.Map;
import java.util.Set;

import com.A.ui.context.impl.TaskContextParamEnum;
import com.A.validation.Message;
import com.A.validation.ValidationReport;
 

public class ResponseItemCustomer extends ResponseItemBase implements
	OrchestrationContext {

    
    
    
    /**
     * @param consumerId
     *            address to add
     * @return success of add process
     */
    public Boolean addNotFound( final String id, final String context )
    { 
        try
        {  
            ValidationReport report = getValidationReport(  );
            report.addMessage( Message.Type.FATAL,  1L ,context+"# " + id + " does not exist!!" );
            if (getResponseList() != null) {
            	getResponseList().put(TaskContextParamEnum.statusReport.name(), report);
    		}
            this.putValidationReport(report);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;

    }
    
    
    /**
     * @param consumerId
     *            address to add
     * @return success of add process
     */
    public Boolean addInteractionNotFound( final String message )
    { 
        try
        {  
            ValidationReport report = getValidationReport(  );
            report.addMessage( Message.Type.FATAL,  1L ,message );
            if (getResponseList() != null) {
            	getResponseList().put(TaskContextParamEnum.statusReport.name(), report);
    		}
            this.putValidationReport(report);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;

    }
    
    
    /**
     * @param consumerId
     *            address to add
     * @return success of add process
     */
    public Boolean addAddressNotFound( final String consumerId )
    { 
        try
        {  
            ValidationReport report = getValidationReport(  );
            report.addMessage( Message.Type.FATAL,  1L ,"Address #" + consumerId + " does not exist!!" );
            if (getResponseList() != null) {
            	getResponseList().put(TaskContextParamEnum.statusReport.name(), report);
    		}
            this.putValidationReport(report);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;

    }
    
    

    /**
     * @param consumerId
     *            address to add
     * @return success of add process
     */
    public Boolean addContactsNotFound( final Map<String,String> contacts)
    { 
        try
        {  
            ValidationReport report = getValidationReport(  );
            StringBuilder message = new StringBuilder("No Customer found with following Contact Information \n");
            Set<String> keys = contacts.keySet();
            for(String key : keys){
            	if(contacts.get( key ) != null)
            	{
            		message.append( key + " : " + contacts.get( key ) );
            		message.append ("\n");
            	}
            }
            report.addMessage( Message.Type.FATAL,  1L , message.toString());
            if (getResponseList() != null) {
            	getResponseList().put(TaskContextParamEnum.statusReport.name(), report);
    		}
            this.putValidationReport(report);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;

    }
    
    /**
     * @param consumerId
     *            sales order to add
     * @return success of add process
     */
    public Boolean addConsumerNotFound( final String consumerId )
    { 
        try
        {  
            ValidationReport report = getValidationReport(  );
            report.addMessage( Message.Type.FATAL,  1L ,"Consumer #" + consumerId + " does not exist!!" );
            if (getResponseList() != null) {
            	getResponseList().put(TaskContextParamEnum.statusReport.name(), report);
    		}
            this.putValidationReport(report);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;

    }
    
    /**
     * @param consumerId
     *            sales order to add
     * @return success of add process
     */
    public Boolean addConsumersNotFound( )
    { 
        try
        {  
            ValidationReport report = getValidationReport(  );
            report.addMessage( Message.Type.FATAL,  1L ,"No consumer found matching search criteria !!" );
            if (getResponseList() != null) {
            	getResponseList().put(TaskContextParamEnum.statusReport.name(), report);
    		}
            this.putValidationReport(report);
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;

    }

    /**
     * Task Response Object. Contains the response result of the task
     */
    public ResponseItemCustomer()
    {
       super();
    }
}
