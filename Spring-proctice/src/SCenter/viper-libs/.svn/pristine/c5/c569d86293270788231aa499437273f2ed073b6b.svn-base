package com.A.ie.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.A.xml.v4.LineItemType;
import com.A.xml.v4.TransientResponseContainerType;

public class TransientLineItemContainer implements Serializable {

	private int transactionKey = -1;

	private static final Logger logger = Logger.getLogger(TransientLineItemContainer.class);
	/**
	 *
	 */
	private static final long serialVersionUID = 9206685812907804753L;
	private Map<Integer, TransientLineItem> container = new HashMap<Integer, TransientLineItem>();
	
	public static TransientLineItemContainer create() {
		TransientLineItemContainer container = new TransientLineItemContainer();

		return container;
	}

	public Map<Integer, TransientLineItem> getContainer() {
		return container;
	}

	public int getTransactionKey() {
		return transactionKey;
	}

	public void setTransactionKey(int transactionKey) {
		this.transactionKey = transactionKey;
	}

	public void setContainer(Map<Integer, TransientLineItem> container) {
		this.container = container;
	}

	public LineItemType get(Integer index) {
		if (((container == null) && (container.size() == 0))
				|| ((container.get(index) == null))) {
			return null;
		}
		TransientLineItem tli = container.get(index);
		logger.debug("TransientLineItemContainer: " + tli.toString());
		LineItemType lit = tli.getLineitem();

		return lit;
	}

	public TransientResponseContainerType getTransientResponseContainer(
			Integer index) {
		if (((container == null) && (container.size() == 0))
				|| ((container.get(index) == null))) {
			return TransientResponseContainerType.Factory.newInstance();
		}
		return container.get(index).getLineitem()
				.getTransientResponseContainer();
	}

	public void add(LineItemType lineItemType) {
		if (lineItemType.getTransientResponseContainer() != null)
		{
			if (lineItemType.getTransientResponseContainer().getLineItemId() != null 
				&& !lineItemType.getTransientResponseContainer().getLineItemId().equals(""))
			{
				int number = Integer.valueOf(lineItemType.getTransientResponseContainer().getLineItemId());
				TransientLineItem transientLineItem = new TransientLineItem(
						lineItemType, number);
		
				logger.debug("LineItemNumber number: "+number);
				logger.debug("lineItemType: "+lineItemType.toString());
				logger.debug("transientLineItem: "+transientLineItem.toString());
		
				container.put(number, transientLineItem);
			}
		}
	}
}
