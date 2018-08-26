package com.AL.activity.impl;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.AL.activity.Activity;
import com.AL.task.context.TaskContextParamEnum;
import com.AL.task.context.impl.OrchestrationContext;
import com.AL.V.beans.entity.SalesOrder;
import com.AL.V.beans.entity.SalesOrderContext;
import com.AL.vm.service.OrderManagementService;
import com.AL.vm.util.converter.unmarshall.UnmarshallSalesOrderContext;
import com.AL.xml.v4.OrderManagementRequestResponseDocument.OrderManagementRequestResponse.Request;
import com.AL.xml.v4.SalesContextType;

@Component("activityPersistSalesContext")
public class ActivityPersistSalesContext implements Activity {

	private static final Logger logger = Logger
			.getLogger(ActivityPersistSalesContext.class);

	@Autowired
	private UnmarshallSalesOrderContext unmarshallSOContext;

	@Autowired
	private OrderManagementService omeService;

	public void process(OrchestrationContext params) {
		Request request = (Request) params.get(TaskContextParamEnum.request.name());
		SalesOrder salesOrder = (SalesOrder) params.get(TaskContextParamEnum.salesOrder.name());
		Set<SalesOrderContext> finalSOCSet = new HashSet<SalesOrderContext>();
		if (request != null && request.getSalesContext() != null) {
			SalesContextType scType = request.getSalesContext();
			if ((scType != null) && (salesOrder != null)) {
				logger.debug("GUID from sales order ="+salesOrder.getGuid());
				Set<SalesOrderContext> oldSocList = salesOrder.getSalesOrderContexts();
				Set<SalesOrderContext> socList = unmarshallSOContext.build(scType);
				//Filter duplicate and use new one with new value
				Set<SalesOrderContext> deleteSet = new HashSet<SalesOrderContext>();
				Iterator<SalesOrderContext> oldItr = oldSocList.iterator();
				while (oldItr.hasNext()) {
					SalesOrderContext oldCtx = oldItr.next();
					for (SalesOrderContext newCtx : socList) {
						String oldEntName = oldCtx.getEntityName();
					    String newEntName = newCtx.getEntityName();
					    String oldName = oldCtx.getName();
					    String newName = newCtx.getName();
						if (oldEntName.equalsIgnoreCase(newEntName) && oldName.equalsIgnoreCase(newName)) {
							deleteSet.add(oldCtx);
						}
					}
				}
				if(!deleteSet.isEmpty()){
					oldSocList.removeAll(deleteSet);
				}
				
				oldSocList.addAll(socList);
				socList = (Set<SalesOrderContext>) salesOrder.getSalesOrderContexts();
				logger.info("Persisting sales context for order:"
						+ salesOrder.getExternalId());
				if(null == params.get("DONT_SAVE_SALES_ORDER")){
					omeService.updateSalesOrder(salesOrder);
				} else {
					//We are saving only sales context. So set the sales order id in sales context
					for(SalesOrderContext sc : socList) {
						sc.setSalesOrderId(salesOrder.getId());
					}
				}
			}
		}else{
			//Just update order if no salescontext given
			if(null == params.get("DONT_SAVE_SALES_ORDER")){
				omeService.updateSalesOrder(salesOrder);
			}
		}
	}

	public Set<SalesOrderContext> updateSalesOrderContext(SalesOrder so, SalesOrderContext newSOC) {
	    if(so.getSalesOrderContexts() != null && !so.getSalesOrderContexts().isEmpty()) {
			boolean foundExisting = false;
			Iterator<SalesOrderContext> oldItr = so.getSalesOrderContexts().iterator();
			while(oldItr.hasNext()) {
			    SalesOrderContext extSoc = oldItr.next();
			    if(newSOC.getEntityName().equalsIgnoreCase(extSoc.getEntityName()) && newSOC.getName().equalsIgnoreCase(extSoc.getName())) {
					extSoc.setValue(newSOC.getValue());
					foundExisting = true;
					break;
			    }
			}
	
			if(!foundExisting) {
			    so.getSalesOrderContexts().add(newSOC);
			}
	    }
	    
	    omeService.saveSalesContext(so.getSalesOrderContexts());
	    return so.getSalesOrderContexts();
	}
}
