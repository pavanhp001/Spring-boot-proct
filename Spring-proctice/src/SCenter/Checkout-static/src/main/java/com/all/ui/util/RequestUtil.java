package com.AL.ui.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public enum RequestUtil {

	INSTANCE;
	
	private static Logger logger = Logger.getLogger(RequestUtil.class);
	
	/**
	 * converts the request object to Map<String, String>
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> dumpRequestScope(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> e = request.getParameterNames();
		String name = null;
		while (e.hasMoreElements()) {
			name = e.nextElement();
			if(name.indexOf("mandatory_disclosure_checkbox") == -1){
				String[] args = request.getParameterValues(name);
				StringBuilder sb = new StringBuilder();
				for (String arg : args) {
					sb.append(arg).append(",");
				}
				sb.setLength(sb.length() - 1);
				map.put(name, sb.toString());
			}else{
				String[] args = request.getParameterValues(name);
				StringBuilder sb = new StringBuilder();
				for (String arg : args) {
					sb.append(arg).append(",");
				}
				sb.setLength(sb.length() - 1);
				request.getSession().setAttribute("mandatoryDisclosureCheckboxes", sb);
			}
			//TODO:Remove
			//logger.info(name + "=" + sb.toString());
		}

		return map;

	}
}
