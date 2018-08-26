package com.A.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.A.productResults.managers.ProductResultsManager;
import com.A.productResults.vo.ProductSearchIface;
import com.A.productResults.vo.ProductSummaryVO;
import com.A.ui.constants.Constants;
import com.A.ui.factory.CartLineItemFactory;
import com.A.ui.factory.WarmTransferFactory;
import com.A.ui.util.LineItemUtil;
import com.A.xml.v4.LineItemStatusCodesType;
import com.A.xml.v4.LineItemStatusType;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderType;



/**
 * @author Sravan Kumar Nalajala
 *
 */
@Service
public class ClosingOfferService {

	private static final Logger logger = Logger.getLogger(ClosingOfferService.class);

	/** This method use to build closing offer list based on conditions
	 * @param orderType
	 * @param productResultManager
	 * @return
	 */
	public List<ProductSummaryVO> buildClosingOfferList(OrderType orderType,ProductResultsManager productResultManager, HttpServletRequest request){
		List<ProductSummaryVO> allClosingOfferProducts = new ArrayList<ProductSummaryVO>();
		if(productResultManager.getCloseOfferMap() != null && !productResultManager.getCloseOfferMap().isEmpty()){
			boolean isVideoOrderd = isVideoOrdered(orderType);
			logger.info("isVideoOrderd="+isVideoOrderd);
			if(!isVideoOrderd){
				if(productResultManager.getCloseOfferMap().get("closeOfferUtility") != null){
					allClosingOfferProducts.addAll(productResultManager.getCloseOfferMap().get("closeOfferUtility"));
				} 
				if( productResultManager.getCloseOfferMap().get("closeOfferRenters") != null
						&& ( !WarmTransferFactory.INSTANCE.isWarmTransferEnabled(orderType, request) 
								&& CartLineItemFactory.INSTANCE.getTPVLineItemsWithSortingOrder(orderType).isEmpty())
								&& !isADTRentersOrdered(orderType)){
					allClosingOfferProducts.addAll(productResultManager.getCloseOfferMap().get("closeOfferRenters"));
				} 
				if( productResultManager.getCloseOfferMap().get("closeOfferVideo") != null){
					allClosingOfferProducts.addAll(productResultManager.getCloseOfferMap().get("closeOfferVideo"));
				}
				if( productResultManager.getCloseOfferMap().get("closeOfferWarmtransfer") != null
						&& ( !WarmTransferFactory.INSTANCE.isWarmTransferEnabled(orderType, request) 
								&& CartLineItemFactory.INSTANCE.getTPVLineItemsWithSortingOrder(orderType).isEmpty())){
					allClosingOfferProducts.addAll(productResultManager.getCloseOfferMap().get("closeOfferWarmtransfer"));
				} 
			}else{
				if(productResultManager.getCloseOfferMap().get("closeOfferUtility") != null){
					allClosingOfferProducts.addAll(productResultManager.getCloseOfferMap().get("closeOfferUtility"));
				} 
				if( productResultManager.getCloseOfferMap().get("closeOfferRenters") != null
						&& ( !WarmTransferFactory.INSTANCE.isWarmTransferEnabled(orderType, request) 
								&& CartLineItemFactory.INSTANCE.getTPVLineItemsWithSortingOrder(orderType).isEmpty())
								&& !isADTRentersOrdered(orderType)){
					allClosingOfferProducts.addAll(productResultManager.getCloseOfferMap().get("closeOfferRenters"));
				}
				if( productResultManager.getCloseOfferMap().get("closeOfferWarmtransfer") != null
						&& ( !WarmTransferFactory.INSTANCE.isWarmTransferEnabled(orderType, request) 
								&& CartLineItemFactory.INSTANCE.getTPVLineItemsWithSortingOrder(orderType).isEmpty())){
					allClosingOfferProducts.addAll(productResultManager.getCloseOfferMap().get("closeOfferWarmtransfer"));
				}

			}
			productResultManager.context.sortProducts(allClosingOfferProducts, "score");
		}
		return allClosingOfferProducts;
	}
	
	/** This method use to check whether video product is PROVISION_READY or not
	 * @param order
	 * @return
	 */
	private boolean isVideoOrdered(OrderType order){
		if(order != null   && order.getLineItems()!= null 
				&& order.getLineItems().getLineItem() != null
				&& !order.getLineItems().getLineItem().isEmpty()){
			for(LineItemType lineItemType:order.getLineItems().getLineItem()){
				if(lineItemType != null 
						&& lineItemType.getLineItemAttributes() != null
						&& lineItemType.getLineItemAttributes().getEntity()!= null 
						&& !lineItemType.getLineItemAttributes().getEntity().isEmpty()){
					if(ProductSearchIface.VIDEO.equalsIgnoreCase(LineItemUtil.getLineItemAttr(lineItemType,Constants.PRODUCT_CATEGORY,Constants.PRODUCT_TYPE_1))
							|| ProductSearchIface.TRIPLE_PLAY.equalsIgnoreCase(LineItemUtil.getLineItemAttr(lineItemType,Constants.PRODUCT_CATEGORY,Constants.PRODUCT_TYPE_1))
							|| Constants.DOUBLE_PLAY_VIDEO_INTERNET.equalsIgnoreCase(LineItemUtil.getLineItemAttr(lineItemType,Constants.PRODUCT_CATEGORY,Constants.PRODUCT_TYPE_1))
							|| Constants.DOUBLE_PLAY_VIDEO_PHONE.equalsIgnoreCase(LineItemUtil.getLineItemAttr(lineItemType,Constants.PRODUCT_CATEGORY,Constants.PRODUCT_TYPE_1))){
						if( lineItemType.getLineItemStatus().getStatusCode() != null 
								&& LineItemStatusCodesType.PROVISION_READY.equals(lineItemType.getLineItemStatus().getStatusCode())){
							return true;
						}else if(lineItemType.getLineItemStatusHistory() != null 
								&& lineItemType.getLineItemStatusHistory().getPreviousStatus() != null
								&& !lineItemType.getLineItemStatusHistory().getPreviousStatus().isEmpty()){
							for(LineItemStatusType lineItemStatus:lineItemType.getLineItemStatusHistory().getPreviousStatus()){
								if(LineItemStatusCodesType.PROVISION_READY.equals(lineItemStatus.getStatusCode())){
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	private boolean isADTRentersOrdered(OrderType order){
		if(order != null   && order.getLineItems()!= null 
				&& order.getLineItems().getLineItem() != null
				&& !order.getLineItems().getLineItem().isEmpty()){
			for(LineItemType lineItemType:order.getLineItems().getLineItem()){
				if(lineItemType != null 
						&& lineItemType.getLineItemAttributes() != null
						&& lineItemType.getLineItemAttributes().getEntity()!= null 
						&& !lineItemType.getLineItemAttributes().getEntity().isEmpty()
						&& lineItemType.getLineItemDetail()!= null
						&& lineItemType.getLineItemDetail().getDetail() != null
						&& lineItemType.getLineItemDetail().getDetail().getProductLineItem() != null
						&& lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider() != null
						&& lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId() != null){
					
					logger.info("HOMESECURITY ProviderExtId ::"+lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId());
					
					if(ProductSearchIface.HOMESECURITY.equalsIgnoreCase(LineItemUtil.getLineItemAttr(lineItemType,Constants.PRODUCT_CATEGORY,Constants.PRODUCT_TYPE_1))
							&& lineItemType.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals(Constants.ADT_RENTERS)){
						if( lineItemType.getLineItemStatus().getStatusCode() != null 
								&& LineItemStatusCodesType.PROVISION_READY.equals(lineItemType.getLineItemStatus().getStatusCode())){
							return true;
						}else if(lineItemType.getLineItemStatusHistory() != null 
								&& lineItemType.getLineItemStatusHistory().getPreviousStatus() != null
								&& !lineItemType.getLineItemStatusHistory().getPreviousStatus().isEmpty()){
							for(LineItemStatusType lineItemStatus:lineItemType.getLineItemStatusHistory().getPreviousStatus()){
								if(LineItemStatusCodesType.PROVISION_READY.equals(lineItemStatus.getStatusCode())){
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
}
