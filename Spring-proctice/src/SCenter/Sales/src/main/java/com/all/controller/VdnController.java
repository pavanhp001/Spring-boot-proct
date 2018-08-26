package com.AL.controller;

import java.io.File;
import java.io.StringReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.AL.ui.domain.ConsumerViewLites;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.ConsumerVO;
import com.AL.ui.vo.SalesCenterVO;
import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheManager;


@Controller
public class VdnController extends BaseController{

	private static final Logger logger = Logger.getLogger(VdnController.class);

	@RequestMapping(value = "/vdnShow")
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("in controller");
		return new ModelAndView("vdn");
	}

	@RequestMapping(value = "/vdn/submit")
	protected String submitXml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {


		logger.info("VDN url: "+request.getParameter("vdnurl"));
		logger.info("DT XML: "+request.getParameter("dtxml"));

		StringReader sr = null;
		request.getSession().setAttribute("salescontextVDN", request.getParameter("vdnurl"));
		
		JAXBContext transientContainerJxbContext = null;

		Cache cache = CacheManager.getInstance().getCache();
		cache.store("salescontextVDN", request.getParameter("vdnurl"));
		String xmlInput = request.getParameter("dtxml");

		if(!Utils.isBlank(xmlInput))
		{
			try {
				
				if (request != null && request.getSession() != null && request.getSession().getAttribute("transientConsumerView") != null){
					transientContainerJxbContext = (JAXBContext) request.getSession().getAttribute("transientConsumerView");	
				}else{
					transientContainerJxbContext = JAXBContext.newInstance(ConsumerViewLites.class);
					request.getSession().setAttribute("transientConsumerView",transientContainerJxbContext);
				}
				sr = new StringReader(xmlInput);
				Unmarshaller unmarshaller = transientContainerJxbContext.createUnmarshaller();
				JAXBElement<ConsumerVO> b = unmarshaller.unmarshal(new StreamSource(sr), ConsumerVO.class);
				ConsumerVO dtConsumer = b.getValue();
				request.getSession().setAttribute("salescontextDt", dtConsumer);
				SalesCenterVO salesCenterVo = new SalesCenterVO();
				salesCenterVo.setValueByName("salesFlow.contextId" , "NA");
				request.getSession().setAttribute("salescontext",salesCenterVo);
			} 
			catch (JAXBException e) {
				e.printStackTrace();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			} 
			finally {
				sr.close();
			}

		}
		
		String absoluteURL = (String) request.getSession().getAttribute("urlPath");
		return "redirect:" + absoluteURL + "/salescenter/login";
	}

}
