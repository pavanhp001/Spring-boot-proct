package com.A.ui.context.impl;

import java.util.Arrays;

import com.A.validation.Message;
import com.A.validation.ReportFactory;
import com.A.validation.ValidationReport;

/**
 * @author ebthomas
 * 
 */
public class DefaultReportFactory implements ReportFactory
{

    /**
     * Default Report Factory.
     */
    public DefaultReportFactory()
    {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public Message createMessage( final Message.Type type, final Long code, final String key, final Object... context )
    {
        return new DefaultMessage( type, code, key, Arrays.asList( context ) );
    }

    /**
     * {@inheritDoc}
     */
    public ValidationReport createValidationReport()
    {
        return new DefaultValidationReport();
    }
}
