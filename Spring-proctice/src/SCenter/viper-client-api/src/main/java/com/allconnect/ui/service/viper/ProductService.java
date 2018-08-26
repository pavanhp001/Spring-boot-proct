package com.A.ui.service.V;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.A.ui.builder.ProviderBuilder;
import com.A.ui.repository.ProviderCriteria;
import com.A.ui.repository.SalesContextDefault;
import com.A.ui.service.V.impl.ProductCacheService;
import com.A.ui.template.Dwelling;
import com.A.ui.template.ProductTemplateConstant;
import com.A.ui.vo.ServiceabilityResultVo;
import com.A.V.domain.SalesContext;
import com.A.V.factory.SalesContextFactory;
import com.A.V.gateway.jms.ProductClientJMS;
import com.A.xml.pr.v4.AddressType;
import com.A.xml.pr.v4.ProductCatalogResults;
import com.A.xml.pr.v4.ProductCatalogType;
import com.A.xml.pr.v4.ProductInfoType;
import com.A.xml.pr.v4.ProductResponseType;
import com.A.xml.pr.v4.EnterpriseResponseDocumentType;
import com.A.xml.pr.v4.ProviderResults;
import com.A.xml.pr.v4.ProviderType;
import com.A.xml.pr.v4.ProductRequestType.ProviderList;
import com.A.xml.se.v4.ProviderCriteriaType2;
import com.A.xml.se.v4.ProviderNameValuePairType2;
import com.A.xml.se.v4.ServiceabilityEnterpriseResponse;
import com.A.xml.se.v4.ServiceabilityResponse2;

public enum ProductService {

	INSTANCE;

	public ProductInfoType getProduct(final String providerExternalId,
			final String productExternalId, final String GUID,
			final SalesContext salesContext) {

		ProductInfoType productInfo = ProductCacheService.INSTANCE.get(providerExternalId, productExternalId);

		// Commented to disable cache value
		/*if (productInfo != null) {
			return productInfo;
		}*/

		String productRequestTemplate = ProductTemplateConstant.INSTANCE.getProductDetailsRequest(providerExternalId, productExternalId, GUID, salesContext);
		
		ProductClientJMS jmsClient = new ProductClientJMS();
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("GUID", GUID);
		EnterpriseResponseDocumentType response = jmsClient.send(productRequestTemplate, headers);

		if (response != null) {
			ProductResponseType productResponseType = (ProductResponseType) response.getResponse();
			ProviderResults results = productResponseType.getProviderResults().get(0);
			if ((results.getProductInfo() != null) && (results.getProductInfo().size() > 0)) {
				productInfo = results.getProductInfo().get(0);
			}
		}

		// Commented to disable cache value
		/*if (productInfo != null) {
			ProductCacheService.INSTANCE.store(productInfo, providerExternalId, productExternalId);
		}*/

		return productInfo;
	}

	public EnterpriseResponseDocumentType getProducts(ProviderList providers,
			final AddressType address, final String GUID,
			final SalesContext salesContext) {

		String productRequestTemplate = ProductTemplateConstant.INSTANCE
				.getProductsRequest(providers, address, GUID, salesContext);

		ProductClientJMS jmsClient = new ProductClientJMS();
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("GUID", GUID);
		EnterpriseResponseDocumentType response = jmsClient
				.send(productRequestTemplate, headers);

		return response;
	}
	
	public EnterpriseResponseDocumentType getProducts(ProviderList providers,
			final AddressType address, final String GUID,
			final SalesContext salesContext, String serviceabilityTransactionType) {

		String productRequestTemplate = ProductTemplateConstant.INSTANCE
				.getProductsRequest(providers, address, GUID, salesContext,serviceabilityTransactionType);

		ProductClientJMS jmsClient = new ProductClientJMS();
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("GUID", GUID);
		EnterpriseResponseDocumentType response = jmsClient
				.send(productRequestTemplate, headers);

		return response;
	}

	public EnterpriseResponseDocumentType getProductsWithFeatures(ProviderList providers,
			final AddressType address, final String GUID,
			final SalesContext salesContext, String serviceabilityTransactionType) {

		String productRequestTemplate = ProductTemplateConstant.INSTANCE
				.getProductsRequestWithFeatures(providers, address, GUID, salesContext,serviceabilityTransactionType);

		ProductClientJMS jmsClient = new ProductClientJMS();
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("GUID", GUID);
		EnterpriseResponseDocumentType response = jmsClient
				.send(productRequestTemplate, headers);

		return response;
	}
	
	
	public ProductInfoType getProduct(String provider, String product,
			String GUID, final Map<String, Map<String, String>> data) {

		SalesContext salesContext = SalesContextFactory.INSTANCE
				.getSalesContext(data);

		return getProduct(provider, product, GUID, salesContext);
	}

	public ProductInfoType getProduct(String provider, String product,
			final Map<String, Map<String, String>> data) {
		SalesContext salesContext = SalesContextFactory.INSTANCE
				.getSalesContext(data);
		return getProduct(provider, product, UUID.randomUUID().toString(),
				salesContext);
	}

	public ProductInfoType getProduct(final String provider,
			final String product, final SalesContext salesContext) {
		return getProduct(provider, product, UUID.randomUUID().toString(),
				salesContext);
	}

	public List<ProductInfoType> getServiceabilityProductList(
			String completeAddress, Dwelling dwellingType) {

		ServiceabilityResultVo vo = getServiceabilityProviderList(
				completeAddress, dwellingType);
		List<ProductInfoType> productList = getServiceabilityProductList(vo,
				completeAddress);

		return productList;
	}

	public ServiceabilityResultVo getServiceabilityProviderList(String address,
			Dwelling dwellingType) {

		SalesContext salesContext = SalesContextFactory.INSTANCE
				.getSalesContext(SalesContextDefault.INSTANCE
						.createSalesContext(dwellingType.name()));

		ProviderCriteriaType2 providerCriteria = ProviderCriteria.INSTANCE
				.createProviderCriteria();

		return getServiceabilityProviderList(address, dwellingType,
				salesContext, providerCriteria);
	}

	public List<com.A.xml.se.v4.ProviderType> getProviders(String address, Dwelling dwellingType) {

		SalesContext salesContext = SalesContextFactory.INSTANCE
				.getSalesContext(SalesContextDefault.INSTANCE
						.createSalesContext(dwellingType.name()));

		ProviderCriteriaType2 providerCriteria = ProviderCriteria.INSTANCE
				.createProviderCriteria();
   
		ServiceabilityEnterpriseResponse sarRes = ESEService.INSTANCE
				.getServiceabilityResponse(address, salesContext,
						providerCriteria);

	 
		ServiceabilityResponse2 sre = (ServiceabilityResponse2) sarRes
				.getResponse();
 
		return sre.getProviderList().getProviders(); 
	}

	public ServiceabilityResultVo getServiceabilityProviderList(String address,
			Dwelling dwellingType, SalesContext salesContext,
			ProviderCriteriaType2 providerCriteria) {

		ServiceabilityResultVo vo = new ServiceabilityResultVo();

		ServiceabilityEnterpriseResponse sarRes = ESEService.INSTANCE
				.getServiceabilityResponse(address, salesContext,
						providerCriteria);

		Map<String, String> realTimePendingProviderList = new HashMap<String, String>();

		ServiceabilityResponse2 sre = (ServiceabilityResponse2) sarRes
				.getResponse();

		com.A.xml.se.v4.ServiceabilityResponse2.ProviderList proList = sre
				.getProviderList();

		String extId = null;
		String name = null;
		ProviderList providerList = new ProviderList();
		for (com.A.xml.se.v4.ProviderCriteriaEntityType2 pr2 : sre
				.getRtimRequestResponseCriteria().getProviderCriteria()
				.getProviders()) {
			extId = null;
			name = pr2.getName();
			for (ProviderNameValuePairType2 pr3 : pr2.getAttributes()) {
				if (pr3.getName() != null
						&& pr3.getName().equalsIgnoreCase("externalId")) {
					extId = pr3.getValueAttribute();
				}
				if (pr3.getName() != null
						&& pr3.getName().equalsIgnoreCase("status")
						&& pr3.getValueAttribute().equalsIgnoreCase("Pending")) {
					if (extId != null) {
						realTimePendingProviderList.put(extId, name);

					}
				} else if (pr3.getName() != null
						&& pr3.getName().equalsIgnoreCase("status")
						&& pr3.getValueAttribute().equalsIgnoreCase("Success")) {
					if (extId != null) {
						ProviderType provider = new ProviderType();
						provider = ProviderBuilder.INSTANCE
								.addRtimProvidersToProvidersList(extId,
										provider, name);

						providerList.getProvider().add(provider);
					}
				}
			}
		}

		vo.setRealTimePendingProviderList(realTimePendingProviderList);
		vo.setServiceabilityResponseList(proList);
		vo.setGuid(sarRes.getGUID());
		vo.setSalesContext(salesContext);
		vo.setPostalCode(sre.getCorrectedAddress().getPostalCode());
		vo.setProviderList(providerList);
		return vo;
	}

	public List<ProductInfoType> getServiceabilityProductList(
			ServiceabilityResultVo vo, String address) {
		List<ProviderResults> results = null;
		List<ProductInfoType> productInfoList = new ArrayList<ProductInfoType>();

		com.A.xml.se.v4.ServiceabilityResponse2.ProviderList proList = vo
				.getServiceabilityResponseList();
		com.A.xml.pr.v4.EnterpriseResponseDocumentType response = ProductService.INSTANCE
				.getProducts(
						ProviderBuilder.INSTANCE.getProviderList(proList,
								vo.getProviderList()),
						getAddress(vo.getPostalCode()), vo.getGuid(),
						vo.getSalesContext());
		if (response != null) {
			ProductResponseType productResponseType = (ProductResponseType) response
					.getResponse();
			results = productResponseType.getProviderResults();
			for (ProviderResults rs : results) {
				for (ProductInfoType product : rs.getProductInfo()) {
					productInfoList.add(product);
				}
			}
		}

		return productInfoList;

	}

	public AddressType getAddress(String postalCode) {
		AddressType address = new AddressType();
		address.setPostalCode(postalCode);
		return address;
	}
	
	public ProductInfoType getProductWithMeta(final String providerExternalId,
			final String productExternalId, final String GUID,
			final SalesContext salesContext) {

		ProductInfoType productInfo = null;
		//Cache Issue not working in CKO
		//ProductCacheService.INSTANCE.get(providerExternalId, productExternalId);

		/*if (productInfo != null) {
			return productInfo;
		}*/

		/*String productRequestTemplate = ProductTemplateConstant.INSTANCE
				.getProductDetailsRequest(providerExternalId,
						productExternalId, GUID, salesContext);*/
		String productRequestTemplate = ProductTemplateConstant.INSTANCE
		.getProductDetailsWithMETARequest(providerExternalId,productExternalId, GUID, salesContext);
		
		ProductClientJMS jmsClient = new ProductClientJMS();
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("GUID", GUID);
		EnterpriseResponseDocumentType response = jmsClient
				.send(productRequestTemplate, headers);

		if (response != null) {
			ProductResponseType productResponseType = (ProductResponseType) response
					.getResponse();
			ProviderResults results = productResponseType.getProviderResults()
					.get(0);
			if ((results.getProductInfo() != null)
					&& (results.getProductInfo().size() > 0)) {
				productInfo = results.getProductInfo().get(0);
			}
		}

		/*if (productInfo != null) {
			ProductCacheService.INSTANCE.store(productInfo, providerExternalId,
					productExternalId);
		}*/

		return productInfo;
	}
	
	/**
	 * Get Cataloged Product
	 */
	public List<ProductCatalogType> getProductCatalogDetails(String[] productCatalogIds,String GUID) {

		String productRequestTemplate = ProductTemplateConstant.INSTANCE.getProductCatalogDetailsRequest(productCatalogIds, GUID);
		
		ProductClientJMS jmsClient = new ProductClientJMS();
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("GUID", GUID);
		EnterpriseResponseDocumentType response = jmsClient.send(productRequestTemplate, headers);

		List<ProductCatalogType> productCatalogTypes = new ArrayList<ProductCatalogType>();
		if (response != null) {
			ProductResponseType productResponseType = (ProductResponseType) response.getResponse();
			ProductCatalogResults results = productResponseType.getProductCatalogResults().get(0);
			if ((results.getProductCatalog() != null) && (results.getProductCatalog().size() > 0)) {
				productCatalogTypes = results.getProductCatalog();
			}
		}

		return productCatalogTypes;
	}
	
}
