package com.A.ui.service.V;

import org.apache.commons.lang.StringUtils;

import com.A.ui.transport.TransportConfig;
import com.A.xml.v4.OrderProvisioningRequest;
import com.A.xml.v4.OrderProvisioningResponse;
import com.A.xml.v4.TransactionType;

public enum OrderProvisioningService {

    INSTANCE;

    private static final String DEFAULT_AGENT_ID = "default";

    public OrderProvisioningResponse authenticateCustomer(OrderProvisioningRequest opRequest) {
        if (StringUtils.isBlank(opRequest.getAgentId())) {
            opRequest.setAgentId(DEFAULT_AGENT_ID);
        }
        opRequest.setTransactionType(TransactionType.AUTHENTICATE_CUSTOMER);
        OrderProvisioningResponse opResponse = TransportConfig.INSTANCE.getOrderProvisioningClient().send(
                opRequest);
        return opResponse;
    }

    public OrderProvisioningResponse creditQual(OrderProvisioningRequest opRequest) {
        if (StringUtils.isBlank(opRequest.getAgentId())) {
            opRequest.setAgentId(DEFAULT_AGENT_ID);
        }
        opRequest.setTransactionType(TransactionType.CREDIT_QUALIFICATION);
        OrderProvisioningResponse opResponse = TransportConfig.INSTANCE.getOrderProvisioningClient().send(
                opRequest);
        return opResponse;
    }

    public OrderProvisioningResponse orderQual(OrderProvisioningRequest opRequest) {
        if (StringUtils.isBlank(opRequest.getAgentId())) {
            opRequest.setAgentId(DEFAULT_AGENT_ID);
        }
        opRequest.setTransactionType(TransactionType.ORDER_QUALIFICATION);
        OrderProvisioningResponse opResponse = TransportConfig.INSTANCE.getOrderProvisioningClient().send(
                opRequest);
        return opResponse;
    }

    public OrderProvisioningResponse validatePayment(OrderProvisioningRequest opRequest) {
        if (StringUtils.isBlank(opRequest.getAgentId())) {
            opRequest.setAgentId(DEFAULT_AGENT_ID);
        }
        opRequest.setTransactionType(TransactionType.VALIDATE_PAYMENT);
        OrderProvisioningResponse opResponse = TransportConfig.INSTANCE.getOrderProvisioningClient().send(
                opRequest);
        return opResponse;
    }
}