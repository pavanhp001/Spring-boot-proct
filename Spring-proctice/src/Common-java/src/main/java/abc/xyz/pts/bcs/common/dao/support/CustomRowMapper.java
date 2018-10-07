/* **************************************************************************
 *                                                            *
 * **************************************************************************
 * This code contains copyright information which is the proprietary property
 * of   Application Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Application Solutions.
 *
 * Copyright   Application Solutions 2008
 * All rights reserved.
 */
package abc.xyz.pts.bcs.common.dao.support;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.JdbcUtils;

public class CustomRowMapper<T> implements ParameterizedRowMapper<T> {



    protected static final Logger LOGGER = Logger.getLogger(CustomRowMapper.class);

    private final ConcurrentMap<Class<?>, Method[]> settersMap = new ConcurrentHashMap<Class<?>, Method[]>();

    private static final String ROW_NUM = "r";
    /** The class we are mapping to */
    private final Class<?> mappedClass;

    private static DatatypeFactory datatypeFactory;

    static {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (final DatatypeConfigurationException e) {
            LOGGER.error("Cannot create DatatypeFactory!", e);
        }
    }

    /**
     * Create a new CustomRowMapper.
     *
     * @param mappedClass
     *            the class that each row should be mapped to.
     */
    public CustomRowMapper(final Class<T> mappedClass) {
        this.mappedClass = mappedClass;
    }

    /**
     * Create the suffix of a bean setter method (i.e. the method name without the initial "set").
     *
     * @param name
     *            the name of the resultSet column to be mapped.
     */
    private String accessName(final String name) {
        final StringBuffer result = new StringBuffer();
        boolean doToUpper = true;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (doToUpper) {
                c = Character.toUpperCase(c);
            }
            if (c == '_') {
                doToUpper = true;
            } else {
                result.append(c);
                doToUpper = false;
            }
        }
        return result.toString();
    }

    @Override
    public T mapRow(final ResultSet rs, final int rowNumber) throws SQLException {
        ResultSetMetaData rsmd = null;
        int columnCount;

        Method[] setterMethods = settersMap.get(mappedClass);
        boolean cached = false;
        if (setterMethods == null) {
            rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();
            setterMethods = new Method[columnCount];
        } else {
            columnCount = setterMethods.length;
            cached = true;
        }

        @SuppressWarnings("unchecked")
        final
        T mappedObject = (T) BeanUtils.instantiateClass(this.mappedClass);

        for (int i = 0; i < columnCount; i++) {
            final int index = i + 1;

            Method setterMethod = null;
            if (!cached) {

                final String column = JdbcUtils.lookupColumnName(rsmd, index).toLowerCase();
                LOGGER.debug(" ------------- column to be set  -------------- "+column);
                
                if (!ROW_NUM.equals(column)) {
                    setterMethod = findSetter(column);
                    setterMethods[i] = setterMethod;
                }
            } else {
                setterMethod = setterMethods[i];
            }
            if (setterMethod != null) {
                final Class<?> argClass = setterMethod.getParameterTypes()[0];
                LOGGER.debug(" ------------- setting -------------- "+setterMethod+" --------------------- "+argClass.getName()+" -------------------- "+" ---- ");
                Object value = JdbcUtils.getResultSetValue(rs, index, argClass);
                if (value != null) {
                    final Class<?> sourceClass = value.getClass();
                    if (!argClass.isAssignableFrom(sourceClass)) {
                        value = convert(value, sourceClass, argClass);
                    }
                    final Object[] theData = { value };
                    try {
                        setterMethod.invoke(mappedObject, theData);
                    } catch (final Exception e) {
                        throw new DataRetrievalFailureException("Setter problems mapping column " + index
                                + ", type required: " + argClass + ", value: " + value);
                    }
                }
            }
        }

        if (!cached) {
            settersMap.put(mappedClass, setterMethods);
        }

        return mappedObject;
    }

    private Method findSetter(final String column) {
        final String accessName = accessName(column);
        final String setterName = new StringBuffer("set").append(accessName).toString();
        final Method[] methods = mappedClass.getMethods();
        for (final Method m : methods) {
            final String methodName = m.getName();
            if (setterName.equals(methodName)) {
                return m;
            }
        }
        throw new DataRetrievalFailureException("Unable to map column: " + column + " to property: " + accessName
                + " for bean: " + mappedClass.getCanonicalName());
    }

    @SuppressWarnings( { "unchecked", "hiding" })
    private <Object, T> T convert(final Object value, final Class<?> sourceClass, final Class<T> targetClass) {
        T result = null;
        if (value instanceof Number) {
            if (targetClass.isAssignableFrom(Number.class)) {
                final Number nv = (Number) value;
                if (targetClass == Long.class) {
                    result = (T) (Long.valueOf(nv.longValue()));
                } else if (targetClass == Double.class) {
                    result = (T) (new Double(nv.doubleValue()));
                } else {
                    LOGGER.error("Cannot convert numeric value: " + value + " from: " + sourceClass + " to: "
                            + targetClass);
                }
            }
        } else if (value instanceof Date) {
            final Date dateValue = (Date) value;
            final GregorianCalendar valueToReturn = (GregorianCalendar) Calendar.getInstance();
            valueToReturn.setTime(dateValue);
            if (targetClass == XMLGregorianCalendar.class) {
                result = (T) datatypeFactory.newXMLGregorianCalendar(valueToReturn);
            } else {
                result = (T) valueToReturn;
            }
        } else if (value instanceof Timestamp) {
            final Timestamp timestampValue = (Timestamp) value;
            final Calendar valueToReturn = Calendar.getInstance();
            final Date dateValue = new Date(timestampValue.getTime());
            valueToReturn.setTime(dateValue);
            result = (T) valueToReturn;
        }

        return result;
    }

}
