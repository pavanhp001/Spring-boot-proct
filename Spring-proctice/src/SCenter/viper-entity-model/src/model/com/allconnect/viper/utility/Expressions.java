/**
 *
 */
package com.A.V.utility;


/**
 * @author ebaugh
 *
 */
public abstract class Expressions
{
    /**
     * Default Constructor.
     */
    private Expressions()
    {
        // Do nothing.
    }

    /**
     * 
     * @param el El expression.
     * @param objClass Class that binds to the EL Expression.
     * @return value expression.
     */
    /*public static final ValueExpression generateExpression1( final String el, final Class< ? > objClass )
    {
        String finalEl = "#{" + el + "}";

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        Application application = facesContext.getApplication();
        ExpressionFactory factory = application.getExpressionFactory();
        ValueExpression expression = factory.createValueExpression( elContext, finalEl, objClass );
        return expression;
    }

    *//**
     * 
     * @param el El expression.
     * @return method expression.
     *//*
    public static final MethodExpression generateMethodExpression1( final String el )
    {
        String finalEl = "#{" + el + "}";

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        Application application = facesContext.getApplication();
        ExpressionFactory factory = application.getExpressionFactory();
        MethodExpression expression = factory.createMethodExpression( elContext, finalEl, null, new Class< ? >[0] );
        return expression;
    }*/

}
