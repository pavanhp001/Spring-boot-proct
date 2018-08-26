package com.AL.util.converter.impl;
 
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.AL.V.beans.entity.LineItem;
import com.AL.V.beans.entity.OrderAudit;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.StatusRecordBean;

public class AuditBuilder {
	
	private static final Logger logger = Logger.getLogger(AuditBuilder.class);

	/**
	 * A method to create OrderAudit instance based on SalesOrder and other information
	 * @param order
	 * @param details
	 * @param action
	 * @return
	 */
	public static OrderAudit createOrderAudit(final SalesOrder order,String details, String action,String agentId) {
		
		logger.debug("audit sales order:" + order.getExternalId());
		
		
		OrderAudit orderAudit = new OrderAudit();
		orderAudit.setAuditDate(Calendar.getInstance());

		if (agentId != null)
			orderAudit.setAgentId(agentId);
		else
			orderAudit.setAgentId(order.getAgentId());

		if (order.getExternalId() != null)
			orderAudit.setOrderId( String.valueOf(order.getExternalId() ));
		else
			orderAudit.setOrderId("-1" );

		if ((order.getCurrentStatus() != null) && ((order.getCurrentStatus().getStatus() != null)))
			orderAudit.setToStatusCode(order.getCurrentStatus().getStatus());
		else
			orderAudit.setToStatusCode("-1");

		orderAudit.setDescription(action);
		orderAudit.setToReasonCode(getResonCode(order.getCurrentStatus()));
		
		//adding list of reason of previous status
		orderAudit.setFromReasonCode(getPreviousReason( order.getHistoricStatus() ));
		
		//adding previous status
		orderAudit.setFromStatusCode(getPreviousStatus( order.getHistoricStatus() ));
		
		//Saving whole order xml which contains previous state of the order		
		orderAudit.setDetail( details );
		
		if ((orderAudit.getFromReasonCode() == null) && (orderAudit.getFromReasonCode().length() == -1)) {
			orderAudit.setFromReasonCode("-1");
		}
		
		if ((orderAudit.getFromStatusCode() == null) && (orderAudit.getFromStatusCode().length() == -1)) {
			orderAudit.setFromStatusCode("-1");
		}
		
		if ((orderAudit.getToReasonCode() == null) && (orderAudit.getToReasonCode().length() == -1)) {
			orderAudit.setToReasonCode("-1");
		}
		
		
	 
		 
		
		return orderAudit;
	}
	
	/**
	 * 
	 * Utility method to get the reason code as string from list of reason
	 * @param order
	 * @return
	 */
	private static String getResonCode(StatusRecordBean statusRecord){
		StringBuilder sb = new StringBuilder(100);
		if ( statusRecord != null )
		{
			List<String> reasonCodes = statusRecord.getReasons();
			for ( String rCode : reasonCodes )
			{
				sb.append( rCode + " , " );
			}
		}
		//TODO this needs to be fixed as sometimes order's current status does nor have proper reason code
		if(sb.length() == 0)
			sb.append( "0" );
		return sb.toString();
	}
	
	/**
	 * 
	 * Utility method to get the reason code as string from list of reason
	 * @param order
	 * @return
	 */
	private static String getPreviousStatus(List<StatusRecordBean> statusList){
		String sb = "";
		if ( statusList != null && statusList.size() > 0)
		{
			StatusRecordBean prevStatus = statusList.get( 0 );
			sb = prevStatus.getStatus();
		}
		return sb.toString();
	}

	/**
	 * 
	 * Utility method to get the reason code as string from list of reason
	 * @param order
	 * @return
	 */
	private static String getPreviousReason(List<StatusRecordBean> statusList){
		StringBuilder sb = new StringBuilder(100);
		if ( statusList != null && statusList.size() > 0)
		{
			StatusRecordBean prevStatus = statusList.get( 0 );
			List<String> prevReasons = prevStatus.getReasons();
			for(String reason : prevReasons){
				sb.append( reason + " , " );
			}
		}
		return sb.toString();
	}
	public static OrderAudit createOrderAudit(final LineItem li) {
		OrderAudit orderAudit = new OrderAudit();
		orderAudit.setAuditDate(Calendar.getInstance());

		// TODO:Complete this code with proper information.
		// TODO:When auditing info is determined.
		//orderAudit.setAgentId(1);

		orderAudit.setOrderId( String.valueOf(li.getExternalId()) );

		orderAudit.setToStatusCode(li.getCurrentStatus().getStatus());

		orderAudit.setDescription(li.getProviderCustomerAccountNumber());
		orderAudit.setToReasonCode("toReasonCode");
		orderAudit.setFromReasonCode("fromReasonCode");
		orderAudit.setFromStatusCode("fromStatusCode");

		return orderAudit;
	}

}
