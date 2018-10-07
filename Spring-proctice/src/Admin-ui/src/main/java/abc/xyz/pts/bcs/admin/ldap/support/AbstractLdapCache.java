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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.naming.event.NamespaceChangeListener;
import javax.naming.event.NamingEvent;
import javax.naming.event.NamingExceptionEvent;
import javax.naming.event.ObjectChangeListener;

public abstract class AbstractLdapCache<T> implements ObjectChangeListener, NamespaceChangeListener {

    private Callable<T> callable = null;
    private FutureTask<T> futureTask = null;
    private boolean refreshCache = false;
    private boolean exceptionOccured = false;
    private boolean listenerRegistered = false;

    public AbstractLdapCache() {
        callable = createCallable();
        futureTask = new FutureTask<T>(callable);
    }

    public void run() {
        futureTask.run();
        listenerRegistered = true;
    }

    protected abstract Callable<T> createCallable();

    public boolean isListenerRegistrationRequired() {
        return exceptionOccured || !listenerRegistered;
    }

    public T get() throws ExecutionException, InterruptedException {
        if (refreshCache || exceptionOccured) {
            callable = createCallable();
            futureTask = new FutureTask<T>(callable);
            futureTask.run();
            refreshCache = false;
            exceptionOccured = false;
        }
        return futureTask.get();
    }

    private void modificationPerformed(final NamingEvent evt) {
        refreshCache = true;
    }

    @Override
    public void namingExceptionThrown(final NamingExceptionEvent evt) {
        exceptionOccured = true;
    }

    @Override
    public void objectChanged(final NamingEvent evt) {
        modificationPerformed(evt);
    }

    @Override
    public void objectAdded(final NamingEvent evt) {
        modificationPerformed(evt);
    }

    @Override
    public void objectRemoved(final NamingEvent evt) {
        modificationPerformed(evt);
    }

    @Override
    public void objectRenamed(final NamingEvent evt) {
        modificationPerformed(evt);
    }

}
