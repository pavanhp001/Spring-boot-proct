package com.A.ui.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.A.productResults.managers.ProductResultsManager;
import com.A.ui.constants.Constants;
import com.A.ui.util.LineItemUtil;
import com.A.ui.util.Utils;
import com.A.ui.vo.SalesCenterVO;
import com.A.xml.v4.LineItemType;
import com.A.xml.v4.OrderLineItemDetailTypeType;
import com.A.xml.v4.OrderType;

public enum WarmTransferFactory {

	INSTANCE;

	/**
	 * Verifies the Order has  WarmTransfer Providers or not.
	 * 
	 * 
	 * @param order
	 * @return
	 */
	public boolean isWarmTransferEnabled(OrderType order, HttpServletRequest request)
	{

		List<LineItemType> lineItems = CartLineItemFactory.INSTANCE.sortLineItemProducts(order, request);

		if(lineItems != null && lineItems.size() != 0)
		{
			
			//Fetching the ProviderId
			String providerId = getLastLineItemProvider(lineItems);
			
			if(providerId != null)
			{
				//Verifying the providerId
				return isWarmTransferProvider(providerId);
			}
		}

		return false;
	}

	/**
	 * Verifies the provider id belongs to Warm transfer providers or not
	 * 
	 * 
	 * @param providerId
	 * @return
	 */
	public boolean isWarmTransferProvider(String providerId)
	{
		Map<String, Integer> warmTransferOrderMap = ProductResultsManager.getWarmTransferOrderMap();
		if(warmTransferOrderMap != null && warmTransferOrderMap.get(providerId) != null){
			return true;
		}
		return false;
	}


	/**
	 * Extracts the providerId from the LineItemDetailType
	 * 
	 * 
	 * @param lineItems
	 * @return
	 */
	public String getLastLineItemProvider(List<LineItemType> lineItems) 
	{

		//Fetching the Last lineItem from the Order
		LineItemType lastLineItem = lineItems.get(lineItems.size() - 1);

		OrderLineItemDetailTypeType detailType = lastLineItem.getLineItemDetail().getDetail();
		if(detailType != null)
		{
			if(detailType.getProductLineItem() != null &&
					detailType.getProductLineItem().getProvider() != null)
			{
				return detailType.getProductLineItem().getProvider().getExternalId();
			}
		}
		return null;
	}

	public String getLastLineItemProvider(List<LineItemType> lineItems,
			HttpServletRequest request) {

		// Fetching the Last lineItem from the Order
		LineItemType lastLineItem = lineItems.get(lineItems.size() - 1);

		OrderLineItemDetailTypeType detailType = lastLineItem.getLineItemDetail().getDetail();
		if(detailType != null)
		{
			if(detailType.getProductLineItem() != null &&
					detailType.getProductLineItem().getProvider() != null)
			{
				request.getSession().setAttribute("lastLineItem", lastLineItem);
				return detailType.getProductLineItem().getProvider().getExternalId();
			}
		}
		return null;
	}
	
	public boolean isEmailAddressUpdated(String emailAddress, SalesCenterVO salesCenterVo){
		boolean isEmailAddressChanged = false;
		String bestEmailContact = salesCenterVo.getValueByName("customer.bestEmailContact");
		if((Utils.isBlank(bestEmailContact)) || (! emailAddress.equalsIgnoreCase(bestEmailContact))){			
			salesCenterVo.setValueByName("customer.bestEmailContact",emailAddress);
			isEmailAddressChanged = true;
		}
		return isEmailAddressChanged;
	}
	
	/*Returns an boolean value for is GasStartDate Changed
	 * @param eletricDate
	 * @param salesCenterVo
	 * @return boolean value based on condition
	*/
	public boolean isGasStartDateChanged(String gasDate, SalesCenterVO salesCenterVo)
	{
		String gasStartDate = salesCenterVo.getValueByName("gasService.startDate");
		if(!Utils.isBlank(gasStartDate) && !Utils.isBlank(gasDate) && (! gasDate.equals(gasStartDate)))
		{			
			salesCenterVo.setValueByName("gasService.startDate",gasDate);
			return true;
		}
		return false;
	}
	
	/*Returns an boolean value for is EletricDate Changed
	 * @param eletricDate
	 * @param salesCenterVo
	 * @return boolean value based on condition
	*/
	public boolean isEletricDateChanged(String eletricDate, SalesCenterVO salesCenterVo)
	{
		String eletricStartDate = salesCenterVo.getValueByName("electricService.startDate");
		if(!Utils.isBlank(eletricStartDate) && !Utils.isBlank(eletricDate) 
			&& (! eletricDate.equals(eletricStartDate)))
		{			
			salesCenterVo.setValueByName("electricService.startDate",eletricDate);
			return true;
		}
		return false;
	}
}
