package com.AL.ui.service.V;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.AL.html.ObjectFactory;
import com.AL.ui.client.ActionStoreInitialRequest;
import com.AL.ui.constants.Constants;
import com.AL.ui.service.V.impl.CKOProductCacheService;
import com.AL.ui.util.SalesContextUtil;
import com.AL.V.domain.SalesContext;
import com.AL.V.factory.SalesContextFactory;
import com.AL.V.gateway.util.JaxbUtil;
import com.AL.xml.pr.v4.CapabilityType;
import com.AL.xml.pr.v4.ProductInfoType;
import com.AL.xml.pr.v4.ProductRequestType;
import com.AL.xml.pr.v4.ProductResponseType;
import com.AL.xml.pr.v4.ProductType;
import com.AL.xml.pr.v4.ProviderResults;
import com.AL.xml.pr.v4.ProviderSourceBaseType;
import com.AL.xml.pr.v4.ProviderSourceType;
import com.AL.xml.pr.v4.ProviderType;
import com.AL.xml.pr.v4.ProductRequestType.ProviderList;
import com.AL.xml.pr.v4.ProviderType.CapabilityList;
import com.AL.xml.v4.AddressType;
import com.AL.xml.v4.CustAddress;
import com.AL.xml.v4.OrderType;

public enum ProductServiceUI {

	INSTANCE;
	
	private static final Logger logger = Logger.getLogger(ProductServiceUI.class);
	
	public static final String STATIC_PROVIDER_ID = "25199176";
	public static final String CACHE_CONTEXT = "sti";

	public ProductInfoType getProductById(String productId) {
		
		ProductInfoType productInfo = ProductServiceUI.INSTANCE.getAttStiSample();
		return productInfo;
	}
	
	public ProductInfoType getAttStiSample() {

		String xmlInput =  ProductServiceUI.INSTANCE.readFileToString(new File("C:/Users/charter2/Desktop/New folder (2)/02-01-2013/Responses/CENLINK-UNLIMITEDVOICE-FL.xml"));
		System.out.println("PRODUCT DETAILS XML FILE OUTPUT ::::::::::::::::: "+xmlInput);
		JaxbUtil<ProviderResults> util = new JaxbUtil<ProviderResults>();
		ProviderResults pr = util.toObject(xmlInput, ProviderResults.class);
		
		ProductInfoType productInfo = pr.getProductInfo().get(0);

		return productInfo;
	}
	
	
	public ProductInfoType getProduct(String productExternalId, String GUID) {
		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(SalesContextUtil.getData());
		ProductInfoType productInfo = ProductService.INSTANCE.getProduct(STATIC_PROVIDER_ID, productExternalId, GUID, salesContext);
		logger.debug("PRODUCT INFO ::::::::::::::: "+productInfo.getProduct().getProvider().getExternalId());
		return productInfo;
	}
	
	public   String readFileToString(File aFileName) {
		String s = "";
		try {
			s = FileUtils.readFileToString(aFileName);
		}
		// Catches any error conditions
		catch (IOException e) {
			e.printStackTrace();
		}

		return s;
	}
	
	/**
	 * check whether the product info object is present in the cache, if present, return the same
	 * else make a getProductDetails Service Call and set the response to the cache Service
	 * 
	 * @param productExternalId
	 * @param GUID
	 * @param providerExternalId
	 * @return
	 */
	public ProductInfoType getProduct(String productExternalId, String GUID, String providerExternalId) {
		
		SalesContext salesContext = SalesContextFactory.INSTANCE.getSalesContext(SalesContextUtil.getData());
		
		/**
		 * checks whether the productExternalId contains '&' and converts it to '&amp;' 
		 */
		if(productExternalId.indexOf("&") >= 0){
			if(!(productExternalId.indexOf("&amp;") >= 0)){
				productExternalId = productExternalId.replace("&", "&amp;");
			}
		}
		
		ProductInfoType productInfo = CKOProductCacheService.INSTANCE.get(providerExternalId+"_"+productExternalId);
		
		if(productInfo == null){
			logger.info("Retrieving_product_from_service_for_product_extenalId="+productExternalId);
			productInfo = ProductService.INSTANCE.getProductWithMeta(providerExternalId, productExternalId, GUID, salesContext);
			CKOProductCacheService.INSTANCE.store(productInfo, providerExternalId, productExternalId);
		}
		
		return productInfo;
	}
	
}