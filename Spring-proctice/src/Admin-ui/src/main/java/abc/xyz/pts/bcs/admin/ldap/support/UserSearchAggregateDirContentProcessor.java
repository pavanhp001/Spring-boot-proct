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

// Copyright 2005-2008 the original author or authors.

/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at http:// www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

import org.springframework.ldap.control.VirtualListViewResultsCookie;
import org.springframework.ldap.core.support.AggregateDirContextProcessor;

import abc.xyz.pts.bcs.common.ldap.support.OrderingSortControlDirContextProcessor;

/**
 * AggregateDirContextProcessor implementation for managing a virtual list view by aggregating DirContextProcessor
 * implementations for a VirtualListViewControl and its required companion SortControl.
 *
 * @author Mattias Hellborg Arthursson
 * @author Ulrik Sandberg
 * @author Marius Scurtescu
 */
public class UserSearchAggregateDirContentProcessor extends AggregateDirContextProcessor {

    private OrderingSortControlDirContextProcessor sortControlDirContextProcessor;
    private OrderingVlvcDirContextProcessor virtualListViewControlDirContextProcessor;
    private AccountUsabilityDirContextProcessor accountUsabilityDirContextProcessor;

    public UserSearchAggregateDirContentProcessor(final String sortKey, final boolean ascendingOrder, final int pageSize) {
        this(new OrderingSortControlDirContextProcessor(sortKey, ascendingOrder), new OrderingVlvcDirContextProcessor(
                pageSize), new AccountUsabilityDirContextProcessor());
    }

    public UserSearchAggregateDirContentProcessor(final String sortKey, final boolean ascendingOrder,
            final int pageSize, final int targetOffset, final int listSize, final VirtualListViewResultsCookie cookie) {
        this(new OrderingSortControlDirContextProcessor(sortKey, ascendingOrder), new OrderingVlvcDirContextProcessor(
                pageSize, targetOffset, listSize, cookie), new AccountUsabilityDirContextProcessor());
    }

    public UserSearchAggregateDirContentProcessor(
            final OrderingSortControlDirContextProcessor sortControlDirContextProcessor,
            final OrderingVlvcDirContextProcessor virtualListViewControlDirContextProcessor,
            final AccountUsabilityDirContextProcessor accountUsabilityDirContextProcessor) {
        this.sortControlDirContextProcessor = sortControlDirContextProcessor;
        this.virtualListViewControlDirContextProcessor = virtualListViewControlDirContextProcessor;
        this.accountUsabilityDirContextProcessor = accountUsabilityDirContextProcessor;
        addDirContextProcessor(this.accountUsabilityDirContextProcessor);
        addDirContextProcessor(this.sortControlDirContextProcessor);
        addDirContextProcessor(this.virtualListViewControlDirContextProcessor);
    }

    public VirtualListViewResultsCookie getCookie() {
        return this.virtualListViewControlDirContextProcessor.getCookie();
    }
}
