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
package abc.xyz.pts.bcs.common.web.userNavigation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an ordered collection of http request made by the user.
 *
 * @author tterry
 *
 */
public class UserNavigationTrail implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<RequestDetails> navigationTrail;

    /**
     * Constructor. Initialises the navigation trail.
     *
     */
    public UserNavigationTrail() {
        navigationTrail = new ArrayList<RequestDetails>();
    }

    /**
     * Add a users request to their navigation trail. This will insert a new item unless there is an existing request
     * for the same view. In this case it will be replaced.
     *
     * @param reqDetails
     */
    public void addRequestDetails(final RequestDetails reqDetails) {
        int foundItem = indexOfPageRequest(reqDetails.getPage());
        if (foundItem > -1) {
            // replace the found entry as we want one navigation entry per
            // spring view.
            navigationTrail.remove(foundItem);
            navigationTrail.add(reqDetails);
        } else {
            navigationTrail.add(reqDetails);
        }
    }

    /**
     * Finds the index of a previous web request for the specified view.
     *
     * @param page
     *            the name of the spring mvc view.
     * @return the index of a request for a duplicate view, otherwise -1
     */
    private int indexOfPageRequest(final String page) {
        int result = -1;
        // we loop through this backwards as duplicates are most likely to be in
        // the previous couple of requests and we fail fast.
        int size = navigationTrail.size() - 1;
        for (int i = size; i > -1; i--) {
            RequestDetails reqDetails = (RequestDetails) navigationTrail.get(i);
            if (reqDetails.getPage().equals(page)) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Clears all items from the users navigation trail.
     *
     */
    public void clear() {
        navigationTrail.clear();
    }

    /**
     * Gets the user's previous request.
     *
     * @return The previous request or null if there was none.
     */
    public RequestDetails getPreviousRequest() {
        RequestDetails result = null;
        if (isUserInTrail()) {
            result = (RequestDetails) navigationTrail.get(navigationTrail.size() - 2);
        }
        return result;
    }

    /**
     * Gets the user's previous request.
     *
     * @return The previous request or null if there was none.
     */
    public RequestDetails getCurrentRequest() {
        RequestDetails result = null;
        if (navigationTrail.size() > 0) {
            result = (RequestDetails) navigationTrail.get(navigationTrail.size() - 1);
        }
        return result;
    }

    /**
     * Removes the user's last request from the trail.
     *
     */
    public void removeLastRequest() {
        if (!navigationTrail.isEmpty()) {
            navigationTrail.remove(navigationTrail.size() - 1);
        }
    }

    /**
     * Finds if the user has started a navigation trail.
     *
     * @return true if the user is in a navigation trail.
     */
    public boolean isUserInTrail() {
        return (navigationTrail.size() > 1);
    }
}
