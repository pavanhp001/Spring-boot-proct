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

package abc.xyz.pts.bcs.common.audit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import abc.xyz.pts.bcs.common.audit.business.Event;

/**
 * The groupProperty is the name of a getter method on your class (minus the get) and
 * the property value is the expected value from the getter that will make this group
 * active.
 *
 * eg.  if you have groupProperty="action" propertyValue="selectThisGroup"
 *
 * Then when Audit is called, the audit aspect will use reflection to call getAction() on
 * your class.  If the returned value from this is "selectThisGroup" then the group will be considered active.
 *
 * NOTE - the code loops through the groups until it finds the first active group - so
 * if you have 2 potential active groups, only the first one matching will be considered active.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface AuditPropertyGroup {
    Event name();

    String groupProperty();

    String propertyValue();
}
