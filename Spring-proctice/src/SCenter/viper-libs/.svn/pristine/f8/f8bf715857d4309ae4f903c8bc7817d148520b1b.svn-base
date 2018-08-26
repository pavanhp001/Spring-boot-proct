package com.A.validation;

/**
 * @author ebthomas
 * 
 */
public interface ReportFactory
{
    /**
     * @return Validation Report
     */
    ValidationReport createValidationReport();

    /**
     * @param type
     *            type of message
     * @param messageKey
     *            message Key
     * @param context
     *            context list
     * @return Message
     */
    Message createMessage( final Message.Type type, final Long messageCode, final String messageKey, final Object... context );
}
