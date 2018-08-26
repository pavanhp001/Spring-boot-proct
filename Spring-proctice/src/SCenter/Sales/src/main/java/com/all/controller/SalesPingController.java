package com.AL.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SalesPingController extends  BaseController {

	private static final Logger logger = Logger.getLogger(SalesPingController.class);

	 

	@RequestMapping(value = "/ajax/sales/ping")
	public ResponseEntity<String> pingAjax(HttpServletRequest request) {

		logger.info("ajax_ping");

		 
		return createJsonResponse();
	}
	
	@RequestMapping(value = "/sales/version", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("version");
		return mav;
	}

	 
	private ResponseEntity<String> createJsonResponse( ) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		JSONObject obj = new JSONObject();
		obj.put("date", new Date());
		obj.put("company","AL");
		obj.put("application","sales");
	 
		
		StringWriter out = new StringWriter();
		try {
			obj.writeJSONString(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonText = out.toString();
		System.out.print(jsonText);
		return new ResponseEntity<String>(jsonText, headers, HttpStatus.CREATED);
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}

}