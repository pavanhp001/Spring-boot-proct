package com.AL.task.request;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.AL.activity.impl.ActivitySalesContextEvaluate;
import com.AL.xml.v4.AbstractRequestType;
import com.AL.xml.v4.AbstractTransactionTypeType;
import com.AL.xml.v4.EnterpriseRequestDocumentType;
import com.AL.xml.v4.LineItemCollectionType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderLineItemDetailTypeType;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.OrderManagementRequestResponseDocument;
import com.AL.xml.v4.ProductBundleType;
import com.AL.xml.v4.ProductEnterpriseRequestDocument;
import com.AL.xml.v4.ProductRequestType;
import com.AL.xml.v4.ProductRequestType.ProductList;
import com.AL.xml.v4.ProductRequestType.ProductList.ProductId;
import com.AL.xml.v4.ProductTransactionTypeType;
import com.AL.xml.v4.ProductType;
import com.AL.xml.v4.SalesContextType;

@Component
public class ProductServiceRequestBuilder implements RequestBuilder<ProductEnterpriseRequestDocument>
{

	private static final Logger logger = Logger.getLogger(ProductServiceRequestBuilder.class);

	private static final String CATALOG_PRODUCTS = "catalogProducts";


	public ProductEnterpriseRequestDocument buildRequest( OrderManagementRequestResponseDocument orderDoc )
	{

		logger.info("Building product service request");
		String guid =  ActivitySalesContextEvaluate.INSTANCE.getGUID(orderDoc);

		if (guid == null) {
			guid = orderDoc.getOrderManagementRequestResponse().getCorrelationId();
		}

		 ProductEnterpriseRequestDocument productRequest = getBaseProductRequest( guid );

		Request orderReq = orderDoc.getOrderManagementRequestResponse().getRequest();
		List<LineItemType> liTypeList = orderReq.getOrderInfo().getLineItems().getLineItemList();

		ProductRequestType prodReqType = populateProducts(liTypeList);
		productRequest.getProductEnterpriseRequest().setRequest( prodReqType );
		return productRequest;
	}




	/**
	 * This method will retrieve each line item from order request document and populate the request for Product service
	 * @param orderDoc
	 * @return
	 */





	private ProductRequestType populateProducts(List<LineItemType> liTypeList)
	{
		ProductRequestType prodReqType = ProductRequestType.Factory.newInstance();

		if(liTypeList != null && !liTypeList.isEmpty())
		{
			ProductList prodListType = prodReqType.addNewProductList();
			for(LineItemType liType : liTypeList)
			{


				OrderLineItemDetailTypeType srcLIDetail = liType.getLineItemDetail().getDetail();
				String detailType = liType.getLineItemDetail().getDetailType().toString();
				if(detailType.equalsIgnoreCase( "product" ))
				{
					ProductId product = prodListType.addNewProductId();
					ProductType srcProdType = srcLIDetail.getProductLineItem();
					product.setExternalId( srcProdType.getExternalId() );
					product.setProviderExternalId( srcProdType.getProvider().getExternalId() );
					product.setInstanceId(String.valueOf( liType.getLineItemNumber() ) );
				}
				else if(detailType.equalsIgnoreCase( "productBundle" ))
				{
					ProductId product = prodListType.addNewProductId();
					ProductBundleType srcProdType = srcLIDetail.getProductBundleLineItem();
					product.setExternalId( srcProdType.getExternalId() );
					product.setProviderExternalId( srcProdType.getProviderId() );
					product.setInstanceId(String.valueOf( liType.getLineItemNumber() ) );
				}
			}
		}




		return prodReqType;
	}

	/**
	 * Helper method to create ProductEnterpriseRequestDocument
	 * @param correlationId
	 * @return
	 */
	public ProductEnterpriseRequestDocument getBaseProductRequest(String correlationId )
	{
		ProductEnterpriseRequestDocument productRequest = ProductEnterpriseRequestDocument.Factory.newInstance();
		EnterpriseRequestDocumentType reqType = productRequest.addNewProductEnterpriseRequest();
		reqType.setGUID( correlationId );

		AbstractTransactionTypeType tranType = reqType.addNewTransactionType();
		ProductTransactionTypeType prodTran = ProductTransactionTypeType.Factory.newInstance();
		prodTran.setProductTransactionType(prodTran.getProductTransactionType().forString( CATALOG_PRODUCTS ));
		reqType.setTransactionType( prodTran );

		SalesContextType scType = reqType.addNewSalesContext();
		AbstractRequestType abstReqType = reqType.addNewRequest();

		return productRequest;
	}

	/**
	 * Method to prepare request for newly added lineitem.
	 */
	public ProductEnterpriseRequestDocument buildRequestForAddLineitem( LineItemCollectionType lineItems, String correlationId )
	{
		ProductEnterpriseRequestDocument productRequest = getBaseProductRequest( correlationId );
		ProductRequestType prodReqType = populateProducts(lineItems.getLineItemList());
		productRequest.getProductEnterpriseRequest().setRequest( prodReqType );
		return productRequest;
	}


}
