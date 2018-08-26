package com.A.ui.transport;

import com.A.V.gateway.CCPClient;
import com.A.V.gateway.CustomerClient;
import com.A.V.gateway.DetailClient;
import com.A.V.gateway.DialogClient;
import com.A.V.gateway.OrderClient;
import com.A.V.gateway.OrderProvisioningClient;
import com.A.V.gateway.ProductClient;
import com.A.V.gateway.UAMClient;
import com.A.V.gateway.jms.CCPClientJMS;
import com.A.V.gateway.jms.CustomerClientJMS;
import com.A.V.gateway.jms.DetailClientJMS;
import com.A.V.gateway.jms.DialogClientJMS;
import com.A.V.gateway.jms.OrderClientJMS;
import com.A.V.gateway.jms.OrderProvisioningClientJMS;
import com.A.V.gateway.jms.ProductClientJMS;
import com.A.V.gateway.jms.UAMClientJMS;

public enum TransportConfig {

	INSTANCE;

	// TODO: Configure SOAP or JMS from the database or external config
	private OrderClient jmsOrderClient = new OrderClientJMS();
	private CustomerClient jmsCustomerClient = new CustomerClientJMS();
	private ProductClient jmsProductClient = new ProductClientJMS();
	private DialogClient jmsDialogClient = new DialogClientJMS();
	private DetailClient jmsDetailClient = new DetailClientJMS();
	private UAMClient jmsUamClient = new UAMClientJMS();
	private CCPClient jmsCCPClient = new CCPClientJMS();

	private OrderProvisioningClient jmsOrderProvisioningClient = new OrderProvisioningClientJMS();

	public OrderProvisioningClient getOrderProvisioningClient() {
		return jmsOrderProvisioningClient;
	}

	public CCPClient getCCPClient() {
		return jmsCCPClient;
	}

	public OrderClient getOrderClient() {
		return jmsOrderClient;
	}

	public CustomerClient getCustomerClient() {
		return jmsCustomerClient;
	}

	public ProductClient getProductClient() {
		return jmsProductClient;
	}

	public DialogClient getDialogClient() {
		return jmsDialogClient;
	}

	public DetailClient getDetailClient() {
		return jmsDetailClient;
	}

	public UAMClient getUAMClientJMS() {
		return jmsUamClient;
	}

}
