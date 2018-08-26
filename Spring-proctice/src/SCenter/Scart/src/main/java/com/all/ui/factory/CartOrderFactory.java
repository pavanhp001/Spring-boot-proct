package com.AL.ui.factory;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import com.AL.ui.service.V.OrderService;
import com.AL.ui.util.CartLineItemUtil;
import com.AL.V.factory.SalesContextFactory;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.OrderType;
import com.AL.xml.v4.SalesContextType;

/**
 * @author Preetam
 *
 */
public enum CartOrderFactory {
	
	INSTANCE;
	/**
	 * Logger Initialization
	 * 
	 */
	private static final Logger logger = Logger.getLogger(CartOrderFactory.class);
	/**
	 * @param agentId
	 * @param orderType
	 * @param salesContext
	 */
	public void updateSalesContextForProductSubmittedInCKOs(String agentId, OrderType orderType, SalesContextType salesContext)
	{
		logger.info("updateSalesContextForProductSubmittedInCKOs_begin");
		long startTimer = 0;
		StopWatch timer = new StopWatch();
		timer.start();
		try{
			boolean isProductSubmittedInCKOs = false;
			
			lineItemLoop: for(LineItemType lineItem : orderType.getLineItems().getLineItem())
			{
				if(CartLineItemUtil.isSubmittedProduct(lineItem) && 
						(lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("27010360")
						|| lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("15500201")		
						|| lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("24699452")
						|| lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("32416075")
						|| lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("32946482")
						|| lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("32952482")
						|| lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("15499341")
						|| lineItem.getLineItemDetail().getDetail().getProductLineItem().getProvider().getExternalId().equals("15499381")))
					{
						isProductSubmittedInCKOs = true;
							break lineItemLoop;
					}
			}
			
			if(isProductSubmittedInCKOs)
			{
				startTimer = timer.getTime();
				OrderService.INSTANCE.updateSalesContext(agentId, String.valueOf(orderType.getExternalId()), 
							CartSalesContextFactory.INSTANCE.getStatusCompleteSalesContext(salesContext), 
							SalesContextFactory.INSTANCE.getAttribute(salesContext, "GUID"));
				logger.info("TimetakenforUpdateOrderSeviceCall="+(timer.getTime()-startTimer));
			}
		}finally
		{
			timer.stop();
		}
	}

}
