package com.AL.controller.stub;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@Controller
public class UserAccessManager extends AbstractController {

	@RequestMapping(value = "/uam_stub_response.html")
	@ResponseBody
	public String positive() {

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

			InputStream iStream = classLoader.getResourceAsStream("/authenticate_success.xml");

			 StringBuffer fileData = new StringBuffer(1000);
		        BufferedReader reader = new BufferedReader(new InputStreamReader( iStream ));
		        char[] buf = new char[1024];
		        int numRead=0;
		        while((numRead=reader.read(buf)) != -1){
		            String readData = String.valueOf(buf, 0, numRead);
		            fileData.append(readData);
		            buf = new char[1024];
		        }
		        reader.close();
		        return fileData.toString();
		        
		   
			
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "exception";
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		mav.addObject("message", "Hello World From Phuong!");
		return mav;
	}

}