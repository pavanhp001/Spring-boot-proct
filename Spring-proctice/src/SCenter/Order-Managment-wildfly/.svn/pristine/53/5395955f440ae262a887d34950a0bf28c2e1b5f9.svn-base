package com.AL.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.V.beans.entity.ProductPromotion;
import com.AL.Vdao.dao.CatalogPromotionDao;
import com.AL.vm.service.CatalogPromotionService;

@Component
public class CatalogPromotionServiceImpl implements CatalogPromotionService
{

	@Autowired
	private CatalogPromotionDao promotionDao;
	
	public ProductPromotion findCatalogPromotionById( String id )
	{
		if(id == null)
		{
			return null;
		}
		return promotionDao.findCatalogPromotionById( id );
	}

}
