package com.A.V.gateway;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class URLConnectionClient implements HttpClient {

	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String DELETE = "DELETE";
	public static final String PUT = "PUT";

	private static final String ENCODING = "UTF-8";
	public static final String AUTH_SCHEME = "OAuth";
	public static final String MULTIPART = "multipart/";

	private static final String DEFAULT_CONTENT_CHARSET = ENCODING;

	public URLConnectionClient() {
	}

	@Override
	public void shutdown() {
		// Nothing to do here
	}

	@Override
	public String execute(String locationUri, String request,
			Map<String, String> headers, String requestMethod) {

		String responseBody = null;
		URLConnection c = null;
		int responseCode = 0;
		try {
			URL url = new URL(locationUri);

			c = url.openConnection();
			responseCode = -1;
			if (c instanceof HttpURLConnection) {
				HttpURLConnection httpURLConnection = (HttpURLConnection) c;

				if (headers != null && !headers.isEmpty()) {
					for (Map.Entry<String, String> header : headers.entrySet()) {
						httpURLConnection.addRequestProperty(header.getKey(),
								header.getValue());
					}
				}

				httpURLConnection.setRequestMethod(requestMethod);
				if (requestMethod.equals(POST)) {
					httpURLConnection.setDoOutput(true);
					OutputStream ost = httpURLConnection.getOutputStream();
					PrintWriter pw = new PrintWriter(ost);
					pw.print(request);
					pw.flush();
					pw.close();

				}

				httpURLConnection.connect();

				InputStream inputStream;
				responseCode = httpURLConnection.getResponseCode();
				if (responseCode == 400) {
					inputStream = httpURLConnection.getErrorStream();
				} else {
					inputStream = httpURLConnection.getInputStream();
				}

				responseBody = saveStreamAsString(inputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseBody;
	}

	/**
	 * Read data from Input Stream and save it as a String.
	 * 
	 * @param is
	 *            InputStream to be read
	 * @return String that was read from the stream
	 */
	public static String saveStreamAsString(InputStream is) throws IOException {
		return toString(is, ENCODING);
	}

	/**
	 * Get the entity content as a String, using the provided default character
	 * set if none is found in the entity. If defaultCharset is null, the
	 * default "UTF-8" is used.
	 * 
	 * @param is
	 *            input stream to be saved as string
	 * @param defaultCharset
	 *            character set to be applied if none found in the entity
	 * @return the entity content as a String
	 * @throws IllegalArgumentException
	 *             if entity is null or if content length > Integer.MAX_VALUE
	 * @throws IOException
	 *             if an error occurs reading the input stream
	 */
	public static String toString(final InputStream is,
			final String defaultCharset) throws IOException {
		if (is == null) {
			throw new IllegalArgumentException("InputStream may not be null");
		}

		String charset = defaultCharset;
		if (charset == null) {
			charset = DEFAULT_CONTENT_CHARSET;
		}
		Reader reader = new InputStreamReader(is, charset);
		StringBuilder sb = new StringBuilder();
		int l;
		try {
			char[] tmp = new char[4096];
			while ((l = reader.read(tmp)) != -1) {
				sb.append(tmp, 0, l);
			}
		} finally {
			reader.close();
		}
		return sb.toString();
	}

}
