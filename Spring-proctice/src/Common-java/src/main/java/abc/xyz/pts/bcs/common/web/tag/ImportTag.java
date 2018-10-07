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
package abc.xyz.pts.bcs.common.web.tag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class ImportTag extends TagSupport {

    private static final Logger LOGGER = Logger.getLogger(ImportTag.class);
    private static final long serialVersionUID = 1L;
    private static final String COOKIE_HEADER = "Cookie";
    private static final String LANGUAGE_HEADER = "Accept-Language";
    private static final String JOSSO_SESSION_ID = "JOSSO_SESSIONID";
    private static final String ERROR_KEY = "errors.there.has.been.an.error";
    private static final String HTTP_SCHEME = "http";
    private static final String TAG_CONFIG_BEAN_NAME = "importTagConfiguration";
    private static final String NEWLINE = "\n";

    private String url = null;
    private static HttpClient client = null;

    static {
        // client is thread safe when used with a MultiThreadedHttpConnectionManager
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        client = new HttpClient(connectionManager);
    }

    public ImportTag() {

    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    private String getJossoSessionId(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String jossoSessionId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(JOSSO_SESSION_ID)) {
                jossoSessionId = cookie.getValue();
                break;
            }
        }
        return jossoSessionId;
    }

    @Override
    public int doStartTag() throws JspException {
        String content = null;
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        ImportTagConfiguration config = getConfiguration();
        GeneralCacheAdministrator cacheAdmin = config.getCacheAdministrator();
        String jossoSessionId = getJossoSessionId(request);
        try {
            content = (String) cacheAdmin.getFromCache(jossoSessionId);

        } catch (NeedsRefreshException nre) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Refreshing cache for session and url: " + jossoSessionId);
            }
            try {
                content = getContentFromUrl(url, jossoSessionId, request, config);
                cacheAdmin.putInCache(jossoSessionId, content);

            } catch (Exception e) {
                cacheAdmin.cancelUpdate(jossoSessionId);
                LOGGER.error("Unable to retreive content for url: " + url, e);
                String i18nErrorMesg = getMessage(ERROR_KEY);
                content = "<font style='color:red;'>" + i18nErrorMesg + "</font>";
            }
        }
        if (content != null) {
            try {
                this.pageContext.getOut().print(content);
            } catch (IOException ioe) {
                LOGGER.error("Unable to display error message to user.", ioe);
            }
        }
        return TagSupport.SKIP_BODY;
    }

    public void release() {
        // do nowt
    }

    private String getContentFromUrl(final String url, final String jossoSessionId, final HttpServletRequest request,
            final ImportTagConfiguration config) throws IOException, Exception {
        String result = null;

        String absUrl = createAbsoluteUrl(url, config);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Attempting to retrieve content from " + absUrl + "...");
        }
        HttpMethod method = new GetMethod(absUrl);
        InputStream is = null;
        BufferedReader reader = null;
        try {
            method.setRequestHeader(COOKIE_HEADER, JOSSO_SESSION_ID + "=" + jossoSessionId);
            String langaugeHeader = request.getHeader(LANGUAGE_HEADER);
            if (langaugeHeader != null) {
                method.setRequestHeader(LANGUAGE_HEADER, langaugeHeader);
            }
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_NOT_MODIFIED) {
                throw new Exception("Status code 200 or 302 not received: Got " + statusCode + " instead.");
            }
            // TODO prob want to sort of the content type and char set from the response header
            is = method.getResponseBodyAsStream();
            InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(NEWLINE);
                line = reader.readLine();
            }
            result = sb.toString();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Content retrieved");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            method.releaseConnection();
            if (reader != null) {
                reader.close();
            }
            if (is != null) {
                is.close();
            }
        }
        return result;
    }

    private WebApplicationContext getApplicationContext() {
        WebApplicationContext appContext = RequestContextUtils.getWebApplicationContext(pageContext.getRequest(),
                pageContext.getServletContext());
        return appContext;
    }

    private String getMessage(final String messageKey) {
        String i18nErrorMesg = getApplicationContext().getMessage(messageKey, null, LocaleContextHolder.getLocale());
        return i18nErrorMesg;
    }

    private ImportTagConfiguration getConfiguration() {
        return (ImportTagConfiguration) getApplicationContext().getBean(TAG_CONFIG_BEAN_NAME);
    }

    private String createAbsoluteUrl(final String relativeUrl, final ImportTagConfiguration config) {
        String result = null;
        if (relativeUrl.startsWith(HTTP_SCHEME)) {
            result = relativeUrl;
        } else {
            StringBuilder bcsUrlBuff = new StringBuilder();
            bcsUrlBuff.append(config.getBaseUrl());
            bcsUrlBuff.append(relativeUrl);
            result = bcsUrlBuff.toString();
        }
        return result;
    }

}
