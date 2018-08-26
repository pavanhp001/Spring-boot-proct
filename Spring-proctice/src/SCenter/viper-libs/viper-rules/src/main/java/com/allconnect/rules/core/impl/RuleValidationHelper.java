package com.A.rules.core.impl;

import org.drools.runtime.KnowledgeRuntime;
import org.drools.runtime.rule.RuleContext;
import com.A.validation.Message;
import com.A.validation.ReportFactory;
import com.A.validation.ValidationReport;

/**
 * @author ebthomas
 * 
 */
public final class RuleValidationHelper
{
    private static final String VALIDATION_REPORT_NAME = "validationReport";
    private static final String REPORT_FACTORY_NAME = "reportFactory";

    /**
     * Rule Validation Helper functions.
     */
    private RuleValidationHelper()
    {
        super();
    }

    /**
     * @param drools drools Rule Context
     * @return Validation Report 
     */
    public ValidationReport getValidationReport( final RuleContext drools )
    {
        KnowledgeRuntime knowledgeRuntime = drools.getKnowledgeRuntime();
        return (ValidationReport) knowledgeRuntime.getGlobal( VALIDATION_REPORT_NAME );

    }
    
    /**
     * @param drools
     *            rule context
     * @param context
     *            objects to add to rule context
     */
    public static void outcome( final RuleContext drools, final String desc, final Object... context )
    {
        KnowledgeRuntime knowledgeRuntime = drools.getKnowledgeRuntime();
        ValidationReport validationReport = (ValidationReport) knowledgeRuntime.getGlobal( VALIDATION_REPORT_NAME );
        ReportFactory reportFactory = (ReportFactory) knowledgeRuntime.getGlobal( REPORT_FACTORY_NAME );
 
        validationReport.addMessage( reportFactory.createMessage( Message.Type.OUTCOME,
                Long.valueOf( Message.Type.OUTCOME.getCode() ), drools.getRule().getName()+"."+desc, context ) ); 
    }
    
    

    /**
     * @param drools
     *            rule context
     * @param context
     *            objects to add to rule context
     */
    public static void error( final RuleContext drools, final String desc, final Object... context )
    {
        KnowledgeRuntime knowledgeRuntime = drools.getKnowledgeRuntime();
        ValidationReport validationReport = (ValidationReport) knowledgeRuntime.getGlobal( VALIDATION_REPORT_NAME );
        ReportFactory reportFactory = (ReportFactory) knowledgeRuntime.getGlobal( REPORT_FACTORY_NAME );

        validationReport.addMessage( reportFactory.createMessage( Message.Type.ERROR,
                Long.valueOf( Message.Type.WARNING.getCode() ), drools.getRule().getName()+"."+desc, context ) );
    }

    /**
     * @param drools
     *            rule context
     * @param context
     *            objects to add to context
     */
    public static void warning( final RuleContext drools, final String desc, final Object... context )
    {
        KnowledgeRuntime knowledgeRuntime = drools.getKnowledgeRuntime();
        ValidationReport validationReport = (ValidationReport) knowledgeRuntime.getGlobal( VALIDATION_REPORT_NAME );
        ReportFactory reportFactory = (ReportFactory) knowledgeRuntime.getGlobal( REPORT_FACTORY_NAME );

        if (reportFactory != null) {
        validationReport.addMessage( reportFactory.createMessage( Message.Type.WARNING, Long.valueOf( Message.Type.WARNING
                .getCode() ), drools.getRule().getName()+"."+desc, context ) );
        }
    }

    /**
     * @param drools
     *            rule context
     * @param context
     *            objects to add to context
     */
    public static void info( final RuleContext drools,final String desc, final Object... context )
    {
    	KnowledgeRuntime knowledgeRuntime = drools.getKnowledgeRuntime();
        ValidationReport validationReport = (ValidationReport) knowledgeRuntime.getGlobal( VALIDATION_REPORT_NAME );
        ReportFactory reportFactory = (ReportFactory) knowledgeRuntime.getGlobal( REPORT_FACTORY_NAME );

        validationReport.addMessage( reportFactory.createMessage( Message.Type.INFO,
                Long.valueOf( Message.Type.INFO.getCode() ), drools.getRule().getName()+"."+desc, context ) );
    }
    
   
}
