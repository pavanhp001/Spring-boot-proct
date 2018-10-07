/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.dto;

/**
 * Wraps any data being sent back to the client via an ajax call. We cater for
 * error responses that may need to be sent back on a successful (i.e. HTTP 200)
 * response, in addition to the object being passed back.
 *
 *  It is envisaged that instances of this class are serialised into JSON via
 *  Spring's bundled Jackson JSON library.
 *
 * @author cwalker
 */
public class JSONResponse {

    public static final String NO_DATA_FOUND_ERROR = "NO_DATA_FOUND";
    public static final String UPDATE_DATA_FAILED_ERROR = "UPDATE_DATA_FAILED";

    /** Any error string to return to the client. */
    private String error;
    /** The data to serialise to JSON. */
    private Object data;

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(final Object data) {
        this.data = data;
    }
}
