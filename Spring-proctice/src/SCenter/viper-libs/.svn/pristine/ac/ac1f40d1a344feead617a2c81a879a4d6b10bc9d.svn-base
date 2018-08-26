package com.A.vm.util.converter.marshall;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.A.V.beans.entity.AccountHolder;
import com.A.V.beans.entity.SalesOrder;
import com.A.V.beans.entity.User;
import com.A.Vdao.dao.AccountHolderDao;
import com.A.vm.service.OrderAgentService;
import com.A.vm.util.converter.mapper.AccountHolderMapper;
import com.A.xml.v4.AccountHolderType;
import com.A.xml.v4.CustomerType;
import com.A.xml.v4.OrderType;

/**
 * @author ebthomas
 *
 * @param <T>
 */
public final class MarshallOrder<T extends SalesOrder> {

    private static final Logger logger = Logger.getLogger(MarshallOrder.class);

    @Autowired
    private MarshallLineItem marshallLineItem;

    @Autowired
    private OrderAgentService agentService;
    
	@Autowired
	private AccountHolderDao accountHolderDao;

    /**
     * Marshall Order.
     */
    public MarshallOrder() {
	super();
    }

    private Boolean isValid() {

	return Boolean.TRUE;
    }

    /**
     * @param orderType
     *            XML Bean Order Type Destination
     * @param salesOrderBean
     *            Sales Order Source
     */
	private void updateBasicOrderInfo(final OrderType orderType,
			final SalesOrder salesOrderBean) {
		if ((orderType != null) && (salesOrderBean != null)) {
			orderType.setAgentId(salesOrderBean.getAgentId());

			if (salesOrderBean.getAgentId() != null) {

				User agent = agentService.findAgentById(salesOrderBean
						.getAgentId());

				if (agent != null) {
					orderType.setAgentName(agent.getUserName());
				} else {
					// Setting agent name as agent id, as accord agents will not
					// exist in V system for harmony
					orderType.setAgentName(salesOrderBean.getAgentId());
				}
			}

			orderType.setAConfirmationNumber(salesOrderBean
					.getAConfirmNumber());
			orderType.setACustomerAccountNumber(salesOrderBean
					.getAAccountNumber());

			orderType.setSource(salesOrderBean.getOrderSource());

			if (salesOrderBean.getReferrer() != null) {
				orderType.setReferrerId(salesOrderBean.getReferrer());
			}
			orderType.setExternalId(salesOrderBean.getExternalId().longValue());
			orderType.setAssociatedWithMove(salesOrderBean
					.getAssociatedWithMove());
			orderType.setMoveDate(salesOrderBean.getMoveDate());
			orderType.setWhenToCallBack(salesOrderBean.getWhenToCallBack());
			orderType.setCampaignId(salesOrderBean.getCampaignId());
			orderType.setOrderDate(salesOrderBean.getOrderDate());
			orderType.setIsZipOnlySearch(salesOrderBean.getIsZipOnlySearch());

			if ((orderType != null)
					&& (orderType.getCustomerInformation() != null)) {
				CustomerType custInfo = orderType.getCustomerInformation()
						.getCustomer();

				if (custInfo != null) {
					CustomerType custType = custInfo;
					if (custType != null) {
						custType.setExternalId(salesOrderBean
								.getConsumerExternalId().longValue());
					}
				}
			}
			
			// Add PUPUSA related entries
			if(null != salesOrderBean.getProgramExternalId()){
				orderType.setProgramExternalId(salesOrderBean.getProgramExternalId());
			}
			if(null != salesOrderBean.getOrdersourceExternalId()){
				orderType.setOrdersourceExternalId(salesOrderBean.getOrdersourceExternalId());
			}
			if(null != salesOrderBean.getInboundVdn()){
				orderType.setInboundVdn(salesOrderBean.getInboundVdn());
			}
		}
	}

    /**
     * @param salesOrderBean
     *            Sales Order Bean
     * @param orderType
     *            Order Type
     * @return Order Type
     */
    public OrderType doBuildOrder(final SalesOrder salesOrderBean, final OrderType orderType, boolean includeAccountHolders) {
		logger.info("Building order response");
		if (salesOrderBean != null) {
		    updateBasicOrderInfo(orderType, salesOrderBean);
		    /*
		     * orderType.setBillingInfo(MarshallBillingInformation.Builder .build(salesOrderBean.getBilling()));
		     */
		    // orderType.setConsumer(MarshallConsumers.buildConsumerBean(salesOrderBean.getConsumer()));
		    orderType.setLineItems(marshallLineItem.build(salesOrderBean.getLineItems(), includeAccountHolders));
		    orderType.setOrderStatus(MarshallStatus.Builder.copyStatusRecordBean(salesOrderBean.getCurrentStatus()));
		    orderType.setOrderStatusHistory(MarshallStatus.Builder.copyStatusRecordBeanList(salesOrderBean.getHistoricStatus()));
		    orderType.setTotalPriceInfo(MarshallPriceInfo.Builder.build(salesOrderBean.getTotalPrice()));
		    // orderType.setSecondCallInfo(copySecondCallInfo(salesOrderBean,orderType.addNewSecondCallInfo()));
		    //LineItemCollectionType lineItemCollection = updateLineItemsInfo(salesOrderBean.getLineItems());
		    //orderType.setLineItems(lineItemCollection);
		    /*
			 * If client wants all accountHolers in the response. Then get all account holders for the order
			 */
			if(includeAccountHolders) {
				logger.debug("includeAccountHolders flag is true");
				//Add all account holders to the response
				for(AccountHolder accountHolder : salesOrderBean.getAccountHolders()) {
					AccountHolderType linAccHolder = orderType.addNewAccountHolder();
					AccountHolderMapper.copyAccountHolder(accountHolder, linAccHolder);
				}
			} 
		}
		logger.info("Building order response completed");
		return orderType;
    }
    
    public OrderType doBuildOrder(final SalesOrder salesOrderBean, final OrderType orderType) {
    	return doBuildOrder(salesOrderBean, orderType, false);
    }

    /**
     * @param salesOrderBean
     *            Sales Order Bean
     * @param orderType
     *            Order Type
     * @return Order Type
     */
    public OrderType build(final SalesOrder salesOrderBean) {
		if (isValid()) {
	
		    OrderType orderType = OrderType.Factory.newInstance();
		    return doBuildOrder(salesOrderBean, orderType);
		}
		throw new IllegalArgumentException("invalid document.  unable to build");
    }

    /**
     * @param salesOrderBean
     *            Sales Order Bean
     * @param orderType
     *            Order Type
     * @return Order Type
     */
    public OrderType build(final SalesOrder salesOrderBean, final OrderType orderType) {
		return build(salesOrderBean, orderType, false);
    }
    
    public OrderType build(final SalesOrder salesOrderBean, final OrderType orderType, boolean getAllAccountHolders) {
		if (isValid()) {
		    return doBuildOrder(salesOrderBean, orderType, getAllAccountHolders);
		}
		throw new IllegalArgumentException("invalid document.  unable to build");
    }

    public MarshallLineItem getMarshallLineItem() {
    	return marshallLineItem;
    }

    public void setMarshallLineItem(MarshallLineItem marshallLineItem) {
    	this.marshallLineItem = marshallLineItem;
    }

}
