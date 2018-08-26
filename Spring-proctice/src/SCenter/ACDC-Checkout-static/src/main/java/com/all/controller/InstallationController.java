package com.AL.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AL.service.InstallationService;
import com.AL.ui.OrderRecapVO;
import com.AL.ui.constants.Constants;
import com.AL.ui.domain.SessionKeys;
import com.AL.ui.factory.CKOLineItemFactory;
import com.AL.ui.service.V.impl.CKOCacheService;
import com.AL.ui.util.JsonUtil;
import com.AL.ui.util.Utils;
import com.AL.ui.vo.CKOInitialVo;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.SessionVO;
import com.AL.xml.v4.OrderType;

@Controller
public class InstallationController extends BaseController {
	private static final Logger logger = Logger.getLogger(InstallationController.class);


	@Autowired
	private InstallationService installationService;


	@RequestMapping(value = "/redirectConformation", method = RequestMethod.GET)
	public ModelAndView handleGet(@ModelAttribute("mavRecap") ModelAndView modelAndView, HttpServletRequest request) {		
		SessionVO sessionVO = CKOCacheService.INSTANCE.get(request.getSession().getId());
		if (Utils.isValidSession(sessionVO, SessionKeys.initiator.name())) {
			if (modelAndView != null
					&& (modelAndView.getModel() != null && !modelAndView.getModel().isEmpty())) {
				CKOCacheService.INSTANCE.clearMAV(request.getSession().getId(), "redirectConformation");
				logger.info("store mav sessionId=" + request.getSession().getId());
				logger.info("sessionID from sessionVO=" + sessionVO.getSessionId());
				CKOCacheService.INSTANCE.storeMAV(modelAndView, sessionVO, "redirectConformation");
			} else {
				logger.info("get mav sessionId=" + request.getSession().getId());
				logger.info("sessionID from sessionVO=" + sessionVO.getSessionId());
				modelAndView = CKOCacheService.INSTANCE.getMAV(request.getSession().getId(), "redirectConformation");
			}
		}
		logger.info("redirectSuccessful");
		//		modelAndView.setViewName("dialogueUpdate");
		return modelAndView;
	}

	/**
	 * @param request
	 * @param redirectAttrs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveInstallation", method = RequestMethod.POST)
	public String saveInstallation(HttpServletRequest request, final RedirectAttributes redirectAttrs) throws Exception {
		logger.info("start_saveInstallation");
		SessionVO sessionVO = CKOCacheService.INSTANCE.get(request.getSession().getId());
		String redirectPath = "redirect:/static/redirectUpdateDialogueSessiontimeOut";
		String idata = request.getParameter("iData");
		JsonUtil<CKOInitialVo> util = new JsonUtil<CKOInitialVo>();
		if(!Utils.isValidSession(sessionVO, SessionKeys.initiator.name()))
		{
			CKOLineItemFactory.INSTANCE.prepareNewSession(sessionVO, request, util);
		}
		CKOInitialVo CKOVo = (CKOInitialVo)sessionVO.get(SessionKeys.initiator.name());
		OrderQualVO orderQualVO = (OrderQualVO)sessionVO.get(SessionKeys.orderQualVo.name());
		if(CKOVo!= null && CKOVo.getParams().contains(Constants.DIGTIAL_KEY)){
			ModelAndView mav = new ModelAndView();
			OrderType orderType = installationService.excuteUpdateDailog(request,orderQualVO);
			OrderRecapVO  orderRecapVO = installationService.buidlOrderRecapVO(request,orderType,orderQualVO,sessionVO);
			logger.info("orderRecapVO"+Utils.convertJSONString(orderRecapVO));
			mav.addObject("orderRecapVO",Utils.convertJSONString(orderRecapVO));
			mav.addObject("iData",idata);
			mav.addObject("monthlyTotal",orderRecapVO.getMonthly());
			mav.addObject("installationTotal",orderRecapVO.getInstallationTotal());
			mav.addObject("iData",idata);
			mav.setViewName("acdc_conformation_info");
			redirectAttrs.addFlashAttribute("mavRecap", mav);
			redirectPath = "redirect:/static/redirectConformation";
		}
		logger.info("redirectPath="+redirectPath);
		return redirectPath;
	}
}
