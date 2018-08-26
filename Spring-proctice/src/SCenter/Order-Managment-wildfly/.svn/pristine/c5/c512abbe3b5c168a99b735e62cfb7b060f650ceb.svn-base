/**
 *
 */
package com.AL.ome.error;

import junit.framework.TestCase;

import com.AL.validation.Message;
import com.AL.validation.ValidationReport;
import com.AL.validation.impl.DefaultReportFactory;
import com.AL.validation.impl.DefaultValidationReport;
import com.AL.validation.impl.OmeValidationHelper;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.StatusType;

/**
 * @author ebthomas
 * 
 */
public class ValidationReportTest extends TestCase
{

    /**
     * Default Validation Report.
     */
    public void testDefaultValidationReport()
    {
        // Create Report and add message
        ValidationReport validationReport = new DefaultValidationReport( new DefaultReportFactory() );
        validationReport.addMessage( Message.Type.ERROR, 1L, "Invalid Data", new Object() );

        // Get Newly created message
        Message message = validationReport.getMessage( "Invalid Data" );

        // Ensure message has required data
        assertEquals( Message.Type.ERROR, message.getType() );
        assertEquals( "Invalid Data", message.getMessageKey() );
        assertNotNull( message.getContextOrdered() );

        // Resolve Business Domain Status back to Web Service Domain Status
        OrderManagementRequestResponseDocument doc = OrderManagementRequestResponseDocument.Factory.newInstance();
        StatusType status = OmeValidationHelper.determineStatus( doc, validationReport );
        OmeValidationHelper.populateProcessingStatus( status, validationReport );

        // Ensure status message was updated properly
        assertEquals( "ERROR", status.getStatusMsg() );
        System.out.println( doc.xmlText() );
    }

}
