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
package abc.xyz.pts.bcs.admin.ldap.support;

/*
 * Copyright 2005-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

import java.lang.reflect.Method;

import javax.naming.NamingException;
import javax.naming.ldap.Control;

import org.springframework.ldap.control.AbstractFallbackRequestAndResponseControlDirContextProcessor;
import org.springframework.ldap.control.VirtualListViewResultsCookie;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.util.ReflectionUtils;

/**
 * DirContextProcessor implementation for managing a virtual list view.
 * <p>
 * This is the request control syntax:
 *
 * <pre>
 * VirtualListViewRequest ::= SEQUENCE {
 *        beforeCount    INTEGER (0..maxInt),
 *        afterCount     INTEGER (0..maxInt),
 *        target       CHOICE {
 *                       byOffset        [0] SEQUENCE {
 *                            offset          INTEGER (1 .. maxInt),
 *                            contentCount    INTEGER (0 .. maxInt) },
 *                       greaterThanOrEqual [1] AssertionValue },
 *        contextID     OCTET STRING OPTIONAL }
 * </pre>
 *
 * <p>
 * This is the response control syntax:
 *
 * <pre>
 * VirtualListViewResponse ::= SEQUENCE {
 *        targetPosition    INTEGER (0 .. maxInt),
 *        contentCount     INTEGER (0 .. maxInt),
 *        virtualListViewResult ENUMERATED {
 *             success (0),
 *             operationsError (1),
 *             protocolError (3),
 *             unwillingToPerform (53),
 *             insufficientAccessRights (50),
 *             timeLimitExceeded (3),
 *             adminLimitExceeded (11),
 *             innapropriateMatching (18),
 *             sortControlMissing (60),
 *             offsetRangeError (61),
 *             other(80),
 *             ... },
 *        contextID     OCTET STRING OPTIONAL }
 * </pre>
 *
 * @author Ulrik Sandberg
 * @author Marius Scurtescu
 * @see <a href="http://www3.ietf.org/proceedings/02nov/I-D/draft-ietf-ldapext-ldapv3-vlv-09.txt">LDAP Extensions for Scrolling View Browsing of Search Results</a>
 */

public class OrderingVlvcDirContextProcessor extends AbstractFallbackRequestAndResponseControlDirContextProcessor {
    private static final String DEFAULT_REQUEST_CONTROL = "com.sun.jndi.ldap.ctl.VirtualListViewControl";
    private static final String DEFAULT_RESPONSE_CONTROL = "com.sun.jndi.ldap.ctl.VirtualListViewResponseControl";

    private static final boolean CRITICAL_CONTROL = true;

    private int pageSize;

    private VirtualListViewResultsCookie cookie;

    private int listSize;

    private int targetOffset;

    private NamingException exception;

    private boolean offsetPercentage;

    public OrderingVlvcDirContextProcessor(final int pageSize) {
        this(pageSize, 1, 0, null);
    }

    public OrderingVlvcDirContextProcessor(final int pageSize, final int targetOffset, final int listSize,
            final VirtualListViewResultsCookie cookie) {
        this.pageSize = pageSize;
        this.targetOffset = targetOffset;
        this.listSize = listSize;
        this.cookie = cookie;

        defaultRequestControl = DEFAULT_REQUEST_CONTROL;
        defaultResponseControl = DEFAULT_RESPONSE_CONTROL;
        fallbackRequestControl = DEFAULT_REQUEST_CONTROL;
        fallbackResponseControl = DEFAULT_RESPONSE_CONTROL;

        loadControlClasses();
    }

    public VirtualListViewResultsCookie getCookie() {
        return cookie;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getListSize() {
        return listSize;
    }

    public NamingException getException() {
        return exception;
    }

    public int getTargetOffset() {
        return targetOffset;
    }

    /**
     * Set whether the <code>targetOffset</code> should be interpreted as percentage of the list or an offset into the
     * list.
     *
     * @param isPercentage
     *            <code>true</code> if targetOffset is a percentage
     */
    public void setOffsetPercentage(final boolean isPercentage) {
        this.offsetPercentage = isPercentage;
    }

    public boolean isOffsetPercentage() {
        return offsetPercentage;
    }

    /*
     * @see org.springframework.ldap.control.AbstractRequestControlDirContextProcessor#createRequestControl()
     */
    public Control createRequestControl() {
        Control control;

        if (offsetPercentage) {
            control = super.createRequestControl(new Class[] { int.class, int.class, boolean.class }, new Object[] {
                    Integer.valueOf(targetOffset), Integer.valueOf(pageSize), Boolean.valueOf(CRITICAL_CONTROL) });
        } else {
            control = super.createRequestControl(new Class[] { int.class, int.class, int.class, int.class,
                    boolean.class }, new Object[] { Integer.valueOf(targetOffset), Integer.valueOf(listSize),
                    Integer.valueOf(0), Integer.valueOf(pageSize - 1), Boolean.valueOf(CRITICAL_CONTROL) });
        }

        if (cookie != null) {
            invokeMethod("setContextID", requestControlClass, control, new Class[] { byte[].class },
                    new Object[] { cookie.getCookie() });
        }

        return control;
    }

    protected void handleResponse(final Object control) {
        byte[] result = (byte[]) invokeMethod("getContextID", responseControlClass, control);
        Integer listSize = (Integer) invokeMethod("getListSize", responseControlClass, control);
        Integer targetOffset = (Integer) invokeMethod("getTargetOffset", responseControlClass, control);
        this.exception = (NamingException) invokeMethod("getException", responseControlClass, control);

        this.cookie = new VirtualListViewResultsCookie(result, targetOffset.intValue(), listSize.intValue());

        if (exception != null) {
            throw LdapUtils.convertLdapException(exception);
        }
    }

    @SuppressWarnings("unchecked")
    protected static Object invokeMethod(final String methodName, final Class clazz, final Object control,
            final Class[] paramTypes, final Object[] paramValues) {
        Method method = ReflectionUtils.findMethod(clazz, methodName, paramTypes);

        return ReflectionUtils.invokeMethod(method, control, paramValues);
    }
}
