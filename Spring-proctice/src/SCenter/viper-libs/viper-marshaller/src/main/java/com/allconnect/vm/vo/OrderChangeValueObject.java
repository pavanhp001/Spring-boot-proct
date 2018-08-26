/**
 *
 */
package com.A.vm.vo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.A.enums.LineItemStatus;
import com.A.enums.OrderStatus;
import com.A.V.beans.entity.LineItem;
import com.A.vm.vo.OrderChangeValueObject;
import com.A.xml.v4.LineItemCollectionType;
import com.A.xml.v4.LineItemStatusType;
import com.A.xml.v4.OrderStatusWithTypeType;
import com.A.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;

/**
 * @author ebthomas
 *
 */
public class OrderChangeValueObject implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(OrderChangeValueObject.class);
	private String orderId;
	private String status;
	private String statusDescription;
	private String lineItemExternalId;
	private LineItem lineItemBean;
	private List<Integer> reasonList;
	private List<String> lineItemExternalIds;
	private Calendar changedAt;
	private String agentId;
	private LineItemCollectionType lineItemCollectionType;
	private boolean isAppliesToLineItemIncluded;
	private String correlationId;



	 public static OrderChangeValueObject doBuildOrderStatus( final Request originalMessage )
     {
         String orderId = originalMessage.getOrderId();
         OrderStatusWithTypeType newOrderStatus = originalMessage.getNewOrderStatus();

         OrderChangeValueObject orderChangeValueObject = null;
         if(newOrderStatus != null)
         {
             orderChangeValueObject = new OrderChangeValueObject( orderId, newOrderStatus );
         }
         else
         {

             orderChangeValueObject = new OrderChangeValueObject( orderId );
         }
         orderChangeValueObject.setAgentId( originalMessage.getAgentId() );
         return orderChangeValueObject;
     }


	public static OrderChangeValueObject createSubmit(
			Request request) {

		logger.debug("creating order change value object");
		OrderChangeValueObject orderChangeVO = doBuildOrderStatus(request);

		// Change the status to submitted to track number/time of submissions
		// for order. Used in retry logic.

		orderChangeVO.setStatus(OrderStatus.submitted.name());
		orderChangeVO.setStatusDescription("order submitted");
		orderChangeVO.setChangedAt(Calendar.getInstance());
		orderChangeVO.setAgentId(AgentUtil.getAgentId(request));

		logger.debug("created order change value object : "+ orderChangeVO.toString());
		return orderChangeVO;

	}


	public void clear()
	{
	        orderId =  null;
	        status=  null;
	        statusDescription=  null;
	        lineItemExternalId=  null;
	        lineItemBean=  null;
	        reasonList=  null;
	        changedAt=  null;
	        agentId=  null;
	        lineItemCollectionType=  null;
	}
	/**
	 * @param lineItemBean
	 *            LIne Item Bean associated with this request
	 */
	public OrderChangeValueObject(final LineItem lineItemBean) {

		this.lineItemBean = lineItemBean;
	}


	public OrderChangeValueObject(final String orderId, final String lineItemExternalId,
			List<Integer> reasonList) {
		this.orderId = orderId;
		this.lineItemExternalId = lineItemExternalId;
		this.reasonList = reasonList;
	}


	/**
	 * @param orderId
	 *            Id of the order
	 * @param lineItemNumber
	 *            LineItem to place the LineItem after
	 * @param lineItemBean
	 *            LineItem Bean
	 */
	public OrderChangeValueObject(final String orderId, final String lineItemExternalId,
			final LineItem lineItemBean) {
		this.orderId = orderId;
		this.lineItemExternalId = lineItemExternalId;
		this.lineItemBean = lineItemBean;
	}
    private List<LineItem> liList;
	/**
	 * @param orderId
	 *            order Number
	 * @param orderStatus
	 *            order Status
	 */
	public OrderChangeValueObject(final String orderId,
			final OrderStatusWithTypeType newOrderStatus) {
		this.orderId = orderId;
		this.status = String.valueOf(newOrderStatus.getStatus());

		this.changedAt = newOrderStatus.getDateTimeStamp();
		this.reasonList = newOrderStatus.getReasonList();
		this.agentId = String.valueOf(newOrderStatus.getAgentId());


	}

	/**
	 * @param orderId
	 *            order Number
	 * @param orderStatus
	 *            order Status
	 */
	public OrderChangeValueObject(final String orderId, final String lineItemExternalId,
			final LineItemStatusType newLineItemStatus) {
		this.orderId = orderId;
		this.status = String.valueOf(newLineItemStatus.getStatusCode());
		this.statusDescription = String.valueOf(newLineItemStatus
				.getStatusCodeDescription());
		this.changedAt = newLineItemStatus.getDateTimeStamp() != null ? newLineItemStatus.getDateTimeStamp() : Calendar.getInstance();
		this.reasonList = newLineItemStatus.getReasonList();
		this.lineItemExternalId = lineItemExternalId;
		//this.agentId = String.valueOf(newLineItemStatus.getAgentId());


	}

	/**
	 *
	 * @param orderId
	 * @param lineItemExternalId
	 * @param newLineItemStatus
	 * @param agentId
	 */
	public OrderChangeValueObject(final String orderId, final String lineItemExternalId,
			final LineItemStatusType newLineItemStatus,String agentId) {
		this.orderId = orderId;
		this.status = String.valueOf(newLineItemStatus.getStatusCode());
		this.statusDescription = String.valueOf(newLineItemStatus
				.getStatusCodeDescription());
		this.changedAt = newLineItemStatus.getDateTimeStamp() != null ? newLineItemStatus.getDateTimeStamp() : Calendar.getInstance();
		this.reasonList = newLineItemStatus.getReasonList();
		this.lineItemExternalId = lineItemExternalId;
		this.agentId = agentId;


	}

	/**
	 *
	 * @param orderId
	 * @param lineItemExternalIds List
	 * @param newLineItemStatus
	 * @param agentId
	 */
	public OrderChangeValueObject(final String orderId, final List<String> lineItemExternalIds,
			final LineItemStatusType newLineItemStatus,String agentId) {
		this.orderId = orderId;
		this.status = String.valueOf(newLineItemStatus.getStatusCode());
		this.statusDescription = String.valueOf(newLineItemStatus
				.getStatusCodeDescription());
		this.changedAt = newLineItemStatus.getDateTimeStamp() != null ? newLineItemStatus.getDateTimeStamp() : Calendar.getInstance();
		this.reasonList = newLineItemStatus.getReasonList();
		this.setLineItemExternalIds(lineItemExternalIds);
		this.agentId = agentId;


	}

    public OrderChangeValueObject(List<LineItem> beanList)
    {
        this.liList = beanList;
    }

    public OrderChangeValueObject(List<LineItem> beanList, LineItemCollectionType liType, final String lineItemExternalId)
    {
        this.liList = beanList;
        this.lineItemCollectionType = liType;
        this.lineItemExternalId = lineItemExternalId;
    }


    public OrderChangeValueObject(final String orderId)
    {
        this.orderId = orderId;
    }
    /**
     * @return order Id
     */
    public String getOrderId()
    {
        return orderId;
    }


	/**
	 * @param orderId
	 *            order Id
	 * @param orderStatus
	 *            order Status
	 * @param lineItemNumber
	 *            line item number
	 */
	public OrderChangeValueObject(final String orderId, final String orderStatus,
			final String lineItemExternalId) {
		this.orderId = orderId;
		this.status = orderStatus;
		this.lineItemExternalId = lineItemExternalId;
	}


	/**
	 * @param orderId
	 *            setter
	 */
	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            setter
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	public String getLineItemExternalId() {
		return lineItemExternalId;
	}

	/**
	 * @param lineItemNumber
	 *            setter
	 */
	public void setLineItemNumber(final String lineItemExternalId) {
		this.lineItemExternalId = lineItemExternalId;
	}

	/**
	 * @return getter
	 */
	public LineItem getLineItem() {
		return lineItemBean;
	}


	/**
	 * @param lineItemBean
	 *            setter
	 */
	public void setLineItem(final LineItem lineItemBean) {
		this.lineItemBean = lineItemBean;
	}

	public List<String> getReasonListAsString() {
		List<String> finalReasons = new ArrayList<String>();

		if (reasonList != null) {
			for (Integer reasonCode : reasonList) {
				finalReasons.add(String.valueOf(reasonCode));
			}
		}
		return finalReasons;
	}

	public List<Integer> getReasonList() {
		return reasonList;
	}

	public void setReasonList(List<Integer> reasonList) {
		this.reasonList = reasonList;
	}

	public Calendar getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(Calendar changedAt) {
		this.changedAt = changedAt;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}



    public List<LineItem> getLiList()
    {
        return liList;
    }


    public void setLiList( List<LineItem> liList )
    {
        this.liList = liList;
    }

	public LineItemCollectionType getLineItemCollectionType() {
		return lineItemCollectionType;
	}

	public void setLineItemCollectionType(
			LineItemCollectionType lineItemCollectionType) {
		this.lineItemCollectionType = lineItemCollectionType;
	}
	public boolean isAppliesToLineItemIncluded()
	{
		return isAppliesToLineItemIncluded;
	}
	public void setAppliesToLineItemIncluded( boolean isAppliesToLineItemIncluded )
	{
		this.isAppliesToLineItemIncluded = isAppliesToLineItemIncluded;
	}
	public LineItem getLineItemBean()
	{
		return lineItemBean;
	}
	public void setLineItemBean( LineItem lineItemBean )
	{
		this.lineItemBean = lineItemBean;
	}
	public String getCorrelationId()
	{
		return correlationId;
	}
	public void setCorrelationId( String correlationId )
	{
		this.correlationId = correlationId;
	}


	/**
	 * @return the lineItemExternalIds
	 */
	public List<String> getLineItemExternalIds() {
		return lineItemExternalIds;
	}


	/**
	 * @param lineItemExternalIds the lineItemExternalIds to set
	 */
	public void setLineItemExternalIds(List<String> lineItemExternalIds) {
		this.lineItemExternalIds = lineItemExternalIds;
	}


	@Override
	public String toString() {
	    return "OrderChangeValueObject [orderId=" + orderId + ", status=" + status + ", statusDescription=" + statusDescription + ", lineItemExternalId=" + lineItemExternalId
		    + ", reasonList=" + reasonList + ", lineItemExternalIds=" + lineItemExternalIds + ", changedAt=" + changedAt + ", agentId=" + agentId
		    + ", lineItemCollectionType=" + lineItemCollectionType + ", isAppliesToLineItemIncluded=" + isAppliesToLineItemIncluded + ", correlationId=" + correlationId
		    + ", liList=" + liList + "]";
	}





}
