/* This code contains copyright information which is the proprietary property
 * of   Advanced Travel Solutions. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of
 *   Advanced Travel Solutions.
 *
 * Copyright   Advanced Travel Solutions 2011
 * Confidential. All rights reserved.
 */
package abc.xyz.pts.bcs.common.web.userNavigation;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * This class implements the Wrapper or Decorator pattern.
 *
 * Methods default to calling through to the wrapped request object, except the ones that read the request's content
 * (parameters, stream or reader).
 *
 *
 * This class provides a buffered content reading that allows the methods * {@link #getReader()},
 * {@link #getInputStream()} and any of the getParameterXXX to be called * safely and repeatedly with the same results.
 * *
 *
 * This class is intended to wrap relatively small HttpServletRequest instances.
 */
public class UpdateParameterHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final String POST = "POST";

    private class ServletInputStreamWrapper extends ServletInputStream {

        private byte[] data;
        private int idx = 0;

        ServletInputStreamWrapper(byte[] data) {
            if (data == null) {
                data = new byte[0];
            }
            this.data = data;
        }

        @Override
        public int read() throws IOException {
            if (idx == data.length) {
                return -1;
            }
            return data[idx++];
        }

    }

    private byte[] contentData;
    private Map parameters;
    private String method;

    public UpdateParameterHttpServletRequestWrapper(final HttpServletRequest request, final Map<String, String[]> parameters) {
        super(request);
        this.parameters = copyMap(parameters);
        this.method = POST;
        parseRequest();
    }

    private Map copyMap(final Map orig) {
        if (orig == null) {
            return (new HashMap());
        }
        Map dest = new HashMap();
        Iterator<String> keys = orig.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            dest.put(key, orig.get(key));
        }
        return (dest);

    }

    /**
     * This method is safe to use multiple times. Changing the returned array will not interfere with this class
     * operation.
     *
     * @return The cloned content data.
     */
    public byte[] getContentData() {
        return contentData.clone();
    }

    @Override
    public int getContentLength() {
        return contentData.length;
    }

    /**
     * This method is safe to use multiple times. Changing the returned map or the array of any of the map's values will
     * not interfere with this class operation.
     *
     * @return The cloned parameters map.
     */
    public Map<String, String[]> getParameters() {
        return parameters;
    }

    /**
     * Loop through parameters and setup byte array for OutputStream.
     */
    private void parseRequest() {
        final StringBuffer sb = new StringBuffer();
        Map ps = getParameters();
        for (Iterator<String> it = ps.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            String value = getParameter(key);
            ps.put(key, value);
            sb.append(key).append("=").append(value);
            if (it.hasNext()) {
                sb.append("&");
            }

        }

        contentData = new byte[sb.toString().getBytes().length];
        contentData = sb.toString().getBytes();
    }

    /**
     * This method is safe to call multiple times. Calling it will not interfere with getParameterXXX() or getReader().
     * Every time a new ServletInputStream is returned that reads data from the begining.
     *
     * @return A new ServletInputStream.
     */
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStreamWrapper(contentData);
    }

    /**
     * This method is safe to call multiple times. Calling it will not interfere with getParameterXXX() or
     * getInputStream(). Every time a new BufferedReader is returned that reads data from the begining.
     *
     * @return A new BufferedReader with the wrapped request's character encoding (or UTF-8 if null).
     */
    public BufferedReader getReader() throws IOException {
        String enc = super.getRequest().getCharacterEncoding();
        if (enc == null) {
            enc = "UTF-8";
        }
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(contentData), enc));
    }

    /**
     * This method is safe to execute multiple times.
     *
     * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
     */
    public String getParameter(final String name) {

        // parseParameters();

        Object value = parameters.get(name);
        if (value == null) {
            return (null);
        } else if (value instanceof String[]) {
            return (((String[]) value)[0]);
        } else if (value instanceof String) {
            return ((String) value);
        } else {
            return (value.toString());
        }
    }

    /**
     * This method is safe.
     *
     * @see {@link #getParameters()}
     * @see javax.servlet.ServletRequest#getParameterMap()
     */
    @SuppressWarnings("unchecked")
    public Map getParameterMap() {
        return getParameters();
    }

    /**
     * This method is safe to execute multiple times.
     *
     * @see javax.servlet.ServletRequest#getParameterNames()
     */
    public Enumeration getParameterNames() {
        return new Enumeration() {
            private String[] arr = (String[]) getParameters().keySet().toArray(new String[0]);
            private int idx = 0;

            public boolean hasMoreElements() {
                return idx <= arr.length - 1;
            }

            public Object nextElement() {
                return arr[idx++];
            }
        };
    }

    /**
     * Override the <code>getParameterValues()</code> method of the wrapped request.
     *
     * @param name
     *            Name of the requested parameter
     */
    public String[] getParameterValues(final String name) {
        Object value = parameters.get(name);
        if (value == null) {
            return ((String[]) null);
        } else if (value instanceof String[]) {
            return ((String[]) value);
        } else if (value instanceof String) {
            String values[] = new String[1];
            values[0] = (String) value;
            return (values);
        } else {
            String values[] = new String[1];
            values[0] = value.toString();
            return (values);
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }
}
