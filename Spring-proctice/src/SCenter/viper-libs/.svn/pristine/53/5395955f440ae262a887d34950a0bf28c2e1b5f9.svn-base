package com.A.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.A.V.beans.entity.ProductPromotion;
import com.A.Vdao.dao.CatalogPromotionDao;
import com.A.vm.service.CatalogPromotionService;

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
