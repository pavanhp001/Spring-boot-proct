package com.A.Vdao.dao;


import com.A.V.beans.entity.ProductPromotion;

public interface PromotionDao {
	ProductPromotion findPromotionById(  final String id);
}
