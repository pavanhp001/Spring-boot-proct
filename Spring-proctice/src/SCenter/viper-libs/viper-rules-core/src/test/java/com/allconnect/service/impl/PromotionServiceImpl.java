package com.A.service.impl;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.A.V.beans.entity.ProductPromotion;
import com.A.Vdao.dao.PromotionDao;
import com.A.vm.service.PromotionService;

@Component
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	private PromotionDao promotionDao;
	
	public PromotionServiceImpl( ) {
		 super();
	}
	 
	public ProductPromotion findPromotionById(Long id) {
		  
		return promotionDao.findPromotionById(  String.valueOf(id.longValue()));
	}

	public ProductPromotion findPromotionById(String id) {
		 
		return promotionDao.findPromotionById( id);
	}

 

}
