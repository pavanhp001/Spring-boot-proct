package com.AL.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.AL.ui.InstallationVO;
import com.AL.ui.ProductVO;
import com.AL.ui.QualificationVO;
import com.AL.ui.domain.DigitalCacheKeys;
import com.AL.ui.domain.SessionKeys;
import com.AL.ui.service.V.impl.CKOCacheService;
import com.AL.ui.service.V.impl.StaticCKOCacheManager;
import com.AL.ui.vo.OrderQualVO;
import com.AL.ui.vo.SessionVO;

@Service
public class BackButtonService {
	
	/**
	 * Get ProductVO from Cache based on combination of OrderId and LineItemExternalId
	 * @param request
	 * @return ProductVO
	 */
	public ProductVO getProductVO(HttpServletRequest request){
		SessionVO sessionVO = CKOCacheService.INSTANCE.get(request.getSession().getId());
		OrderQualVO orderQualVO = (OrderQualVO)sessionVO.get(SessionKeys.orderQualVo.name());

		ProductVO productVO = (ProductVO)StaticCKOCacheManager.INSTANCE.getObjectFromCache(DigitalCacheKeys.ProductVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
		return productVO;
	}
	
	/**
	 * Get QualificationVO from Cache based on combination of OrderId and LineItemExternalId
	 * @param request
	 * @return QualificationVO
	 */
	public QualificationVO getQualificationVO(HttpServletRequest request){
		SessionVO sessionVO = CKOCacheService.INSTANCE.get(request.getSession().getId());
		OrderQualVO orderQualVO = (OrderQualVO)sessionVO.get(SessionKeys.orderQualVo.name());

		QualificationVO qualificationVO = (QualificationVO)StaticCKOCacheManager.INSTANCE.getObjectFromCache(DigitalCacheKeys.QualificationVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
		return qualificationVO;
	}
	
	/**
	 * Get InstallationVO from Cache based on combination of OrderId and LineItemExternalId
	 * @param request
	 * @return InstallationVO
	 */
	public InstallationVO getInstallationVO(HttpServletRequest request){
		SessionVO sessionVO = CKOCacheService.INSTANCE.get(request.getSession().getId());
		OrderQualVO orderQualVO = (OrderQualVO)sessionVO.get(SessionKeys.orderQualVo.name());

		InstallationVO installationVO = (InstallationVO)StaticCKOCacheManager.INSTANCE.getObjectFromCache(DigitalCacheKeys.InstallationVO+orderQualVO.getOrderId()+"_"+orderQualVO.getLineItemExternalId());
		return installationVO;
	}

}
